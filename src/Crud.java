import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Crud {
    private String filename;
    private String key;
    public Crud() {
    }


    public void setKey(String key) {
        this.key = key;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean isValidKey(String key,String type) throws Exception {
        if(key.equals("")){
            System.out.println("Key Cannot be null");
            return false;
        }
        if(key.length()>32){
            System.out.println("Key Size Exceeded...");
            return  false;
        }
        if(isAlreadyExistKey(key) && type.equals("write")){
            System.out.println("Key Already Exist...");
            return  false;
        }
        if(!isAlreadyExistKey(key) && !type.equals("write")){
            System.out.println("Key Not Found");
            return false;
        }
        return  true;
    }

    private boolean isAlreadyExistKey(String key) throws Exception {
        Map map=readJson();
        Set<String> keys=map.keySet();
        return keys.contains(key);
    }

    public void writeIntoJson(Map<String,String> details) throws Exception {
        JSONObject json=new JSONObject(details);
        JSONObject parentJson=new JSONObject();
        parentJson.put(key,json);
        if(isFileAlreadyPresent()){
            parentJson.putAll(readJson());
        }
        if(details.size()==0 || isFileLessThan1GB(Path.of(filename),json.toJSONString().getBytes().length+10))
        Files.write(Path.of(filename),parentJson.toJSONString().getBytes());

    }
    public boolean isFileAlreadyPresent(){
        Path path= Paths.get(filename);
        return Files.exists(path);
    }
    public boolean isFileLessThan1GB(Path path,int currentSize) throws IOException {
        System.out.println("File size "+Files.size(path)+" Bytes");
        System.out.println("Current data size "+currentSize+" Bytes");
        return (int) (Files.size(path) +currentSize)/ (Math.pow(1024.00, 3)) <= 1;
    }
    public HashMap readJson() throws Exception {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return (HashMap) jsonParser.parse(reader);
    }
    public void deleteJsonData() throws Exception {
        Map data=readJson();
        data.remove(key);
        JSONObject json=new JSONObject(data);
        Files.write(Path.of(filename),json.toJSONString().getBytes());
    }

    public HashMap readJsonWithKey() {
        try {
            FileReader reader = new FileReader(filename);
            JSONParser jsonParser = new JSONParser();
            return (HashMap) ((HashMap) jsonParser.parse(reader)).get(key);
        }catch (Exception e){
            System.out.println(filename +" error message "+e.getMessage());
        }
        return new HashMap();
    }
}
