package network.types;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import network.configures.HttpConfiguration;

/**
 * Created by cheng on 27/1/2017.
 */

public class HttpParams extends ArrayList<HttpParam> implements HttpConfiguration {

   @Override
   public String toString(){
 
       StringBuilder paramsString = new StringBuilder();

       try {
           Iterator<HttpParam> it = this.iterator();
           while (it.hasNext()){
               HttpParam param = it.next();

               paramsString.append(URLEncoder.encode(param.getKey(),HTTP_ENCODING));
               paramsString.append("=");
               paramsString.append(URLEncoder.encode(param.getValue().toString(),HTTP_ENCODING));

               if (it.hasNext()){
                   paramsString.append("&");
               }
           }
       } catch (UnsupportedEncodingException e){
    	   System.out.println(this.getClass().getSimpleName()+e.toString());
       }
       return paramsString.toString();
   }

}
