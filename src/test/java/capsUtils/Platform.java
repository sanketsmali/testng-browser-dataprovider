package capsUtils;

public class Platform {

    private String name;
    private String device;
    private String os;
    private String os_version;
    private String browser;
    private String browserVersion;
    private boolean real_mobile;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDevice() {
        return device;
    }
    public void setDevice(String device) {
        this.device = device;
    }

    public String getOs() {
        return os;
    }
    public void setOs(String os) {
        this.os = os;
    }

    public String getOs_version() {
        return os_version;
    }
    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getBrowser() {
        return browser;
    }
    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }
    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public boolean isRealMobile() { return real_mobile; }
    public void setRealMobile(boolean real_mobile) { this.real_mobile = real_mobile; }

}
