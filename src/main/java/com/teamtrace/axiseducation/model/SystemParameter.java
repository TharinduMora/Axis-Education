package com.teamtrace.axiseducation.model;

import javax.persistence.*;

@Entity
@Table(name = "sys_parameter")
@Cacheable
public class SystemParameter {
    public static final String PK_TYPE = "TINYINT(3) UNSIGNED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parameter_id", updatable = false, nullable = false, columnDefinition = PK_TYPE + " AUTO_INCREMENT")
    private int parameterId;
    @Column(name = "data_type", nullable = false)
    private short dataType;
    @Column(name = "sys_key", nullable = false, columnDefinition = "VARCHAR(100)")
    private String key;
    @Column(name = "sys_value", nullable = false, columnDefinition = "VARCHAR(10000)")
    private String value;

    public int getParameterId() {
        return parameterId;
    }

    public void setParameterId(int parameterId) {
        this.parameterId = parameterId;
    }

    public short getDataType() {
        return dataType;
    }

    public void setDataType(short dataType) {
        this.dataType = dataType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
