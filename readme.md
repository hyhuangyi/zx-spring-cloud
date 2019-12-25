## zx-spring-cloud
* 版本  boot=>2.2.1.RELEASE  cloud =>Hoxton.RELEASE
* 模块及启动顺序 eureka->zuul->oauth2->provider->consumer  comm为公共模块
* 启动 设置堆内存最小(Xms)/最大(Xmx)/新生代(Xmn) nohup java -server -Xms512m -Xmx512m -Xmn256m -jar xx.jar &
* 依次启动后访问文档地址  http://localhost:8001/swagger-ui.html（路由swagger文档总入口）
