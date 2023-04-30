package ru.clevertec.checkrunner.util.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.util.cache.Cache;
import ru.clevertec.checkrunner.util.factory.Factory;
import ru.clevertec.checkrunner.util.factory.impl.CacheFactory;

import java.util.List;
import java.util.Optional;

@Aspect
@Configuration
public class DiscountCardCacheAspect {

    private final Cache<Long, DiscountCard> cache;

    public DiscountCardCacheAspect(
            @Value("${app.cache.algorithm}") String cacheType,
            @Value("${app.cache.capacity}") int cacheCapacity
    ) {
        Factory<Long, DiscountCard> factory = new CacheFactory<>();
        cache = factory.getCache(cacheType, cacheCapacity);
    }

    @Around("execution(* ru.clevertec.checkrunner.repository.DiscountCardRepository.save(..))")
    public DiscountCard aroundCreateMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        DiscountCard discountCard = (DiscountCard) joinPoint.proceed();
        cache.put(discountCard.getId(), discountCard);
        return discountCard;
    }

    @Around("execution(* ru.clevertec.checkrunner.repository.DiscountCardRepository.findById(..))")
    public Optional<DiscountCard> aroundFindByIdMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        if (cache.containsKey(id)) {
            return Optional.of(cache.get(id));
        }
        Optional<DiscountCard> discountCardOptional = (Optional<DiscountCard>) joinPoint.proceed();
        discountCardOptional.ifPresent(discountCard -> cache.put(discountCard.getId(), discountCard));
        return discountCardOptional;
    }

    @Around("execution(* ru.clevertec.checkrunner.repository.DiscountCardRepository.deleteById(..))")
    public Object aroundDeleteByIdMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        if (cache.containsKey(id)) {
            cache.remove(id);
        }
        return joinPoint.proceed();
    }

    @Around("execution(* ru.clevertec.checkrunner.repository.DiscountCardRepository.delete(..))")
    public Object aroundDeleteMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        DiscountCard discountCard = (DiscountCard) joinPoint.getArgs()[0];
        if (cache.containsKey(discountCard.getId())) {
            cache.remove(discountCard.getId());
        }
        return joinPoint.proceed();
    }

    @Around("execution(* ru.clevertec.checkrunner.repository.DiscountCardRepository.existsByNumber(..))))")
    public boolean aroundExistsByNumberMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Long number = (Long) joinPoint.getArgs()[0];
        List<DiscountCard> cacheDiscountCardList = cache.values().stream()
                .filter(discountCard -> number.equals(discountCard.getNumber()))
                .toList();
        if (!cacheDiscountCardList.isEmpty()) {
            return true;
        }
        return (boolean) joinPoint.proceed();
    }

    @Around("execution(* ru.clevertec.checkrunner.repository.DiscountCardRepository.findAllByNumber(..))))")
    public List<DiscountCard> aroundFindByNumberMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Long number = (Long) joinPoint.getArgs()[0];
        List<DiscountCard> cacheDiscountCardList = cache.values().stream()
                .filter(discountCard -> number.equals(discountCard.getNumber()))
                .toList();
        if (!cacheDiscountCardList.isEmpty()) {
            return cacheDiscountCardList;
        }
        List<DiscountCard> discountCardList = (List<DiscountCard>) joinPoint.proceed();
        discountCardList.forEach(discountCard -> cache.put(discountCard.getId(), discountCard));
        return discountCardList;
    }
}
