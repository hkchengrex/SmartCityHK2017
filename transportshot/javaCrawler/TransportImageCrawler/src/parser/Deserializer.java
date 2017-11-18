package parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by cheng on 27/1/2017.
 */

public class Deserializer {

    private static Gson gson ;

    public Deserializer(){

        if (gson == null){
            gson = new GsonBuilder().create();
        }

    }
  
    public GeneralInfo deserializeTransport(String jsonString){
        Type type = new TypeToken<GeneralInfo>(){}.getType();
        return  gson.fromJson(jsonString,type);
    }

   
 }
