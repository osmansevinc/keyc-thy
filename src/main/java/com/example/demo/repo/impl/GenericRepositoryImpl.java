package com.example.demo.repo.impl;

import com.example.demo.entity.model.BaseEntity;
import com.example.demo.repo.GenericRepository;
import com.example.demo.util.Constants;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public class GenericRepositoryImpl<T extends BaseEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements GenericRepository<T, ID>
{
    protected final EntityManager entityManager;

    public GenericRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public GenericRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public void insert(T entity) {
            entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public Optional<T> softDeleteById(ID id) {
        T entity = entityManager.find(this.getDomainClass(), id);
        Optional<T> returned = Optional.empty();
        if (entity != null) {
            entity.setStatus(Constants.PASSIVE_STATUS);
            entityManager.persist(entity);
            returned = Optional.of(entity);
        }
        return returned;
    }

    @Override
    public void deleteAll() {
        List<T> list = findAll();
        for(T entity : list){
            entityManager.remove(entity);
        }
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("SELECT t FROM " + this.getDomainClass().getName() + " t").getResultList();
    }

    @Override
    public Optional<T> findById(ID id) {
        T entity = entityManager.find(this.getDomainClass(), id);
        Optional<T> returned = Optional.empty();
        if (entity != null) {
            returned = Optional.of(entity);
        }
        return returned;
    }
}
