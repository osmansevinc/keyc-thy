package com.example.demo.entity.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "template")
@Data
public class Template {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2", parameters = { @org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy") })
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "template_type_id")
    private String templateTypeId;

    @Column(name = "input_params")
    private String inputParams;

    @Column(name = "operation_id")
    private String operationId;

    @Column(name = "operation_text")
    private String operationText;


}
