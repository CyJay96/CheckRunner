package ru.clevertec.checkrunner.util.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.util.cache.Cache;
import ru.clevertec.checkrunner.util.factory.Factory;
import ru.clevertec.checkrunner.util.factory.impl.CacheFactory;

import java.util.Optional;

@Aspect
@Configuration
public class ProductCacheAspect {

    private final Cache<Long, Product> cache;

    public ProductCacheAspect(
            @Value("${cache.algorithm}") String cacheType,
            @Value("${cache.capacity}") int cacheCapacity
    ) {
        Factory<Long, Product> factory = new CacheFactory<>();
        cache = factory.getCache(cacheType, cacheCapacity);
    }

    @Around("execution(* ru.clevertec.checkrunner.repository.ProductRepository.save(..))")
    public Product aroundCreateMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Product product = (Product) joinPoint.proceed();
        cache.put(product.getId(), product);
        return product;
    }

    @Around("execution(* ru.clevertec.checkrunner.repository.ProductRepository.findById(..))")
    public Optional<Product> aroundFindByIdMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        if (cache.containsKey(id)) {
            return Optional.of(cache.get(id));
        }
        Optional<Product> productOptional = (Optional<Product>) joinPoint.proceed();
        productOptional.ifPresent(product -> cache.put(product.getId(), product));
        return productOptional;
    }

    @Around("execution(* ru.clevertec.checkrunner.repository.ProductRepository.deleteById(..))")
    public Object aroundDeleteByIdMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        if (cache.containsKey(id)) {
            cache.remove(id);
        }
        return joinPoint.proceed();
    }
}
