package ltd.inmind.order.records;

import java.io.Serializable;

public record Inventory(Long productId, Long count, Double price) implements Serializable {
}
