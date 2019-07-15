package ru.morou.korekor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.morou.korekor.controller.repr.CartItemRepr;
import ru.morou.korekor.controller.repr.ProductRepr;
import ru.morou.korekor.service.CartService;
import ru.morou.korekor.service.ProductService;
import ru.morou.korekor.service.repr.ProductInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @Controller - (Слой представления) Аннотация для маркировки java класса, как класса контроллера. Данный класс
 * представляет собой компонент, похожий на обычный сервлет (HttpServlet) (работающий с объектами HttpServletRequest и
 * HttpServletResponse), но с расширенными возможностями от Spring Framework.
 */

@Controller
public class MainController {

    /**
     *  Классический вариан логирования, а можно по другому https://habr.com/ru/post/276729/
     */
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Value("#{'${my.new.property}' + ' hahahah'}")
    private String myNewProperty;

    private final ProductService productService;

    private final CartService cartService;

    @Autowired
    public MainController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("cartItems", cartService.findAllItems());
        return "index";
    }

    @GetMapping("/shopping-cart")
    public String cartPage() {
        return "shopping-cart";
    }

    @GetMapping("/checkout")
    public String checkoutPage() {
        return "checkout";
    }

    @GetMapping("/products-list")
    public String storePage(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products-list";
    }

    @PostMapping("/shopping-cart/update")
    public String updateCart(CartItemRepr cartItemRepr, HttpServletRequest httpServletRequest) {
        logger.info("Update customer cart");
        ProductRepr productRepr = productService.findById(cartItemRepr.getProductId());

        if (productRepr != null) {
            cartService.addItemQty(new ProductInfo (productRepr, "", ""), cartItemRepr.getQty());
        }
        return "redirect:" + cartItemRepr.getPageUrl();
    }
}
