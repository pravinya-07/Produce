package pkyc.demo.Controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pkyc.demo.Model.User;
import pkyc.demo.Repositories.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AmazonS3 s3Client;

    //Added post request with /addUser
    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    //Added get request with /getAllUser
    @GetMapping("/getAllUser")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    //Added delete request with /deleteAllUser
    @DeleteMapping("/deleteAllUser")
    public void deleteAllUser() {
        userRepository.deleteAll();
    }

    //Added find request with /createdAt
    @PostMapping("user/{creation_date}/{last_updated}/{value}/{sourceFolder}/{destinationFolder}")
    public void findAll(@PathVariable("creation_date") LocalDateTime creation_date,
                   @PathVariable("last_updated") LocalDateTime last_updated,
                   @PathVariable("value") int value,
                   @PathVariable("sourceFolder") String sourceFolder,
                   @PathVariable("destinationFolder") String destinationFolder) {
        List<User> list = userRepository.findByDateRange(creation_date, last_updated, value);
        for (User user : list) {
//            @PostMapping(value = "/move",produces = "application/json")
//            public String moveFile(@RequestParam("sourceFolder") String sourceFolder, @RequestParam("destinationFolder") String destinationFolder) throws
//            IOException {
//             Copy the object from the source folder to the destination folder
            CopyObjectRequest copyObjectRequest = new CopyObjectRequest("rpk-custdata-dev", sourceFolder + "/" + user.getPanId(), "rpk-custdata-dev", destinationFolder + "/" + user.getPanId());
            s3Client.copyObject(copyObjectRequest);

            // Delete the object from the source folder
            s3Client.deleteObject("rpk-custdata-dev", sourceFolder + "/" + user.getPanId());

            System.out.println(user.getPanId());
        }
        }
//    }

        // Return a success message
//        return "File " + key + " moved successfully!";
//    @GetMapping("user/{creation_date}/{last_updated}/{value}")
//    List<User> findAll(@PathVariable("creation_date") LocalDateTime creation_date,
//                       @PathVariable("last_updated") LocalDateTime last_updated,
//                       @PathVariable("value") int value) {
//        return userRepository.findByDateRange(creation_date, last_updated, value);
//    }
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

    @GetMapping("/list")
    public List<String> listObjects(@RequestParam("prefix") String prefix) {
        // List all objects in the specified folder with the specified prefix
        List<String> objectKeys = s3Client.listObjects("rpk-custdata-dev", prefix).getObjectSummaries().stream()
                .map(s3ObjectSummary -> s3ObjectSummary.getKey())
                .collect(Collectors.toList());
        System.out.println("Objects in " + prefix + " are " + objectKeys);
        // Return the list of object keys
        return objectKeys;
    }
}