package com.teamtrace.axiseducation.api.request;

import io.swagger.annotations.ApiModelProperty;

public class StatusUpdateRequest extends Request {
    private int primaryId;
    @ApiModelProperty(value = "0 = CREATED, 1 = PENDING, 2 = APPROVED")
    private short status;

    public int getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(int primaryId) {
        this.primaryId = primaryId;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }
}

