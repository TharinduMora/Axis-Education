package com.teamtrace.axiseducation.api.request;

public class SystemParameterCreateRequest extends Request {
    private short dataType;
    private String key;
    private String value;

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
