package com.example.demo.entity.model;

import com.example.demo.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity implements Serializable{

    final static Logger logger = LoggerFactory.getLogger(BaseEntity.class);

    @Column(name = "status")
    private Integer status = Constants.ACTIVE_STATUS;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
