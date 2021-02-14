package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository <T, ID extends Serializable> extends JpaRepository<T, ID>
{
    public void insert(T entity);

    public void update(T entity);

    public Optional<T> softDeleteById(ID id);

    public void deleteAll();

    public List<T> findAll();

    public Optional<T> findById(ID id);
}
