package com.gzzhsl.pcms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class PreProgressDefault {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer preProgressDefaulId;
    private Integer serialNumber;
    private String planProject;

}
