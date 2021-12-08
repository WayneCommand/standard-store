package ltd.inmind.order.records;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public record Order(
        String id,
        List<Inventory> inventories,
        String account,
        Instant createTime,
        Long status // 1 created, 2 paid, 3 transferring, 4 done, 0 canceled
) implements Serializable {
    public static Order createNew(List<Inventory> inventories, String account) {
        return new Order(String.valueOf(System.nanoTime()), inventories, account, Instant.now(), 1L);
    }

    public Order toPaid() {
        return changeStatus(2L);
    }

    public Order toTransferring() {
        return changeStatus(3L);
    }

    public Order toDone() {
        return changeStatus(4L);
    }

    public Order changeStatus(Long _status) {
        return new Order(this.id, this.inventories, this.account, this.createTime, _status);
    }

}
