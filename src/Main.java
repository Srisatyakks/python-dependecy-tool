

import java.io.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    public static void main(String[] args) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("C:\\Users\\sri.satya\\Desktop\\Dox\\Tool\\src\\resources\\dependencies.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject dependencyList = (JSONObject) obj;

            parseEmployeeObject( dependencyList ) ;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseEmployeeObject(JSONObject dependency) throws IOException {

        Map<String,String> dependencyEntry = (Map<String, String>) dependency.get("dependencies");
        boolean flag=true;
        for (Map.Entry<String,String> entry : dependencyEntry.entrySet()) {
            try {
                Process process = Runtime.getRuntime().exec("pip install " + entry.toString().replaceAll("=","==")  );
                boolean exitCode = process.waitFor(20, TimeUnit.SECONDS);
                System.out.println("\nExited with error code : " + exitCode);
                if(!exitCode){
                    flag=false;
                    System.out.println(entry.toString()+" is not installed");
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }

            //PythonInterpreter
        }
        if(flag){
            System.out.println("Success");
        }

    }
}

