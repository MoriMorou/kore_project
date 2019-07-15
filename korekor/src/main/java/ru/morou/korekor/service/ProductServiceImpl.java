package ru.morou.korekor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import ru.morou.korekor.controller.repr.ProductRepr;
import ru.morou.korekor.persist.model.Picture;
import ru.morou.korekor.persist.model.PictureData;
import ru.morou.korekor.persist.model.Product;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Autowired - Аннотация позволяет автоматически установить значение поля.
 * @Transactional - Перед исполнением метода помеченного данной аннотацией начинается транзакция, после выполнения
 * метода транзакция коммитится, при выбрасывании RuntimeException откатывается.
 */

public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ru.morou.korekor.persist.repo.ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ru.morou.korekor.persist.repo.ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<ProductRepr> findAll() {
        return productRepository.findAll().stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductRepr findById(Long id) {
        return new ProductRepr (productRepository.findById(id).get());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(ProductRepr productRepr) throws IOException {
        Product product = (productRepr.getId() != null) ? this.productRepository.findById(productRepr.getId()).get()
                : new Product();
        product.setName(productRepr.getName());
        product.setCategories(productRepr.getCategories());
        product.setBrand(productRepr.getBrand());
        product.setPrice(productRepr.getPrice());
        if (productRepr.getNewPictures() != null) {
            for (MultipartFile newPicture : productRepr.getNewPictures()) {
                logger.info("Product {} file {} size {}", product.getId(),
                        newPicture.getOriginalFilename(), newPicture.getSize());

                if (product.getPictures() == null) {
                    product.setPictures(new ArrayList<>());
                }

                product.getPictures().add(new Picture(newPicture.getOriginalFilename(),
                        newPicture.getContentType(), new PictureData(newPicture.getBytes())));
            }
        }
        this.productRepository.save(product);
    }
}
