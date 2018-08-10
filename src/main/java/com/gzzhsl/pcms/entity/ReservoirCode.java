package com.gzzhsl.pcms.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class ReservoirCode {
    @Id
    private Integer reservoirId;
    private String reservoirName;
    private String reservoirCode;
}
