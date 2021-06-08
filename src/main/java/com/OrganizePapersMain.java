package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhouyong
 * @date 2020/11/30 15:52
 */
@SpringBootApplication
@MapperScan("com.papers.mapper")
@EnableTransactionManagement
public class OrganizePapersMain {
    public static void main(String[] args) {
        SpringApplication.run(OrganizePapersMain.class);
    }
}
