package com.eddy.health.aide;

import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author PuaChen
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.eddy.health.aide.dao"})
@ServletComponentScan
@Log4j2
public class AideApplication {

    public static void main(String[] args) {
        log.info("-----启动中-----");
        SpringApplication.run(AideApplication.class, args);
        log.info("-----启动完成-----");
    }

}
