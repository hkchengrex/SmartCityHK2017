package network.interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLSocketFactory;
import network.types.Utility;
import network.configures.HttpConfiguration;
import network.types.HttpAuth;
import network.HttpResponse;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;

import java.awt.image.BufferedImage;

/**
 * Created by cheng on 26/1/2017.
 */

public abstract class HttpRequest implements HttpConfiguration {

    private HttpAuth httpAuth = null;
    private String requestDateTime = null;
    /*
    private HttpsURLConnection connection = null;
    */
    private static final SimpleDateFormat ENCRPYT_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private HttpURLConnection connection = null;
    
    private int responseType = TEXT;
    private static SSLSocketFactory sslSocketFactory ;

    public final static int TEXT = 0 , IMAGE = 1;


    public HttpRequest(HttpAuth httpAuth){
        this.httpAuth = httpAuth;
    }
    public HttpRequest(){}

    public HttpResponse connect(){

        connection = null;
        
        HttpResponse httpResponse = new HttpResponse();
        
        if (Utility.isNetworkConnected()) {
            try {
            	
            	/*
                connection = (HttpsURLConnection) setUrl().openConnection();
                */
            	 connection = (HttpURLConnection) setUrl().openConnection();

                connection.setReadTimeout(HTTP_TIMEOUT);
                connection.setConnectTimeout(HTTP_TIMEOUT);
                
                /*
                if (sslSocketFactory == null){
                    sslSocketFactory = createTrustedCAs(context).getSocketFactory();
                }

                if (sslSocketFactory != null) {
                    connection.setSSLSocketFactory(sslSocketFactory);
                }
                */

                requestDateTime = getCurrentDate();
                setHttpAuthentication(connection,httpAuth,requestDateTime);
                setRequestProperties(connection);
                
                connection.setRequestProperty("user-date",requestDateTime);
                connection.setRequestProperty("Content-Type","application/json;charset=utf-8");

                if (connection.getDoOutput()) {
                    setOutputStream(connection.getOutputStream());
                }

                int responseCode = connection.getResponseCode();
                httpResponse.setState(responseCode);

                switch (responseCode) {
                    case HTTP_OK:
                        if (responseType == TEXT){
                            httpResponse.setResponseBody(readStream(connection.getInputStream()));
                        } else if (responseType == IMAGE) {
                            httpResponse.setResponseImageBody(ImageIO.read(connection.getInputStream()));                          
                        }
                        break;
                    case HTTP_INTERNAL_ERROR:
                        httpResponse.setResponseBody(readStream(connection.getErrorStream()));
                        break;
                    case HTTP_FORBIDDEN:
                        httpResponse.setResponseBody(readStream(connection.getInputStream()));
                        break;
                }


            } catch (Exception e) {
                httpResponse.setState(HttpResponse.HTTP_CONNECTION_FAIL);
            }  finally {
                if (connection != null) {
                    connection.disconnect();
                    connection = null;
                }
            }
        }
        else {
            httpResponse.setState(HttpResponse.HTTP_CONNECTION_FAIL);
        }

        return httpResponse;

    }

    public void closeConnection(){
        if (connection != null){
            connection.disconnect();
            connection = null;
        }
    }
    
    public String getCurrentDate() {
    	return ENCRPYT_DATE_FORMAT.format(new Date());
    }

    public void setReturnType(int type){
        responseType = type;
    }
    protected abstract URL setUrl();
    protected abstract void setRequestProperties(HttpURLConnection connection) throws IOException;
    protected abstract void setOutputStream(OutputStream os) throws IOException;
    protected abstract void setHttpAuthentication(HttpURLConnection connection,HttpAuth httpAuth,String requestDateTime) throws IOException;
  
    private String readStream(InputStream inputStream) throws IOException{

        StringBuffer stringBuffer = new StringBuffer();

        BufferedReader bufferedReader  = new BufferedReader( new InputStreamReader(inputStream) );

        String tempStr ;

        while((tempStr = bufferedReader.readLine()) != null ) {
                stringBuffer.append(tempStr);
        }

        bufferedReader.close();
        inputStream.close();

        return stringBuffer.toString();
    }

    /*
    private SSLContext createTrustedCAs(){

        Certificate ca = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            InputStream ins = con.getResources().openRawResource(R.raw.lets_encrypt_x3_cross_signed);
            ca = cf.generateCertificate(ins);

            ins.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
            return context;
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
       return null;
    }
    */
}
