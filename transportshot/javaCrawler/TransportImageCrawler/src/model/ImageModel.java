package model;

import java.awt.image.BufferedImage;

import network.HttpGet;
import network.HttpResponse;
import network.interfaces.HttpRequest;
import network.types.HttpUrl;

/**
 * Created by cheng on 25/3/2017.
 */

public class ImageModel extends Model{


    public ModelResponse<BufferedImage> getImage(String urlLink){

        HttpUrl url = new HttpUrl(urlLink);

        request = new HttpGet(url);
        request.setReturnType(HttpRequest.IMAGE);
        HttpResponse httpResponse = request.connect();

        if (!httpResponse.isConnectionFail()) {
            return new ModelResponse<BufferedImage>(httpResponse.getResponseImageBody());
        }
        return new ModelResponse<>(httpResponse.getState());
    }
}
