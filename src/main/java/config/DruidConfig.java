package config;




import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class DruidConfig {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean

    public DataSource druid(){
        return  new DruidDataSource();
    }

    //配置一个Druid的监控
    //1.配置一个后台Servlet

    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet());
        Map<String,String> initParams = new HashMap<>();
        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","cmq521000");
        initParams.put("allow","");//默认允许所有访问
        initParams.put("deny","192.168.11.128");

        bean.setInitParameters(initParams);
        return  bean;
    }


    //配置一个WEB监控的FILTER
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String,String> initParam = new HashMap<>();
        initParam.put("exclusions","*.js,*.css,/druid/*");

        bean.setInitParameters(initParam);

        bean.setUrlPatterns(Arrays.asList("/*"));

        return bean;



    }

}
