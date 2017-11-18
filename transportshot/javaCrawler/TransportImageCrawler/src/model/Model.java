package model;

import network.interfaces.HttpRequest;

/**
 * Created by cheng on 23/2/2017.
 */

public class Model {

    protected String apiKey;
    protected String secretKey;
    protected HttpRequest request;

    public Model(String apiKey , String secretKey){
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }
    
    public Model(){
    }

    public void terminateRequest(){
        if ( request != null){
            request.closeConnection();
        }
    }

}
