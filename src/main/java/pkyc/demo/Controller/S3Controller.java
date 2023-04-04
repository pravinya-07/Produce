//package pkyc.demo.Controller;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CopyObjectRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/s3")
//public class S3Controller {
//
//    @Autowired
//    private AmazonS3 s3Client;
//
//
//    @PostMapping(value = "/move",produces = "application/json")
//    public String moveFile(@RequestParam("key") String key, @RequestParam("sourceFolder") String sourceFolder, @RequestParam("destinationFolder") String destinationFolder) throws IOException {
//        // Copy the object from the source folder to the destination folder
//        CopyObjectRequest copyObjectRequest = new CopyObjectRequest("rpk-custdata-dev", sourceFolder + "/" + key, "rpk-custdata-dev", destinationFolder + "/" + key);
//        s3Client.copyObject(copyObjectRequest);
//
//        // Delete the object from the source folder
//        s3Client.deleteObject("rpk-custdata-dev", sourceFolder + "/" + key);
//
//        // Return a success message
//        return "File "+key+" moved successfully!";
//    }
//
//    @GetMapping("/list")
//    public List<String> listObjects(@RequestParam("prefix") String prefix) {
//        // List all objects in the specified folder with the specified prefix
//        List<String> objectKeys = s3Client.listObjects("rpk-custdata-dev", prefix).getObjectSummaries().stream()
//                .map(s3ObjectSummary -> s3ObjectSummary.getKey())
//                .collect(Collectors.toList());
//        System.out.println("Objects in "+prefix+" are "+objectKeys);
//        // Return the list of object keys
//        return objectKeys;
//    }
//}