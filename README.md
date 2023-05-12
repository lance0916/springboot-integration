# SpringBoot 集成示例

# Web-Template
- Knife4j（Swagger）
  - 配置类：[Knife4jConfig.java](web-template%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fconfig%2FKnife4jConfig.java)
  - 示例Controller：[Knife4jController.java](web-template%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fcontroller%2Fknife4j%2FKnife4jController.java)
- 日志模版配置
  - logback：[logback-template.xml](web-template%2Fsrc%2Fmain%2Fresources%2Flogback-template.xml)
  - log4j：[log4j2-template.xml](web-template%2Fsrc%2Fmain%2Fresources%2Flog4j2-template.xml)
- 分布式链路日志追踪
  - 配置 [TraceFilter.java](web-template%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Ffilter%2FTraceFilter.java) 已进行 traceid、pspanid、spanid 的解析与生成
  - RestTemplate 支持往下传递 traceid、spanid
  - logback 日志格式支持打印 traceid、pspanid、spanid
- MyBatis Generator
  - 集成 mybatis-generator-maven-plugin Maven 插件
  - 默认配置 [mybatis-generator-config.xml](web-template%2Fsrc%2Fmain%2Fresources%2Fmybatis-generator-config.xml)
  - 在 pom.xml 中的 properties 进行 jdbc 参数的配置
- 工具类配置
  - 64进制编码：[SixTwoRadixUtil.java](web-template%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Futil%2FSixTwoRadixUtil.java)
  - Jwt工具类：[JwtUtil.java](web-template%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Futil%2FJwtUtil.java)
  - SpringMVCUtil，获取 HttpServletRequest 和 HttpServletResponse。[SpringMVCUtil.java](web-template%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Futil%2FSpringMVCUtil.java)
- 健康检查接口
  - [HealthCheckController.java](web-template%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fcontroller%2FHealthCheckController.java)