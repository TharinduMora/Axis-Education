package com.teamtrace.axiseducation.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class SearchCriteria extends Request {
    private int PK;
    @JsonIgnore
    private String KEY;
    private List<Integer> PKs;
    private int offset;
    private int limit = 1000;
    private List<String> searchKeys;
    private List<Object> values;
    @ApiModelProperty(value = "EQ - equal, GT - greater than, GTE - greater than or equal, LT - less than, LTE - less than or equal, LIKE - like")
    private List<String> operators;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "Format yyyy-MM-dd")
    private Date fromDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "Format yyyy-MM-dd")
    private Date toDate;
    @ApiModelProperty(value = "Key for order by operation.")
    private String orderByKey;
    @ApiModelProperty(value = "ASC - ascending, DESC descending")
    private String orderByValue;
    private List<Integer> statuses;
    @ApiModelProperty(value = "DATE - day wise,MONTH - month wise, YEAR - year wise")
    private String groupBy;

    public int getPK() {
        return PK;
    }

    public void setPK(int PK) {
        this.PK = PK;
    }

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public List<Integer> getPKs() {
        return PKs;
    }

    public void setPKs(List<Integer> PKs) {
        this.PKs = PKs;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<String> getSearchKeys() {
        return searchKeys;
    }

    public void setSearchKeys(List<String> searchKeys) {
        this.searchKeys = searchKeys;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public List<String> getOperators() {
        return operators;
    }

    public void setOperators(List<String> operators) {
        this.operators = operators;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getOrderByKey() {
        return orderByKey;
    }

    public void setOrderByKey(String orderByKey) {
        this.orderByKey = orderByKey;
    }

    public String getOrderByValue() {
        return orderByValue;
    }

    public void setOrderByValue(String orderByValue) {
        this.orderByValue = orderByValue;
    }

    public List<Integer> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Integer> statuses) {
        this.statuses = statuses;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }
}
