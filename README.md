##NewsWeb

mvn flyway:migrate

mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

目前bug：  
首页带图新闻不能正确跳转   
我的关注里默认显示的不是我关注的所有标签而是所有新闻  
报错
2020-05-04 21:33:12.656  WARN 16108 --- [nio-8080-exec-4] o.m.s.mapper.ClassPathMapperScanner      : No MyBatis mapper was found in '[com.next.newsweb.mapper]' package. Please check your configuration.
2020-05-04 21:33:12 JRebel: Reconfiguring bean 'attentiontagController' [com.next.newsweb.controller.AttentiontagController]
2020-05-04 21:33:59.307  WARN 16108 --- [nio-8080-exec-6] o.m.s.mapper.ClassPathMapperScanner      : No MyBatis mapper was found in '[com.next.newsweb.mapper]' package. Please check your configuration.
2020-05-04 21:34:06 JRebel: Reloading class 'com.next.newsweb.controller.NewsController'.
2020-05-04 21:34:09.886  WARN 16108 --- [nio-8080-exec-1] o.m.s.mapper.ClassPathMapperScanner      : No MyBatis mapper was found in '[com.next.newsweb.mapper]' package. Please check your configuration.
2020-05-04 21:34:10 JRebel: Reconfiguring bean 'newsController' [com.next.newsweb.controller.NewsController]

基本完成  

![示例图片](src/main/resources/static/images/ScreenShot/example.png)
