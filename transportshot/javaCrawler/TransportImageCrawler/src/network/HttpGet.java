package network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import network.interfaces.HttpRequest;
import network.types.HttpAuth;
import network.types.HttpParams;
import network.types.HttpUrl;

/**
 * Created by cheng on 26/1/2017.
 */

public class HttpGet extends HttpRequest {

    private HttpUrl url = null ;
    private HttpParams params = null ;
    private static final String HTTP_GET = "GET";

    public HttpGet(HttpUrl url){
        super();
        this.url = url;
    }

    public HttpGet(HttpUrl url,HttpParams params){
        super();
        this.url = url;
        this.params = params;
    }

    public HttpGet(HttpUrl url,HttpAuth httpAuth){
        super(httpAuth);
        this.url = url;
    }

    public HttpGet(HttpUrl url,HttpParams params,HttpAuth httpAuth){
        super(httpAuth);
        this.url = url;
        this.params = params;
    }

    @Override
    protected URL setUrl() {
        if (params != null){
            return url.getUrl(params);
        }
        return url.getUrl();
    }

    @Override
    protected void setRequestProperties(HttpURLConnection connection) throws IOException {
        connection.setRequestMethod(HTTP_GET);
        connection.setDoInput(true);
    }

    @Override
    protected void setOutputStream(OutputStream os) throws IOException {

    }

	@Override
	protected void setHttpAuthentication(HttpURLConnection connection, HttpAuth httpAuth,String requestDateTime) throws IOException {
		 if (httpAuth!=null){
			   if (params == null) {}
	           connection.setRequestProperty ("authorization", httpAuth.toString(params,requestDateTime));
	       }			
	}
}
