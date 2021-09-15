package top.mylady.item.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import top.mylady.item.config.RateLimits;


@Service
public class RateLimitSvcImpl implements RateLimitSvc{

    public boolean perTenantLimitsEnabled = true;
    public String perTenantLimitsConfiguration = "2:30, 5:60";
    public ConcurrentMap<String, RateLimits> perTenantLimits = new ConcurrentHashMap<>();

    @Override
    public boolean execRateLimit(String tenantId) {
        if (perTenantLimitsEnabled) {

            RateLimits rateLimits = perTenantLimits.computeIfAbsent( tenantId, id ->
                    new RateLimits(perTenantLimitsConfiguration)
            );

            if (!rateLimits.tryConsume()) {
                // rateLimits.getAvailableTokens() = 1;
                return false;
            } else {
                // rateLimits.getAvailableTokens() = 0;
                return true;
            }
        }

        return true;
    }
}
