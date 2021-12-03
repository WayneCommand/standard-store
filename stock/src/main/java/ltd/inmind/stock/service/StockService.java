package ltd.inmind.stock.service;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.TxnResponse;
import io.etcd.jetcd.op.Cmp;
import io.etcd.jetcd.op.CmpTarget;
import io.etcd.jetcd.op.Op;
import io.etcd.jetcd.options.PutOption;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ltd.inmind.stock.client.EtcdClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@AllArgsConstructor
@Service
public class StockService {

    private final Client etcd;

    public static final Long DEFAULT_TIMEOUT = 10L;

    public void clearance(Long productId, Long count) {
        final ByteSequence productKey = EtcdClient.toByteSequence(productId);

        final KV kvClient = etcd.getKVClient();
        final CompletableFuture<GetResponse> respFuture = kvClient.get(productKey);

        try {
            final GetResponse resp = respFuture.get(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            final List<KeyValue> kvs = resp.getKvs();

            if (kvs.isEmpty())
                throw new RuntimeException("product not exist.");

            final KeyValue stock = kvs.get(0);
            final Long val = Long.valueOf(EtcdClient.toStr(stock.getValue()));

            if (val < count)
                throw new RuntimeException("stock not enough.");


            final Cmp codi = new Cmp(productKey, Cmp.Op.EQUAL, CmpTarget.value(stock.getValue()));
            final Op.PutOp put = Op.put(productKey, EtcdClient.toByteSequence(val - count), PutOption.DEFAULT);
            final CompletableFuture<TxnResponse> commit = kvClient.txn()
                    .If(codi)
                    .Then(put)
                    .commit();

            final TxnResponse txnResponse = commit.get(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            if (!txnResponse.isSucceeded()){
                throw new RuntimeException("clearance failed");
            }

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("clearance failed product id = " + productId, e);
            throw new RuntimeException("clearance failed");
        }

    }

    public void ret(Long productId, Long count) {

    }
}
