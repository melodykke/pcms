package com.gzzhsl.pcms.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
public class Region {
    @Id
    @GeneratedValue
    private Integer regionId;
    private String regionCode;
    private String regionName;
    private Integer parentId;
}
