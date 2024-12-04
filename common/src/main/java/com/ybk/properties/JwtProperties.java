package com.ybk.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ybk.jwt")
@Data
public class JwtProperties {

    /**
     * admin生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * leader生成jwt令牌相关配置
     */
    private String leaderSecretKey;
    private long leaderTtl;
    private String leaderTokenName;

    /**
     * leader生成jwt令牌相关配置
     */
    private String refereeSecretKey;
    private long refereeTtl;
    private String refereeTokenName;
}
