package ltd.inmind.stock.route;

import lombok.AllArgsConstructor;
import ltd.inmind.stock.configuration.StockLoader;
import ltd.inmind.stock.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/stock")
public class StockRoute {

    private StockService stockService;


    /**
     * 清算
     *
     * @param productId 商品
     * @param count     数量
     */
    @PostMapping("/clearance")
    public ResponseEntity<String> clearance(Long productId, Long count) {

        try {
            stockService.clearance(productId, count);
            return ResponseEntity.ok("");
        } catch (RuntimeException e) {
            return ResponseEntity.unprocessableEntity()
                    .body("clearance failed, cause: " + e.getMessage());
        }
    }


    /**
     * 返还
     *
     * @param productId 商品
     * @param count     数量
     */
    @PostMapping("/return")
    public ResponseEntity<String> ret(Long productId, Long count) {
        try {
            stockService.ret(productId, count);
            return ResponseEntity.ok("");
        } catch (RuntimeException e) {
            return ResponseEntity.unprocessableEntity()
                    .body("return failed, cause: " + e.getMessage());
        }
    }


}
