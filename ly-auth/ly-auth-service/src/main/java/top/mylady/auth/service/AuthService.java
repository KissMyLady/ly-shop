package top.mylady.auth.service;

import org.springframework.stereotype.Service;
import top.mylady.auth.config.JwtProperties;
import top.mylady.auth.entity.UserInfo;
import top.mylady.auth.utils.JwtUtils;

import javax.annotation.Resource;


@Service
public class AuthService {


    @Resource
    private JwtProperties jwtProperties;

    public String authentication(String username, String password){
        //从数据库查询用户, 校验是否存在用户;

        try {
            String token = JwtUtils.generateToken(new UserInfo(20L, username),
                    jwtProperties.getPrivateKey(),
                    50);
            return token;
        }
        catch (Exception e){
            System.out.println("错误, 原因e: "+ e);
            return null;
        }

    }

}
