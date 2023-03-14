package ru.clevertec.checkrunner.util.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.util.cache.Cache;
import ru.clevertec.checkrunner.util.factory.Factory;
import ru.clevertec.checkrunner.util.factory.impl.CacheFactory;

import java.util.Optional;

@Aspect
@Configuration
public class ReceiptCacheAspect {

    private final Cache<Long, Receipt> cache;

    public ReceiptCacheAspect(
            @Value("${app.cache.algorithm}") String cacheType,
            @Value("${app.cache.capacity}") int cacheCapacity
    ) {
        Factory<Long, Receipt> factory = new CacheFactory<>();
        cache = factory.getCache(cacheType, cacheCapacity);
    }

//    @Around("execution(* ru.clevertec.checkrunner.repository.ReceiptRepository.save(..))")
//    public Receipt aroundCreateMethod(ProceedingJoinPoint joinPoint) throws Throwable {
//        Receipt receipt = (Receipt) joinPoint.proceed();
//        cache.put(receipt.getId(), receipt);
//        return receipt;
//    }
//
//    @Around("execution(* ru.clevertec.checkrunner.repository.ReceiptRepository.findById(..))")
//    public Optional<Receipt> aroundFindByIdMethod(ProceedingJoinPoint joinPoint) throws Throwable {
//        Long id = (Long) joinPoint.getArgs()[0];
//        if (cache.containsKey(id)) {
////            return Optional.of(cache.get(id));
//        }
//        Optional<Receipt> receiptOptional = (Optional<Receipt>) joinPoint.proceed();
//        receiptOptional.ifPresent(receipt -> cache.put(receipt.getId(), receipt));
//        return receiptOptional;
//    }
//
//    @Around("execution(* ru.clevertec.checkrunner.repository.ReceiptRepository.deleteById(..))")
//    public Object aroundDeleteByIdMethod(ProceedingJoinPoint joinPoint) throws Throwable {
//        Long id = (Long) joinPoint.getArgs()[0];
//        if (cache.containsKey(id)) {
//            cache.remove(id);
//        }
//        return joinPoint.proceed();
//    }
}
