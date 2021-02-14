package com.example.demo.entity.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "operation")
@Data
public class Operation {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2", parameters = { @org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy") })
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_code")
    private String shortCode;
}
