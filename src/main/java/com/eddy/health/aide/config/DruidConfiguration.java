package com.eddy.health.aide.config;

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

/**
 * @Author PuaChen
 * @Create 2018-07-16
 */
@Configuration
public class DruidConfiguration {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource createDruid(){
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>(3);

        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","admin");
        //默认就是允许所有访问
        initParams.put("allow","");
        bean.setInitParameters(initParams);
        return bean;
    }


    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String,String> initParams = new HashMap<>(1);
        initParams.put("exclusions","*.js,*.css,/druid/*,*.svg,*.woff2,*.ttf,*.woff,*.eot");

        bean.setInitParameters(initParams);

        bean.setUrlPatterns(Arrays.asList("/*"));

        return  bean;
    }

}
