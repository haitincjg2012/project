package com.alqsoft.utils.gzyl;

/**
 * Created by JinLei on 2016/10/17.
 */
public enum SystemRetField {
    RET_CODE("retCode"), RET_DESC("retDesc"), RET_BODY("retBody");

    private SystemRetField(String name){
        this.name = name;
    }

    private String name;

    @Override
    public String toString() {
        return this.name;
    }
}
