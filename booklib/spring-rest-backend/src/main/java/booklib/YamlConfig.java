package booklib;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YamlConfig {
	public String security_config;
	public String getSecurityConfig() {
		return security_config;
	}
	public void setSecurityConfig(String s) {
		this.security_config = s;
	}
}