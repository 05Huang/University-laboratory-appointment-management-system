package com.mafei.laboratory;

import com.mafei.laboratory.commons.security.TokenProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author wutangsheng
 * @create 2021-02-10 16:51
 * @info
 */
@SpringBootApplication
@EnableConfigurationProperties(TokenProperties.class)
@EnableJpaRepositories
@EntityScan
public class LaboratoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(LaboratoryApplication.class, args);
    }
}