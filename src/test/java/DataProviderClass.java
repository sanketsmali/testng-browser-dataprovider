import capsUtils.Caps;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.testng.annotations.DataProvider;

import java.io.InputStream;

public class DataProviderClass {


    @DataProvider(name = "capabilities", parallel = true)
    public Object[][] createData() {
        Yaml yaml = new Yaml(new Constructor(Caps.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("caps.yml");
        Caps caps = yaml.load(inputStream);

        Object[][] table = new Object[caps.getPlatformDetails().size()][5];
        for(int i=0; i<caps.getPlatformDetails().size();i++){
            table[i][0] = caps.getPlatformDetails().get(i).getName();
            table[i][1] = caps.getPlatformDetails().get(i).getOs();
            table[i][2] = caps.getPlatformDetails().get(i).getOsVersion();
            table[i][3] = caps.getPlatformDetails().get(i).getBrowser();
            table[i][4] = caps.getPlatformDetails().get(i).getBrowserVersion();
        }


        return table;
    }
}
