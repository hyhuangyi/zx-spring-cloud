package zx.cn.zuul.configuration;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

/**
 * 分布式swagger文档配置
 */
@Component
@Primary
@Slf4j
public class ZuulSwaggerConfig implements SwaggerResourcesProvider {
    /**版本**/
    private final String version = "2.0";
    @Autowired
    private ZuulProperties properties;
    /**
     * 构建文档列表
     * @return
     */
    @Override
    public List<SwaggerResource> get() {
        //读取属性文件
        Properties p = readProperties();
        List<SwaggerResource> resources = Lists.newArrayList();
        properties.getRoutes().values().stream().forEach((route)->{
            String serviceId = route.getServiceId();
            String group_property = p.getProperty(serviceId);
            //分组
            if(StringUtils.isNotBlank(group_property)){
                String[] groupNames=group_property.split(",");
                for(String groupName:groupNames){
                    resources.add(createResource(serviceId,serviceId,version,groupName));
                }
            }else {//未分组
                resources.add(createResource(serviceId,serviceId,version));
            }
        });
        return resources;
    }

    /**
     * 构建SwaggerResource(不分组)
     * @param name
     * @param location
     * @param version
     * @return
     */
    private SwaggerResource createResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation("/" + location + "/v2/api-docs" );
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

    /**
     * 构建SwaggerResource(分组)
     * @param name
     * @param location
     * @param version
     * @param groupName
     * @return
     */
    private SwaggerResource createResource(String name, String location, String version, String groupName) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation("/" + location + "/v2/api-docs?group=" + groupName);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

    /**
     * 解析 swagger.properties
     * @return
     */
    private Properties readProperties() {
        Properties p = new Properties();
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(ZuulSwaggerConfig.class.getResourceAsStream("/swagger.properties"), "UTF-8");
            p.load(in);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("加载 swagger.properties 属性文件出错,错误信息:" + e.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

}
