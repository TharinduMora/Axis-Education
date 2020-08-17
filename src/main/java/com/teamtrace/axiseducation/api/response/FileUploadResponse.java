package com.teamtrace.axiseducation.api.response;

public class FileUploadResponse extends ApiResponse {
    private String url;
    private String fileName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
