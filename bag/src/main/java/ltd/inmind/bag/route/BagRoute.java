package ltd.inmind.bag.route;

import lombok.AllArgsConstructor;
import ltd.inmind.bag.service.BagService;
import ltd.inmind.common.CommonAuth;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/bag")
@AllArgsConstructor
public class BagRoute {

    private final BagService bagService;

    @PutMapping("/proudct/add")

    public void addProduct(Long productId, Long count, HttpServletRequest request) {

        final String userAccount = CommonAuth.getUserAccount(CommonAuth.getAuthToken(request));

        bagService.addProduct(productId, count, userAccount);

    }

    @PutMapping("/product/del")
    public void delProduct(Long productId, Long count, HttpServletRequest request) {

        final String userAccount = CommonAuth.getUserAccount(CommonAuth.getAuthToken(request));

        bagService.delProduct(productId, count, userAccount);

    }

    @PostMapping("/settlement")
    public void settlement(HttpServletRequest request) {
        final String userAccount = CommonAuth.getUserAccount(CommonAuth.getAuthToken(request));

        bagService.settlement(userAccount);
    }


}
