package network;

/**
 * Created by cheng on 27/1/2017.
 */

public class HttpCrawlLink {

    public final static int GET_TRANSPORT_SHOT = 0;

    public static String getCrawlLink(int link){

        switch(link){
            case GET_TRANSPORT_SHOT: return "http://qg-api.citysdk.cn/hktraffic/trafficSnapshot";
        }
        return null;

    }


}
