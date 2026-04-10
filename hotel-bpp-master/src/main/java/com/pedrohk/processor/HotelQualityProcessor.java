package com.pedrohk.processor;

import com.pedrohk.annotation.AuditLog;
import com.pedrohk.service.CleaningService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HotelQualityProcessor implements BeanPostProcessor {

    private final List<String> processedBeans = new ArrayList<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(AuditLog.class)) {
            processedBeans.add("BEFORE:" + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(AuditLog.class)) {
            processedBeans.add("AFTER:" + beanName);
            if (bean instanceof CleaningService cleaningService) {
                cleaningService.setInspected(true);
            }
        }
        return bean;
    }

    public List<String> getProcessedBeans() {
        return processedBeans;
    }
}
