package local.com;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.swing.JOptionPane;

import org.json.JSONObject;

/**
 * @author 박창현
 *
 */
public class Main implements Runnable {

	Thread thread;

	URL url;

	String urlString;

	boolean flag = false;

	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;

		StringBuilder sb = new StringBuilder();

		String line;

		try {

			br = new BufferedReader(new InputStreamReader(is));

			while ((line = br.readLine()) != null) {

				sb.append(line);

			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			if (br != null) {

				try {

					br.close();

				} catch (IOException e) {

					e.printStackTrace();

				}
			}
		}

		return sb.toString();

	}

	public void callAPI(int temp, int hit) throws IOException {

			System.out.println("call api : " + temp + ", " + hit);
			url = new URL(urlString + "?name=pch" + "&temp=" + temp + "&hit=" + hit);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());

			JSONObject json = new JSONObject(getStringFromInputStream(in));

			System.out.println(json.toString());


	}

	public Main(String url) {
		urlString = url;
	}

	public void start() {
		if (thread == null) {
			flag = true;
			thread = new Thread(this);
			thread.start();
		}
	}

	Random rn;

	@Override
	public void run() {

		rn = new Random();
		while (flag) {
			try {
				callAPI(rn.nextInt(20), rn.nextInt(20));
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {

				flag = false;
				JOptionPane.showMessageDialog(null, e.getMessage());
				//e.printStackTrace();
			}
		}

	}

	public void stop() {
		flag = false;
		thread = null;

	}

}
