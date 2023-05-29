package pkyc.demo.Controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import pkyc.demo.Model.User;
import pkyc.demo.Repositories.UserRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

//Controller to transfer files withing specified date-range and produce records in LOCALSTACK SQS
@RestController
public class UserController {

    //    Autowiring userRepository from Repositories package
    @Autowired
    private UserRepository userRepository;

    //    Autowiring queueMessagingTemplate from config package
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    //    taking value of endpoint url from application properties
    @Value("${cloud.aws.end-point.uri}")
    private String endPoint;

    //    taking value of bucket-name from application properties
    @Value("${application.bucket.name}")
    private String bucketName;

    //    Autowiring AmazonS3 from config package
    @Autowired
    private AmazonS3 s3Client;


    //    Post mapping Rest Api for transfering files from one folder to other within same S3
//    with specified creation-date,last-updated,value(number of records),sourceFolder and destinationFolder
//    And then produce record in the localstack SQS
    public String getBucketName() {
        return bucketName;
    }

    //    Post mapping Rest Api for transfering files from one folder to other within same S3
//    with specified creation-date,last-updated,value(number of records),sourceFolder and destinationFolder
//    And then produce record in the localstack SQS
    @PostMapping("user/transfer")
    public String transferFilesAndProcessUsers(
            @RequestParam("creation_date") LocalDate creationDate,
            @RequestParam("last_updated") LocalDate lastUpdated,
            @RequestParam("value") int value,
            @RequestParam("sourceFolder") String sourceFolder,
            @RequestParam("destinationFolder") String destinationFolder) {
        List<User> userList = userRepository.findByDateRange(creationDate, lastUpdated, value);
        for (User user : userList) {
            Logger log = Logger.getLogger("pkyc");
            if (value != 0 && !user.isFileTransferred) {
                String key = user.getPanId();
                String prefix = key + ".png";
                CopyObjectRequest copyObjectRequest = new CopyObjectRequest(
                        bucketName,
                        sourceFolder + "/" + prefix,
                        bucketName,
                        destinationFolder + "/" + prefix
                );

                // Copy object from the source folder to the destination folder
                s3Client.copyObject(copyObjectRequest);

                // Delete the object from the source folder
                s3Client.deleteObject(bucketName, sourceFolder + "/" + prefix);

                // Set the flag to indicate file transfer
                user.setIsFileTransferred(true);
                userRepository.save(user);

//                Send message to SQS queue
                String message = "File transfer completed for user: " + user.getUserId();
                queueMessagingTemplate.send(endPoint, MessageBuilder.withPayload(message).build());
            } else {
                log.info("Please enter a positive value.");
            }
        }
        return "File transfer for the selected date range completed successfully.";
    }

}







