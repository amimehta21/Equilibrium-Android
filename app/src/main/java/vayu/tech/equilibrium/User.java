package vayu.tech.equilibrium;

public class User {

    private int userId;
    private String name;
    private String email;
    private String password;
    private boolean isAccountActive;
    private boolean canLogin;
    private boolean isUserManager;
    private boolean isAthlete;
    private boolean isDataVisualizer;
    private boolean agreeToPolicy;
    private boolean isLicenseAllocator;
    private int orgId; // optional
    private boolean isPurchaser;
    private boolean isDataCollector; // optional
    private String creationDate;

    // Constructors

    public User(int userId, String name, String email, String password, boolean isAccountActive, boolean canLogin, boolean isUserManager, boolean isAthlete, boolean isDataVisualizer, boolean agreeToPolicy, boolean isLicenseAllocator, int orgId, boolean isPurchaser, boolean isDataCollector, String creationDate) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAccountActive = isAccountActive;
        this.canLogin = canLogin;
        this.isUserManager = isUserManager;
        this.isAthlete = isAthlete;
        this.isDataVisualizer = isDataVisualizer;
        this.agreeToPolicy = agreeToPolicy;
        this.isLicenseAllocator = isLicenseAllocator;
        this.orgId = orgId;
        this.isPurchaser = isPurchaser;
        this.isDataCollector = isDataCollector;
        this.creationDate = creationDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountActive() {
        return isAccountActive;
    }

    public void setAccountActive(boolean accountActive) {
        isAccountActive = accountActive;
    }

    public boolean isCanLogin() {
        return canLogin;
    }

    public void setCanLogin(boolean canLogin) {
        this.canLogin = canLogin;
    }

    public boolean isUserManager() {
        return isUserManager;
    }

    public void setUserManager(boolean userManager) {
        isUserManager = userManager;
    }

    public boolean isAthlete() {
        return isAthlete;
    }

    public void setAthlete(boolean athlete) {
        isAthlete = athlete;
    }

    public boolean isDataVisualizer() {
        return isDataVisualizer;
    }

    public void setDataVisualizer(boolean dataVisualizer) {
        isDataVisualizer = dataVisualizer;
    }

    public boolean isAgreeToPolicy() {
        return agreeToPolicy;
    }

    public void setAgreeToPolicy(boolean agreeToPolicy) {
        this.agreeToPolicy = agreeToPolicy;
    }

    public boolean isLicenseAllocator() {
        return isLicenseAllocator;
    }

    public void setLicenseAllocator(boolean licenseAllocator) {
        isLicenseAllocator = licenseAllocator;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public boolean isPurchaser() {
        return isPurchaser;
    }

    public void setPurchaser(boolean purchaser) {
        isPurchaser = purchaser;
    }

    public boolean isDataCollector() {
        return isDataCollector;
    }

    public void setDataCollector(boolean dataCollector) {
        isDataCollector = dataCollector;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isAccountActive=" + isAccountActive +
                ", canLogin=" + canLogin +
                ", isUserManager=" + isUserManager +
                ", isAthlete=" + isAthlete +
                ", isDataVisualizer=" + isDataVisualizer +
                ", agreeToPolicy=" + agreeToPolicy +
                ", isLicenseAllocator=" + isLicenseAllocator +
                ", orgId=" + orgId +
                ", isPurchaser=" + isPurchaser +
                ", isDataCollector=" + isDataCollector +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }

}
