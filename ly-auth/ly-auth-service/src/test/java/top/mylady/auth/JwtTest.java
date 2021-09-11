package top.mylady.auth;


import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.auth.entity.UserInfo;
import top.mylady.auth.utils.JwtUtils;
import top.mylady.auth.utils.RsaUtils;

import java.security.PrivateKey;
import java.security.PublicKey;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTest {

    private static final String pubKeyPath = "H:\\myPprogramming\\javaCode\\ly-Shop\\libs\\keys\\rsa.pub";
    private static final String priKeyPath = "H:\\myPprogramming\\javaCode\\ly-Shop\\libs\\keys\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    /**
     * 1, 这段测试生成密对; 生成时, 请屏蔽掉Before
     */
    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "869");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "张麻子"), privateKey, 50);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoi5byg6bq75a2QIiwiZXhwIjoxNjMxMzQ3NjU4fQ.WMytiVc2OWYoOgiwfvc28Yn-c7Awm_e8VcqQa2V-a0M1I1dkkuC5K7sdjLYBvT6GwU4DPIY4b-374HeuI18hHUvmdKwX-DrC1JGY7G-kB9V5m2UIV6NcribEaATs-oX-KZm0OsudSMIkdZPieSMG-kbLTCcyPmiRKefZZljO6mY";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);

        System.out.println("打印UserInfo: "+ user);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }

}
