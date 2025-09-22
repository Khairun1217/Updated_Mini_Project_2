package dataProviders;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.testng.annotations.DataProvider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GetTestData {
	@DataProvider(name="getTestData")
	public Object[][] getData(Method method) throws IOException{
		
		String projectPath = System.getProperty("user.dir");
        String filePath = projectPath + "/src/test/resources/testData/testData.json";
     // Read JSON into List<HashMap<String, String>>
        FileReader reader = new FileReader(filePath);
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<List<HashMap<String, String>>>() {}.getType();
        List<HashMap<String, String>> data = gson.fromJson(reader, type);
        reader.close();
        
     // Filter JSON objects matching the current test name
        List<HashMap<String, String>> filtered = new ArrayList<>();
        for (HashMap<String, String> map : data) {
            if (map.get("testName").equalsIgnoreCase(method.getName())) {
                // dynamic email if empty
                if (map.get("username") == null || map.get("username").isEmpty()) {
                    String dynamicEmail = "user_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10) + "@test.com";
                    map.put("username", dynamicEmail);
                }
                filtered.add(map);
            }
        }

        Object[][] returnData = new Object[filtered.size()][1];
        for (int i = 0; i < filtered.size(); i++) {
            returnData[i][0] = filtered.get(i);
        }
        return returnData;
	}
}
