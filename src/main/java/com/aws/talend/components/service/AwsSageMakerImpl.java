package com.aws.talend.components.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sagemakerruntime.AmazonSageMakerRuntime;
import com.amazonaws.services.sagemakerruntime.AmazonSageMakerRuntimeClientBuilder;
import com.amazonaws.services.sagemakerruntime.model.InvokeEndpointRequest;
import com.amazonaws.services.sagemakerruntime.model.InvokeEndpointResult;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class AwsSageMakerImpl {
    private static AwsSageMakerImpl instance = null;
    private AmazonSageMakerRuntime client = null;
    private InvokeEndpointRequest invokeEndpointRequest = null;
    private String awsAccessKey;
    private String awsSecretKey;
    private String awsRegion;
    private String contentType;
    private String accept;
    private String endpointName;

    private AwsSageMakerImpl(String awsAccessKey, String awsSecretKey, String awsRegion, String contentType,
                             String accept, String endpointName)
    {
        this.awsAccessKey = awsAccessKey;
        this.awsSecretKey = awsSecretKey;
        this.awsRegion = awsRegion;
        this.contentType = contentType;
        this.accept = accept;
        this.endpointName = endpointName;
        init();
    }

    public static AwsSageMakerImpl getInstance(String awsAccessKey, String awsSecretKey, String awsRegion, String contentType,
                                               String accept, String endpointName) {
        if (instance == null)
            instance = new AwsSageMakerImpl(awsAccessKey, awsSecretKey, awsRegion, contentType, accept, endpointName);

        return instance;
    }


    private void init(){
        AWSCredentials credentials = new BasicAWSCredentials(this.awsAccessKey, this.awsSecretKey);

        client = AmazonSageMakerRuntimeClientBuilder
                .standard()
                .withRegion(this.awsRegion)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        invokeEndpointRequest = new InvokeEndpointRequest();
        invokeEndpointRequest.setContentType(this.contentType);
        invokeEndpointRequest.setAccept(this.accept);

    }

    public String invokeEndpoint(String data) throws AwsSageMakerInvokeException
    {
        String body = null;
        try {
            invokeEndpointRequest.setBody(ByteBuffer.wrap(data.getBytes("UTF-8")));
        } catch (java.io.UnsupportedEncodingException e) {}
        invokeEndpointRequest.setEndpointName(this.endpointName);

        InvokeEndpointResult result = client.invokeEndpoint(invokeEndpointRequest);
        int statusCode = result.getSdkHttpMetadata().getHttpStatusCode();
        if (statusCode == 200)
            body = StandardCharsets.UTF_8.decode(result.getBody()).toString();
        else
            throw new AwsSageMakerInvokeException("Status Code: " + statusCode + " Message: " +body);

        return body;
    }
}
