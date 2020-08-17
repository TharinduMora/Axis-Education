package com.teamtrace.axiseducation.service;

import com.teamtrace.axiseducation.api.request.Request;
import com.teamtrace.axiseducation.api.response.FileUploadResponse;

import java.io.InputStream;

public interface FileService {
    FileUploadResponse uploadImage(InputStream inputStream, String type, Request api);

    //UpdateResponse deleteImageS3(FileDeleteRequest request);
}
