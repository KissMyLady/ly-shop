# 乐优项目


## 域名
保证生产, 测试统一, 尽量都采用域名来访问项目    
一级域名: www.leyou.com, leyou.com, leyou.cn    
二级域名: manage.leyou.com, api.leyou.com    


## 端口
- goods-service: 8081    
- auth JWT校验: 8087
- gateWay 网关: 10010


### 服务Api
1, 商品分类: http://127.0.0.1:8081/goods/category/list?pid=2    
2, 品牌分类: http://127.0.0.1:8081/goods/brand/page?page=2&rows=5    


## 项目技术选型    
前端技术:     
- 基础ES6标准     
- JQuery     
- Vue 2.0, Vuetify(UI框架)    
- 前端构建工具: WebPack    
- 前端安装包工具: Npm    
- Vue脚手架: Vue-cli      
- Vue路由: vue-router    
- ajax框架: axios    
- 富文本框架: Quill-Editor    

后端技术:     
- 基于SpringMVC, Spring 5.x和 MyBatis3    
- SpringBoot 2版本    
- SpringCloud    
- Redis 5.0.9 for Windows    
- ElasticSearch    
- Nginx    
- FastDFS    
- MyCat    
- Thymeleaf    
- RabbitMQ    
- MySQL 8.0.22    
- java-JDK11    


### 开发环境    
- IDE: 2021.2    
- JDK: JDK11    
- 项目构建: Maven3以上    
- 版本控制工具: git    


### 项目架构
计划20多台服务器, devops  32核 128G内存

前后端分离
前端分为两部分：
- 后台管理：主要面向的是数据管理人员，采用基于Vue的单页应用开发方式    
- 门户系统：面向的是客户，门户采用的是Vue结合Nuxt实现服务端渲染方式    

后端:
后端采用 SpringCloud的微服务架构, 统一对外提供Rest风格API. 后台管理与门户系统共享API. 微服务中通过JWT方式来识别用户身份, 开放不同接口.    
![lysc.png](https://blog.mylady.top/static/images/blog_sku_img/java/lysc.png)


## 服务器预算清单
计划的服务节点数量：

- nginx：1主，1备    
- Eureka集群：2台    
- Zuul集群：2台    
- 配置中心（ConfigServer）：1台    
- RabbitMQ：2台    
- Redis：3台    
- FastDFS：2台tracker，2台storage    
- mysql数据库：1主，1备，2从    
- elasticsearch：3台    
- 商品服务：1台    
- 文件上传服务：1台    
- SMS服务：2台    
- 搜索服务：2台    
- 静态页服务：1台    
- 用户中心：1    
- 授权中心：2    
- 购物车：1    
- 订单：2    

共计36个服务节点。
