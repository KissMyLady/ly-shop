package top.mylady.gateway.config;
import com.netflix.zuul.ZuulFilter;
import org.springframework.stereotype.Component;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import top.mylady.auth.utils.JwtUtils;
import top.mylady.common.utils.CookieUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;



@Component
public class AuthFilter extends ZuulFilter{

    private final List<String> allowPaths = new ArrayList<>();

    //Ulr路径白命令
    public List<String> getAllowPaths(){
        allowPaths.add("/api/auth");
        allowPaths.add("/api/search");
        allowPaths.add("/api/user/register");
        allowPaths.add("/api/user/check");
        allowPaths.add("/api/user/code");
        allowPaths.add("/api/item");
        return allowPaths;
    }

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        //获取应用上下文
        RequestContext context = RequestContext.getCurrentContext();
        //从上下文对象中获取请求对象
        HttpServletRequest request = context.getRequest();
        //获取路径
        String requestUrl = request.getRequestURI();

        System.out.println("shouldFilter 过滤器判断是否执行过滤token");


        //判断白名单是否放行
        for (String item: getAllowPaths()){
            if (requestUrl.startsWith(item)){
                System.out.println("判断白名单是否放行 => 不执行过滤");
                return false;  //不执行过滤
            }
        }
        System.out.println("判断白名单是否放行 => 执行过滤器 需要token校验");
        return true;  //执行过滤
    }

    /**
     * 过滤token
     */
    @Override
    public Object run() throws ZuulException {
        //获取应用上下文
        RequestContext context = RequestContext.getCurrentContext();

        //从上下文对象中获取请求对象
        HttpServletRequest request = context.getRequest();

        //获取token
        String token_params = request.getParameter("token");

        String token_cooker = CookieUtils.getCookieValue(request, this.jwtProperties.getCookieName());
        System.out.println("gateway, 获取token过滤器, 打印获取到的token_params: "+token_params+": token_cooker: "+ token_cooker);

        if (token_params != null){
            //校验token
            try {
                JwtUtils.getInfoFromToken(token_params, this.jwtProperties.getPublicKey());
            }
            catch (Exception e){
                System.out.println("错误, 原因e: "+ e);
                return false;
            }
            return null;
        }

        try {
            //校验token
            JwtUtils.getInfoFromToken(token_cooker, this.jwtProperties.getPublicKey());
        }
        catch (Exception e){
            System.out.println("网关过滤器token错误, 原因e: "+ e);
            return false;
        }
        return null;
    }


}
