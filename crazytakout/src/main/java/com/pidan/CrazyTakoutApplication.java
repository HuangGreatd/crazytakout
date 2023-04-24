package com.pidan;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author 黄大头
 * @date 2023年04月22日 9:48
 */
@SpringBootApplication
@Slf4j
@MapperScan("com.pidan.mapper")
@ServletComponentScan
public class CrazyTakoutApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrazyTakoutApplication.class,args);
        log.info("项目启动成功..");
    }
}
