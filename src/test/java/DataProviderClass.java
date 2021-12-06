import capsUtils.Caps;
import capsUtils.Platform;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.*;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

public class DataProviderClass {
    public static final Map<String, String> env = System.getenv();
    public static final String  USERNAME= env.get("BROWSERSTACK_USERNAME");
    public static final String PASSWORD = env.get("BROWSERSTACK_ACCESS_KEY");

    @DataProvider(name = "capabilities", parallel = true)
    public Object[][] createData() throws IOException {
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        Caps caps = new Caps();

        PreemptiveBasicAuthScheme authenticationScheme = new PreemptiveBasicAuthScheme();
        authenticationScheme.setUserName(USERNAME);
        authenticationScheme.setPassword(PASSWORD);
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://api-cloud.browserstack.com")
                .setBasePath("app-automate")
                .setAuth(authenticationScheme)
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();

        // filters
        Map<String, String> filters = new LinkedHashMap<String, String>();
        filters.put("Platform", "mobile");
        filters.put("Os", "android"); // ios-android
        filters.put("Os_version", "11.0"); // os versions

        // platform: getPlatform
        List<Platform> all_devices = get("devices.json")
                .jsonPath()
                .getList("", Platform.class);
//                .filter(d -> d.getOs().equals("android"))
//                .filter(d -> d.getOs_version().equals("11"))
//                .collect(Collectors.toList());
        List<Platform> filtered_devices = applyFilters(all_devices, filters);
        caps.setPlatformDetails(filtered_devices);
        om.writeValue(new File("src/test/resources/gen-caps.yml"), caps);


        Object[][] table = new Object[caps.getPlatformDetails().size()][6];
        for(int i=0; i<caps.getPlatformDetails().size();i++){
            System.out.println(caps.getPlatformDetails().get(i).getDevice() +"-"+ caps.getPlatformDetails().get(i).getOs_version());
            table[i][0] = caps.getPlatformDetails().get(i).getName();
            table[i][1] = caps.getPlatformDetails().get(i).getOs();
            table[i][2] = caps.getPlatformDetails().get(i).getOs_version();
            table[i][3] = caps.getPlatformDetails().get(i).getBrowser();
            table[i][4] = caps.getPlatformDetails().get(i).getBrowserVersion();
            table[i][5] = caps.getPlatformDetails().get(i).getDevice();

        }


        return table;
    }

    public List<Platform> applyFilters(List<Platform> all_devices, Map<String, String> filters){

        for(Map.Entry<String,String> filter : filters.entrySet()){
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + filter.getValue());
            if(filter.getKey().equals("Platform")) {
                if (filter.getValue().equals("mobile")) all_devices = all_devices.stream().filter(d -> d.isRealMobile()).collect(Collectors.toList());
                else all_devices = all_devices.stream().filter(d -> !d.isRealMobile()).collect(Collectors.toList());
            }
            if(filter.getKey().equals("Os"))
                all_devices = all_devices.stream().filter(d -> d.getOs().equals(filter.getValue())).collect(Collectors.toList());
            if(filter.getKey().equals("Os_version"))
                all_devices = all_devices.stream().filter(d -> d.getOs_version().equals(filter.getValue())).collect(Collectors.toList());
        }
        return all_devices;
    }
}
