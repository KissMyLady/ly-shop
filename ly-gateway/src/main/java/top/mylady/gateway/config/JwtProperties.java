package top.mylady.gateway.config;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import top.mylady.auth.utils.RsaUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;


@Data
@Configuration
public class JwtProperties {

    private String AbsPath = "H:\\myPprogramming\\javaCode\\ly-Shop\\libs\\keys";

    /**
     * 密钥
     */
    //@Value("${leyou.jwt.secret}")
    private String secret = "leyou@login(Auth}*^31)&mylady%";

    /**
     * 公钥地址
     */
    //@Value("${leyou.jwt.pubKeyPath}")
    private String pubKeyPath = AbsPath+ "\\rsa.pub";

    /**
     * 私钥地址
     */
    //@Value("${leyou.jwt.priKeyPath}")
    private String priKeyPath = AbsPath+  "\\rsa.pri";

    /**
     * token过期时间
     */
    //@Value("${leyou.jwt.expire}")
    private int expire = 60;

    /**
     * 公钥
     */
    private PublicKey publicKey;

    /**
     * 私钥
     */
    private PrivateKey privateKey;

    /**
     * cookie名字
     */
    // @Value("${leyou.jwt.cookieName}")
    private String cookieName = "cookieName";

    /**
     * cookie生命周期
     */
    // @Value("${leyou.jwt.cookieMaxAge}")
    private Integer cookieMaxAge = 30;

    /**
     * 在构造方法之后执行该方法
     */
    @PostConstruct
    public void init(){
        try {
            File pubKey = new File(pubKeyPath);
            File priKey = new File(priKeyPath);
            if (!pubKey.exists() || !priKey.exists()){
                //生成公钥和密钥
                RsaUtils.generateKey(pubKeyPath, priKeyPath, secret);
            }
            //获取
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
        }
        catch (Exception e){
            System.out.println("初始化公钥失败, 抛出异常, 原因e: "+ e);
            throw new RuntimeException();
        }
    }
}
