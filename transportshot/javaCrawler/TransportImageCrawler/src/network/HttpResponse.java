package network;

import java.util.HashMap;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

import java.awt.image.BufferedImage;

/**
 * Created by fung on 2017/3/4.
 */

public class HttpResponse {


    private HashMap<String,String> responseHeaders;
    private String responseBody;
    public final static int HTTP_CONNECTION_FAIL = -1;
    private int state = HTTP_CONNECTION_FAIL;
    private BufferedImage responseImageBody;
    
    public BufferedImage getResponseImageBody() {
        return responseImageBody;
    }

    public void setResponseImageBody(BufferedImage responseImageBody) {
        this.responseImageBody = responseImageBody;
    }  

    public HttpResponse(String responseBody, int state) {
        this.responseBody = responseBody;
        this.state = state;
    }

    public HashMap<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(HashMap<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public void setResponseBody(String responseBody) {

        this.responseBody = responseBody;
    }

    public HttpResponse() {
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public int getState() {
        return state;
    }

    public boolean isForBid(){
        return state == HTTP_FORBIDDEN;
    }

    public boolean isInternalError(){
        return state == HTTP_UNAUTHORIZED;
    }
    
    public boolean isUNAUTHORIZED(){
        return state == HTTP_INTERNAL_ERROR;
    }

    public boolean isConnectionFail(){ return state == HTTP_CONNECTION_FAIL;}
}
