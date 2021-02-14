package com.example.demo.config;

import com.example.demo.config.factory.BaseRepositoryFactoryBean;
import com.example.demo.repo.impl.GenericRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.demo.repo",repositoryFactoryBeanClass  = BaseRepositoryFactoryBean.class )
public class JPAConfig
{
}
