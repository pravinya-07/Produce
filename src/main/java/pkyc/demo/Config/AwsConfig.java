package pkyc.demo.Config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Configuring the AWS S3,localstack SQS service with script to process documents and produce records in SQS
@Configuration
public class AwsConfig {

//    taking value of access-key from application properties
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

//    taking value of secret-key from application properties
    @Value("${cloud.aws.credentials.secret-key}")
    private String accessSecret;

//    taking value of regin from application properties
    @Value("${cloud.aws.region.static}")
    private String region;

//    Bean for SQS queue-messaging-template
    @Bean
    public QueueMessagingTemplate queueMessagingTemplate() {
        return new QueueMessagingTemplate(amazonSQSAsync());
    }

//    Bean for AWS S3 service
    @Bean
    public AmazonS3 s3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region).build();
    }

//    Bean for localstack SQS service
    public AmazonSQSAsync amazonSQSAsync() {

        AmazonSQSAsyncClientBuilder amazonSQSAsyncClientBuilder = AmazonSQSAsyncClientBuilder.standard();
        AmazonSQSAsync amazonSQSAsync = null;
        amazonSQSAsyncClientBuilder.withRegion(region);
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, accessSecret);
        amazonSQSAsyncClientBuilder.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials));
        amazonSQSAsync = amazonSQSAsyncClientBuilder.build();
        return amazonSQSAsync;
    }
}