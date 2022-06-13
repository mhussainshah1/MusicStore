package music.business;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Download implements Serializable {
    private Long downloadId;
    private User user;
    private LocalDateTime downloadDate;
    private String productCode;

    public Download() {
    }

    public Download(User user, LocalDateTime downloadDate, String productCode) {
        this.user = user;
        this.downloadDate = downloadDate;
        this.productCode = productCode;
    }

    public Long getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(Long downloadId) {
        this.downloadId = downloadId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(LocalDateTime downloadDate) {
        this.downloadDate = downloadDate;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}