package com.teamtrace.axiseducation.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Region;
import com.teamtrace.axiseducation.AxisEducationConfiguration;
import com.teamtrace.axiseducation.api.request.Request;
import com.teamtrace.axiseducation.api.response.FileUploadResponse;
import com.teamtrace.axiseducation.service.FileService;
import com.teamtrace.axiseducation.util.constant.Statuses;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

public class FileServiceImpl implements FileService {
    static java.util.logging.Logger logger = Logger.getLogger(FileServiceImpl.class.getName());
    private final String imageRoot;
    private final String imageStorage;
    private final String awsAccessKey;
    private final String awsSecretKey;
    private final String s3Bucket;

    public FileServiceImpl(AxisEducationConfiguration configuration) {
        imageRoot = configuration.imageRoot;
        imageStorage = configuration.imageStorage;
        awsAccessKey = configuration.awsAccessKey;
        awsSecretKey = configuration.awsSecretKey;
        s3Bucket = configuration.s3Bucket;
    }

    public FileUploadResponse uploadImage(InputStream is, String type, Request api) {
        if ("S3".equalsIgnoreCase(imageStorage)) {
            return uploadImageS3(is, type, api);
        } else {
            return uploadImageFile(is, type, api);
        }
    }

    private FileUploadResponse uploadImageFile(InputStream fileInputStream, String type, Request api) {
        FileUploadResponse response = new FileUploadResponse();
        response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
        try {
            String fileName = System.nanoTime() + ".jpg";

            File to = new File(imageRoot + File.separator + File.separator + fileName);
            to.getParentFile().mkdirs();

            java.nio.file.Files
                    .copy(fileInputStream, to.toPath(), StandardCopyOption.REPLACE_EXISTING);

            response.setFileName("/" + fileName);
            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

    private FileUploadResponse uploadImageS3(InputStream fileInputStream, String type, Request api) {
        FileUploadResponse response = new FileUploadResponse();
        response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
        try {
            String fileName = "/" + System.nanoTime() + ".jpg";
            File to = new File(imageRoot + File.separator + File.separator + fileName);
            to.getParentFile().mkdirs();

            java.nio.file.Files
                    .copy(fileInputStream, to.toPath(), StandardCopyOption.REPLACE_EXISTING);

            AWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
            AmazonS3 s3client = new AmazonS3Client(credentials);

            s3client.setRegion(Region.AP_Singapore.toAWSRegion());

            s3client.putObject(new PutObjectRequest(s3Bucket, fileName, to)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            to.delete();

            response.setFileName(fileName);
            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response;
    }


}
