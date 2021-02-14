package com.example.demo.config.factory;

import com.example.demo.repo.impl.GenericRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class BaseRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
        extends JpaRepositoryFactoryBean<R, T, I> {

    public BaseRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
        // TODO Auto-generated constructor stub
    }

    protected RepositoryFactorySupport createRepositoryFactory(
            EntityManager entityManager) {
        return new BaseRepositoryFactory(entityManager);
    }

    private static class BaseRepositoryFactory<T, I extends Serializable> extends
            JpaRepositoryFactory {

        private EntityManager entityManager;

        public BaseRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.entityManager = entityManager;
        }


        @SuppressWarnings({ "unchecked", "rawtypes" })
        protected Object getTargetRepository(
                RepositoryMetadata metadata, EntityManager entityManager) {

            Class<?> repositoryInterface = metadata.getRepositoryInterface();

            JpaEntityInformation<?, Serializable> entityInformation =
                    getEntityInformation(metadata.getDomainType());

            return new GenericRepositoryImpl(entityInformation, entityManager); //custom implementation
        }

        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return GenericRepositoryImpl.class;
        }
    }
}
