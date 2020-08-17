package com.teamtrace.axiseducation.model;

import javax.persistence.*;

@Entity
@Table(name = "sys_statement")
@Cacheable
public class SystemStatement {
    public static final String PK_TYPE = "TINYINT(3) UNSIGNED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statement_id", updatable = false, nullable = false, columnDefinition = PK_TYPE + " AUTO_INCREMENT")
    private int statementId;
    @Column(name = "sys_key", nullable = false, columnDefinition = "VARCHAR(100)")
    private String key;
    @Column(name = "sys_value", nullable = false, columnDefinition = "VARCHAR(10000)")
    private String value;

    public int getStatementId() {
        return statementId;
    }

    public void setStatementId(int statementId) {
        this.statementId = statementId;
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
