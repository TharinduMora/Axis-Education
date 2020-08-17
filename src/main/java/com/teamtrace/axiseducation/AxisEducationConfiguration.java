package com.teamtrace.axiseducation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public class AxisEducationConfiguration extends Configuration {
    @JsonProperty("database")
    public Map database;
    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;
    @Valid
    @NotNull
    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration jerseyClient;
    @Valid
    @NotNull
    @JsonProperty("imageRoot")
    public String imageRoot;
    @Valid
    @NotNull
    @JsonProperty("imageStorage")
    public String imageStorage;
    @Valid
    @NotNull
    @JsonProperty("fbOAuthUrl")
    public String fbOAuthUrl;
    @Valid
    @NotNull
    @JsonProperty("googleOAuthClients")
//    public List<String> googleOAuthClients;
    public String googleOAuthClients;
    @Valid
    @NotNull
    @JsonProperty("pushHost")
    public String pushHost;
    @Valid
    @NotNull
    @JsonProperty("pushPort")
    public int pushPort;
    @Valid
    @NotNull
    @JsonProperty("pushRoot")
    public String pushRoot;
    @Valid
    @NotNull
    @JsonProperty("awsAccessKey")
    public String awsAccessKey;
    @Valid
    @NotNull
    @JsonProperty("awsSecretKey")
    public String awsSecretKey;
    @Valid
    @NotNull
    @JsonProperty("s3Bucket")
    public String s3Bucket;
    @Valid
    @NotNull
    @JsonProperty("googleMapsApiKey")
    public String googleMapsApiKey;
    @Valid
    @NotNull
    @JsonProperty("notificationThreadCount")
    public int notificationThreadCount;
}
