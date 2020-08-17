package com.teamtrace.axiseducation.api.request;

public class GetByPrimaryIdRequest extends Request {
    private int primaryId;

    public int getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(int primaryId) {
        this.primaryId = primaryId;
    }
}
