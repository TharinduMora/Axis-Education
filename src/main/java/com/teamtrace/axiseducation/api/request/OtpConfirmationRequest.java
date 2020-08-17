package com.teamtrace.axiseducation.api.request;

public class OtpConfirmationRequest extends Request {
    private String loginName;
    private String otp;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
