package top.mylady.auth.ctrl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import top.mylady.auth.service.AuthService;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/auth")
public class AuthCtrl {

    @Resource
    private AuthService authService;

    @PostMapping("/accredit")
    public ResponseEntity authentication(@RequestParam("username") String username,
                                         @RequestParam("password") String password){

        //调用service服务器, 获取token
        String token = this.authService.authentication(username, password);

        if (token == null){
            return new ResponseEntity<>("sorry, nof find", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(token, HttpStatus.OK);

    }


}
