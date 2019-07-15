package ru.morou.korekor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.WebApplicationContext;
import ru.morou.korekor.persist.repo.ProductRepository;
import ru.morou.korekor.persist.repo.RoleRepository;
import ru.morou.korekor.persist.repo.UserRepository;

/**
 * JPA (Java Persistence API)
 */

@Configuration
public class ServiceConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ServiceConfiguration.class);

    @Bean
    public UserService userService(UserRepository userRepository, RoleRepository roleRepository,
                                   BCryptPasswordEncoder passwordEncoder) {
        return new UserServiceJpaImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new ProductServiceImpl(productRepository);
    }

    /**
     * Области видимости бинов:
     * singleton - Возвращает один и тот же экземпляр бина на каждый запрос контейнера Spring IoC (по умолчанию).
     * prototype - Создает и возвращает новый экземпляр бина на каждый запрос.
     * request - Создает и возвращает экземпляр бина на каждый HTTP запрос*.
     * session - Создает и возвращает экземпляр бина для каждой HTTP сессии*.
     * global-session - Создает и возвращает экземпляр бина для глобальной HTTP сессии*.
     * @return
     */
    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public CartService cartService() {
        return new CartServiceImpl();
    }
}
