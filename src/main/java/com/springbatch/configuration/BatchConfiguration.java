package com.springbatch.configuration;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:application.properties")
@ComponentScan(value = {"com.springbatch.*"})
public class BatchConfiguration extends DefaultBatchConfigurer {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;




    @Bean
    public JdbcTemplate getJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setFetchSize(10);
        jdbcTemplate.setDataSource(dataSource());
        jdbcTemplate.setMaxRows(1000);
        jdbcTemplate.setQueryTimeout(10000);
        return jdbcTemplate;
    }


    @Override
    public void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        return  DataSourceBuilder.create()
                .url(url)
                .username(userName)
                .password(password)
                .build();
    }


    @Bean
    public JobLauncher createJobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        return simpleJobLauncher;
    }


    @Bean
    public JobRepository getJobRepository(final DataSource dataSource, final ResourcelessTransactionManager transactionManager) {
        try {
            JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
            factory.setDataSource(dataSource);
            factory.setTransactionManager(transactionManager);
            factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
            factory.setTablePrefix("BATCH_");
            factory.setMaxVarCharLength(1000);
            return factory.getObject();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    @Bean
    public ResourcelessTransactionManager getTransactionManager() {
        return new ResourcelessTransactionManager();
    }


}
