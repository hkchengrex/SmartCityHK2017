package network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import network.configures.HttpConfiguration;
import network.interfaces.HttpRequest;
import network.types.HttpAuth;
import network.types.HttpParams;
import network.types.HttpUrl;

/**
 * Created by cheng on 27/1/2017.
 */

public class HttpPost extends HttpRequest implements HttpConfiguration {

    private HttpUrl url = null ;
    private HttpParams params = null ;
    private static final String HTTP_POST = "POST";

    public HttpPost(HttpUrl url){
        super();
        this.url = url;
    }

    public HttpPost(HttpUrl url, HttpParams params){
        super();
        this.url = url;
        this.params = params;
    }

    public HttpPost(HttpUrl url,HttpAuth auth){
        super(auth);
        this.url = url;
    }

    public HttpPost(HttpUrl url, HttpParams params, HttpAuth auth){
        super(auth);
        this.url = url;
        this.params = params;
    }

    @Override
    protected URL setUrl() {
        return url.getUrl();
    }

    @Override
    protected void setRequestProperties(HttpURLConnection connection) throws IOException {
        connection.setRequestMethod(HTTP_POST);
        connection.setDoInput(true);
        connection.setDoOutput(true);
    }

    @Override
    protected void setOutputStream(OutputStream os) throws IOException {

        if (params!=null){
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, HTTP_ENCODING));
            writer.write(params.toString());
            writer.flush();
            writer.close();
        }
    }
    
	@Override
	protected void setHttpAuthentication(HttpURLConnection connection, HttpAuth httpAuth,String requestDateTime) throws IOException {
		 if (httpAuth!=null){
	           connection.setRequestProperty ("authorization", httpAuth.toString(params,requestDateTime));
	       }			
	}
}
