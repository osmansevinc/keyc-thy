package com.example.demo.entity.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "template_type")
@Data
public class TemplateType extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2", parameters = { @Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy") })
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_code")
    private String shortCode;
}
