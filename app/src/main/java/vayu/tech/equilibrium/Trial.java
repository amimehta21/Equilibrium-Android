package vayu.tech.equilibrium;

import java.io.Serializable;

public class Trial implements Serializable {

    private int trialId;
    private String name;
    private String trialType;
    private boolean isArchived;
    private int userId;
    private int licenseId; // optional
    private String timestamp;

    public Trial(int trialId, String name, String trialType, boolean isArchived, int userId, int licenseId, String timestamp) {
        this.trialId = trialId;
        this.name = name;
        this.trialType = trialType;
        this.isArchived = isArchived;
        this.userId = userId;
        this.licenseId = licenseId;
        this.timestamp = timestamp;
    }

    public Trial(int trialId, String name) {
        this.trialId = trialId;
        this.name = name;
    }

    public int getTrialId() {
        return trialId;
    }

    public void setTrialId(int trialId) {
        this.trialId = trialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrialType() {
        return trialType;
    }

    public void setTrialType(String trialType) {
        this.trialType = trialType;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(int licenseId) {
        this.licenseId = licenseId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Trial{" +
                "trialId=" + trialId +
                ", name='" + name + '\'' +
                ", trialType='" + trialType + '\'' +
                ", isArchived=" + isArchived +
                ", userId=" + userId +
                ", licenseId=" + licenseId +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

}
