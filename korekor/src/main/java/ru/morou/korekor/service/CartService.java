package ru.morou.korekor.service;

import ru.morou.korekor.service.repr.ProductInfo;
import java.util.Map;
import java.math.BigDecimal;

public interface CartService {

    void addItemQty(ProductInfo productInfo, int qty);

    void removeItemQty(ProductInfo productInfo, int qty);

    void removeItem(ProductInfo productInfo);

    Map<ProductInfo, Integer> findAllItems();

    Integer getItemsQty();

    BigDecimal getSubTotal();
}
