package client.pack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ClientSettings {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8085;

    private String host;
    private int port;

    public String getHost() {
        return host;
    }
    public int getPort() {
        return port;
    }

    public static ClientSettings getSettingsFromJson(String path, String fileName){
        File jsonFile =new File(path, fileName);
        ClientSettings settings = null;
        if(jsonFile.exists()&&jsonFile.isFile()){
            try{
                String jsonStr = Files.readString(jsonFile.toPath());
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                settings = gson.fromJson(jsonStr,ClientSettings.class);
            }catch(IOException e){
            }
        }
        return settings;
    }
}
