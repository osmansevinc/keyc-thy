package com.example.demo.repo;

import com.example.demo.entity.model.Operation;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends GenericRepository<Operation,String> {

}
