package top.mylady.auth.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Component
@FeignClient(value="user-service")
public interface UserClient {

    @PostMapping("/user/login")
    ResponseEntity findUserByName(@RequestParam("username")String username,
                                  @RequestParam("pwd")String pwd);
}
