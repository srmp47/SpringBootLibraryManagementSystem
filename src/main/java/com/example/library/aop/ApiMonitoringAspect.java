package com.example.library.aop;

import com.example.library.services.MonitoringService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ApiMonitoringAspect {

    private final MonitoringService monitoringService;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    @Before("controllerMethods()")
    public void countTotalRequests(JoinPoint joinPoint) {
        monitoringService.incrementTotal();
    }

    @AfterReturning("controllerMethods()")
    public void countSuccessfulRequests() {
        monitoringService.incrementSuccess(getCurrentUsername());
    }

    @AfterThrowing("controllerMethods()")
    public void countFailedRequests() {
        monitoringService.incrementFailure(getCurrentUsername());
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getName())) {
            return auth.getName();
        }
        return "anonymous";
    }
}
