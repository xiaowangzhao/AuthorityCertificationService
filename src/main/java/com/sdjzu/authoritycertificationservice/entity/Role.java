package com.sdjzu.authoritycertificationservice.entity;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public class Role implements GrantedAuthority,Serializable {

    private int id;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
