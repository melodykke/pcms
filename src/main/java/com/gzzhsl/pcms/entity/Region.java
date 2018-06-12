package com.gzzhsl.pcms.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


public class Region {

    private Integer regionId;
    private String regionCode;
    private String regionName;
    private Region parent;
    private Set<Region> children = new HashSet<Region>();
}
