package com.teamtrace.axiseducation.api.request;

public class SystemParameterUpdateRequest extends Request  {
    private int parameterId;
    private String key;
    private String value;

    public int getParameterId() {
        return parameterId;
    }

    public void setParameterId(int parameterId) {
        this.parameterId = parameterId;
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
