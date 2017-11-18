import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import model.ImageModel;
import model.ModelResponse;
import model.TransportModel;
import network.HttpCrawlLink;
import network.HttpGet;
import network.HttpResponse;
import network.types.HttpAuth;
import network.types.HttpParams;
import network.types.HttpUrl;
import types.Shot;

public class main {

	static final String location = "C:\\Users\\fung\\Desktop\\h\\";
	
	public static void main(String[] args) throws IOException {
		
		TransportModel model = new TransportModel("FA2AF4E76D8C03BE","wnQHQiV1mydfyzEOyAyaoztfeCAIW5iH");
		List<Shot> shot = model.getTransport().getResponseBody().getShot();
		for (int i=0;i<shot.size();i++){
			ModelResponse<BufferedImage> img = new ImageModel().getImage(shot.get(i).getImageLink());
			if (img.getResponseBody()!=null) {
				File file = new File(location+i+".jpg");
		        ImageIO.write(img.getResponseBody(), "jpg", file);
			}		
		}
		
	}
}
