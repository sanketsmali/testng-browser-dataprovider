package capsUtils;

public class Platform {

    private String name;
    private String os;
    private String osVersion;
    private String browser;
    private String browserVersion;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getOs() {
        return os;
    }
    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
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

}
