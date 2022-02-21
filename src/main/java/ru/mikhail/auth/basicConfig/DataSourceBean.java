package ru.mikhail.auth.basicConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@PropertySource(value = "classpath:db.properties")
public class DataSourceBean {
    @Value("${spring.datasource.jdbc-url}")
    private String url;
    @Value("${spring.datasource.driver}")
    private String driverName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dm = new DriverManagerDataSource();
        dm.setDriverClassName(driverName);
        dm.setUrl(url);
        dm.setUsername(username);
        dm.setPassword(password);
        return dm;
    }
}
