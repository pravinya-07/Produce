package pkyc.demo.Model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
//@Document("")
public class User {

    @Id
    private String userId;
    private String lenderSlug;
    private String panNumber;

    private String panId;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;

    public Boolean isFileTransferred;
    public User(String lenderSlug, String panNumber, String panId, LocalDateTime creation_date, LocalDateTime last_updated,Boolean isFileTransferred) {
        super();
        this.lenderSlug = lenderSlug;
        this.panNumber = panNumber;
        this.panId = panId;
        this.creation_date = creation_date;
        this.last_updated = last_updated;
        this.isFileTransferred =isFileTransferred;
    }

//    getters and setters for all above mentioned fields excluding setUserId as it is given by Mongodb by default
    public Boolean getIsFileTransferred() {
        return isFileTransferred;
    }

    public void setIsFileTransferred(Boolean fileTransferred) {
        isFileTransferred = fileTransferred;
    }

    public String getLenderSlug() {
        return lenderSlug;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public String getPanId() {
        return panId;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getLast_updated() {
        return last_updated;
    }


    public void setLenderSlug(String lenderSlug) {
        this.lenderSlug = lenderSlug;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public void setPanId(String panId) {
        this.panId = panId;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public void setLast_updated(LocalDateTime last_updated) {
        this.last_updated = last_updated;
    }

}
