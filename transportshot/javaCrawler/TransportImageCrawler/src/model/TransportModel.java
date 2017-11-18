package model;




import java.util.List;

import network.HttpCrawlLink;
import network.HttpGet;
import network.HttpResponse;
import network.interfaces.HttpRequest;
import network.types.HttpAuth;
import network.types.HttpParams;
import network.types.HttpUrl;
import parser.Deserializer;
import parser.GeneralInfo;
import types.Shot;


/**
 * Created by cheng on 3/2/2017.
 */

public class TransportModel extends Model implements BaseTransportShotModel {

	public TransportModel(String apiKey , String secretKey){
        super(apiKey,secretKey);
    }
	
    // Get the events Slots , events Name and events ID
    @Override
    public ModelResponse<GeneralInfo> getTransport() {

    	HttpUrl url = new HttpUrl(HttpCrawlLink.getCrawlLink(HttpCrawlLink.GET_TRANSPORT_SHOT));
		HttpParams params = new HttpParams();
		HttpAuth auth = new HttpAuth(apiKey,secretKey);

        HttpRequest request = new HttpGet(url, params, auth);
        HttpResponse httpResponse = request.connect();

        if (!httpResponse.isConnectionFail()) {
            if (!httpResponse.isInternalError()){
                return new ModelResponse<>(new Deserializer().deserializeTransport(httpResponse.getResponseBody()));
            }
            //return new ModelResponse<>(new Deserializer().deserializeError(httpResponse.getResponseBody()));
        }
        return new ModelResponse<>(httpResponse.getState());
    }



}
