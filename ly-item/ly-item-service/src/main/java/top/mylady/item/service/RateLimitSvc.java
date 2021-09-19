package top.mylady.item.service;



public interface RateLimitSvc {

    boolean execRateLimit(String tenantId);
}
