package bbcat.config;

import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeetlConf {

    @Value("${beetl.template.path}")
    private String templatePath;

    @Value("${beetl.config.path}")
    private String beetlConfigPath;

    @Value("${local.host}")
    private String local;

    @Value("${protocol.type}")
    private String protocol;

    @Value("${server.port}")
    private String port;

    @Value("${static.resources}")
    private String staticResources;

    @Bean(initMethod = "init", name = "beetlConfig")
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
        ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
        String root = null;
        try {
            root = patternResolver.getResource(templatePath).getFile().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        WebAppResourceLoader webAppResourceLoader = new WebAppResourceLoader(root);
        beetlGroupUtilConfiguration.setResourceLoader(webAppResourceLoader);
        beetlGroupUtilConfiguration.setConfigFileResource(patternResolver.getResource(beetlConfigPath));
        return beetlGroupUtilConfiguration;
    }

    @Bean
    public GroupTemplate getGroupTemplate(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetConfig){
        GroupTemplate gt = beetConfig.getGroupTemplate();
        /*定义全局共享变量*/
        Map<String,Object> shared = new HashMap<>(16);
        shared.put("ctxPath", protocol+"://"+local+":"+port+"/"+staticResources);
        shared.put("ctxLocal", protocol+"://"+local+":"+port+"/");
        gt.setSharedVars(shared);
        return gt;
    }

    @Bean(name = "beetlViewResolver")
    public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setSuffix(".html");
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        return beetlSpringViewResolver;
    }
}
