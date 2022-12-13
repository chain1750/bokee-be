package com.chaincat.bokee;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @author Chain
 */
@Slf4j
@SpringBootApplication
@MapperScan(value = "com.chaincat.bokee.dao.mapper")
public class BokeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BokeeApplication.class, args);
        log.info("bokee application startup.");
    }
}
