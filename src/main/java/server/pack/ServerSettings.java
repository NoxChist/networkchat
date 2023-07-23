package server.pack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ServerSettings {
    public static final int DEFAULT_PORT = 8085;
    private int port;

    public int getPort() {
        return port;
    }
    public static ServerSettings getSettingsFromJson(String path, String fileName){
        File jsonFile =new File(path, fileName);
        ServerSettings settings = null;
        if(jsonFile.exists()&&jsonFile.isFile()){
            try{
                String jsonStr = Files.readString(jsonFile.toPath());
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                settings = gson.fromJson(jsonStr,ServerSettings.class);
            }catch(IOException e){
            }
        }
        return settings;
    }
}
