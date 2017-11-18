package network.types;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cheng on 27/1/2017.
 */

public class HttpUrl {

    private String urlString;

    public HttpUrl(String urlString){
        this.urlString = urlString;
    }

    public URL getUrl(HttpParams params){
        URL url = null;
        try {
            url = new URL(urlString+params.toString());
        } catch (MalformedURLException e) {
           System.out.println(this.getClass().getSimpleName()+e.toString());
        }
        return url;
    }

    public URL getUrl(){
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
           System.out.println(this.getClass().getSimpleName()+e.toString());
        }
        return url;
    }

}
