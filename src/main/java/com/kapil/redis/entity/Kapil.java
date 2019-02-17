package com.kapil.redis.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Kapil  implements Serializable {

    private String name;
    private String id;

    public Kapil(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
