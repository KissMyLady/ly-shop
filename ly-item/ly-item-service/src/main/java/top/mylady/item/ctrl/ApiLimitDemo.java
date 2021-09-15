package top.mylady.item.ctrl;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.item.service.RateLimitSvcImpl;


@RestController
@RequestMapping
public class ApiLimitDemo {

    private static final Logger logger = LoggerFactory.getLogger(ApiLimitDemo.class);

    //实际上我们一般不会再controller中直接进行rateLimit， 而是在网关处，根据租户id，用户id，应用id，或者ip进行限流。
    //本程序只是RateLimit的例子，不建议直接在生产代码中使用
    @Autowired
    private RateLimitSvcImpl rateLimitSvc;

    @GetMapping(value="/tenants/{tenantId}", produces="application/json;charset=UTF-8")
    public String getDevice(@PathVariable String tenantId) {
        logger.info("打印tenantId: "+ tenantId);

        if (rateLimitSvc.execRateLimit(tenantId)) {
            logger.info("调用Dao访问层, 返回查询的信息 >>>>>>>> ");
            return "Ok";

        } else {
            JSONObject json = new JSONObject();
            json.put("errMsg", "too many requests");
            return json.toJSONString();
        }
    }

}
