import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import model.ImageModel;
import model.ModelResponse;
import model.TransportModel;
import types.Shot;

public class main {

	static String filelocation = "~/code/SmartCityHK2017/data/img";
	static final int INAGE_TO_GET[] = { 14, 63, 65, 82, 88, 102, 124, 143, 154 };
	static final String API_KEY = "FA2AF4E76D8C03BE";
	static final String SECRET_KEY = "wnQHQiV1mydfyzEOyAyaoztfeCAIW5iH";
	static int timesd = 0;

	public static void main(String[] args){
		gui();
	}

	public static void repeatedllyGetShots(){
		System.out.println("----GetImageData----");
		final long PERIOD_IN_SECOND = 120;
		List<Shot> shot = getShotsData();
		if (shot == null) {
			System.out.println("Error in getting ImageData");
			return;
		}
		System.out.println("----End----");
		System.out.println("----GetImageRepeatedly----(Period : " + PERIOD_IN_SECOND + "s )");
		Timer timer = new Timer();
		final TimerTask getImagesTask = new TimerTask() {
			int times = 0;

			@Override
			public void run() {
				getSpecificShots(shot, times);
				System.out.println("----Wait for next turn----");
				times++;
				timesd = times;
			}
		};
		timer.schedule(getImagesTask, 0, PERIOD_IN_SECOND * 1000);
	}

	public static void getAllShots() throws IOException {
		System.out.println("----GetImageData----");
		List<Shot> shot = getShotsData();
		if (shot == null) {
			System.out.println("Error in getting ImageData");
			return;
		}
		System.out.println("----End----");
		System.out.println("----GetImageRepeatedly----");
		for (int i = 0; i < shot.size(); i++) {
			System.out.println(shot.get(i).getImageLink() + " (ImageId: " + i + " )");
			getImage(shot.get(i).getImageLink(), String.valueOf(i));
		}
		System.out.println("End");
	}

	public static void getSpecificShots(List<Shot> shot, int times) {
		System.out.println("----Start----(times : " + times + " )");

		try {
			FileOutputStream signal = new FileOutputStream(filelocation + File.separator + "signal");
			signal.close();
			FileOutputStream lock = new FileOutputStream(filelocation + File.separator + "lock");
			lock.close();
		}catch (Exception e){
			e.printStackTrace();
		}

		//Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
		for (int i = 0; i < shot.size(); i++) {
			for (int j = 0; j < INAGE_TO_GET.length; j++) {
				if (i == INAGE_TO_GET[j]) {
					System.out.println(shot.get(i).getImageLink() + " (ImageId: " + String.valueOf(i) + "-"
							+ shot.get(i).getAddress() + " )");
					try {
						getImage(shot.get(i).getImageLink(), String.valueOf(i) + "-" + shot.get(i).getAddress());
					} catch (IOException e) {
						System.out.println("Error : " + e.getMessage());
					}
				}
			}
		}
		System.out.println("----End----");
		try {
			Files.deleteIfExists(Paths.get(filelocation + File.separator + "lock"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	// -----------------------------------------------------------------------------------------------------//
	public static List<Shot> getShotsData() {
		TransportModel model = new TransportModel(API_KEY, SECRET_KEY);
		return model.getTransport().getResponseBody().getShot();
	}

	public static void getImage(String link, String imageName) throws IOException {
		ModelResponse<BufferedImage> img = new ImageModel().getImage(link);
		if (img.getResponseBody() != null) {
			File file = new File(filelocation + File.separator + imageName + ".jpg");
			ImageIO.write(img.getResponseBody(), "jpg", file);
		}
	}

	public static void gui() {
		JFrame frame = new JFrame("TransportImageCrawler");
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton button = new JButton("Select Directory");
		JButton button2 = new JButton("Start");
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repeatedllyGetShots();	
				button2.setText(String.valueOf(timesd));
				frame.pack();
			}});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) 
				{
					File selectedFile = fileChooser.getSelectedFile();
					filelocation=selectedFile.getAbsolutePath();
					button.setText(filelocation);
					frame.pack();
				}
			}
		});
		frame.add(button2);
		frame.add(button);
		frame.pack();
		frame.setVisible(true);
	}

}
