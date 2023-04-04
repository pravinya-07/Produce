package pkyc.demo.Model;

import org.springframework.web.multipart.MultipartFile;

public class TransferRequest {

    private String user_id;
    private String pan;
    private String creation_date;
    private String last_updated;
    private String lender_slug;
    private String sourceFolderName;
    private String destinationFolderName;
    private String bucketName;

    public TransferRequest(String pan, String creation_date, String last_updated, String lender_slug, String sourceFolderName, String destinationFolderName, String bucketName) {
        super();
        this.pan = pan;
        this.creation_date = creation_date;
        this.last_updated = last_updated;
        this.lender_slug = lender_slug;
        this.sourceFolderName = sourceFolderName;
        this.destinationFolderName = destinationFolderName;
        this.bucketName = bucketName;
    }
    public void setPan(String pan) {
        this.pan = pan;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public void setLender_slug(String lender_slug) {
        this.lender_slug = lender_slug;
    }


    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPan() {
        return pan;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public String getLender_slug() {
        return lender_slug;
    }




    public String getBucketName() {
        return bucketName;
    }

    public String getSourceFolderName() {
        return sourceFolderName;
    }

    public void setSourceFolderName(String sourceFolderName) {
        this.sourceFolderName = sourceFolderName;
    }

    public String getDestinationFolderName() {
        return destinationFolderName;
    }

    public void setDestinationFolderName(String destinationFolderName) {
        this.destinationFolderName = destinationFolderName;
    }
}