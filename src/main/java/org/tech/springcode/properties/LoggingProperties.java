package org.tech.springcode.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author by: 神秘的鱼仔
 * @ClassName: LoggingProperties
 * @Description:
 * @Date: 2024/6/19 20:09
 */
@Component
@ConfigurationProperties(prefix = "logging")
public class LoggingProperties {
    private List<String> includePaths;

    public List<String> getIncludePaths() {
        return includePaths;
    }

    public void setIncludePaths(List<String> includePaths) {
        this.includePaths = includePaths;
    }
}
