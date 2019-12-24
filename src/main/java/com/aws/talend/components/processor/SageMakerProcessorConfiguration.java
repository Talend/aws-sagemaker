package com.aws.talend.components.processor;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.action.Proposable;
import org.talend.sdk.component.api.configuration.constraint.Pattern;
import org.talend.sdk.component.api.configuration.ui.DefaultValue;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.Credential;
import org.talend.sdk.component.api.meta.Documentation;

@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
        @GridLayout.Row({ "awsAccessKey" }),
        @GridLayout.Row({ "awsSecretKey" }),
        @GridLayout.Row({ "awsRegion" }),
        @GridLayout.Row({ "contentType" }),
        @GridLayout.Row({ "accept" }),
        @GridLayout.Row({ "batchSize" }),
        @GridLayout.Row({ "endpointName" })
})
@Documentation("TODO fill the documentation for this configuration")
public class SageMakerProcessorConfiguration implements Serializable {

    @Option
    @Credential
    @Documentation("AWS Access Key")
    private String awsAccessKey;

    @Option
    @Credential
    @Documentation("AWS Secret Key")
    private String awsSecretKey;

    @Option
    @Proposable("listAwsRegions")
    @Documentation("Aws Regions to select from")
    private String awsRegion;

    @Option
    @Proposable("listTypes")
    @Documentation("HTTP Header Content-Type value")
    private String contentType;

    @Option
    @Proposable("listTypes")
    @Documentation("HTTP Header Accept value")
    private String accept;

    @Option
    @DefaultValue("500")
    @Documentation("Number of rows in commit interval to endpoint")
    private int batchSize = 500;

    @Option
    @Pattern("^[a-zA-Z0-9](-*[a-zA-Z0-9])*")
    @Documentation("Configure SageMaker EndPoint")
    private String endpointName;



    public String getAwsAccessKey() {return this.awsAccessKey;}

    public SageMakerProcessorConfiguration setAwsAccessKey(String awsAccessKey) {
        this.awsAccessKey = awsAccessKey;
        return this;
    }

    public String getAwsSecretKey() {return this.awsSecretKey;}

    public SageMakerProcessorConfiguration setAwsSecretKey(String awsSecretKey) {
        this.awsSecretKey = awsSecretKey;
        return this;
    }

    public String getAwsRegion() {return this.awsRegion;}

    public SageMakerProcessorConfiguration setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
        return this;
    }

    public String getContentType() {return this.contentType;}

    public SageMakerProcessorConfiguration setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getAccept() {return this.accept;}

    public SageMakerProcessorConfiguration setAccept(String accept) {
        this.accept = accept;
        return this;
    }

    public int getBatchSize() {return this.batchSize;}

    public SageMakerProcessorConfiguration setBatchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    public String getEndpointName() {return this.endpointName;}

    public SageMakerProcessorConfiguration setEndpoint(String endpointName) {
        this.endpointName = endpointName;
        return this;
    }
}