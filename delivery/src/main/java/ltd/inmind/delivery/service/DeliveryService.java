package ltd.inmind.delivery.service;

import ltd.inmind.delivery.records.Package;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeliveryService {

    static final Map<String, Package> memCache = new HashMap<>();


    public Package create(String senderName, String receiverName) {

        final Package aPackage = Package.create(senderName, receiverName);

        memCache.put(aPackage.ticketId(), aPackage);

        return aPackage;
    }

    public void sign(String tickedId) {

        if (memCache.containsKey(tickedId)) {

            final Package aPackage = memCache.get(tickedId);

            final Package sign = aPackage.sign();

            memCache.put(tickedId, sign);
        }

    }


}
