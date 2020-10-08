package local.com.impl;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import local.com.AbstractThread;
import local.com.Sendable;
import local.com.Senser;
import local.com.StringUtil;

/**
 * @author 박창현
 *
 */
public class DummySenser extends Senser {

	private Sender api;

	private Sender heartbeat;

	private Random rn;

	public DummySenser(String id,String url) {
		super(id, url);
		rn = new Random();
		api = new Sender(new Api(this, 1000));
		heartbeat = new Sender(new Heatbeat(this, 5000));
	}

	String errorMessage = null;

	public void process(int command) {

		switch (command) {
		case START:
			api.start();
			heartbeat.start();
			break;
		case STOP:
			api.stop();
			heartbeat.stop();
			break;

		default:
			break;
		}
	}

	@Override
	public void run() {

		process(START);
	}

	@Override
	public void stop() {
		super.stop();
		process(STOP);
	}


	class Sender extends AbstractThread {

		Sendable sendable;

		public Sender(Sendable sendable) {
			this.sendable = sendable;
		}

		@Override
		public void run() {
			while (flag) {

				try {
					sendable.send();

					Thread.sleep(sendable.period);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					this.stop();
					JOptionPane.showMessageDialog(null, e.getMessage());

				}
			}
		}
	}

	class Heatbeat extends Sendable {

		public Heatbeat(Senser senser, int period) {

			super(senser, period);

		}

		@Override
		public void send() throws Exception {
			senser.update();

			this.senser.getCommandMap();

			url = new URL(urlString + "/heartbeat?name=" + senser.getID());

			System.out.println("call api : " + url);

			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

			InputStream in = new BufferedInputStream(urlConnection.getInputStream());

			JSONObject json = new JSONObject(StringUtil.getStringFromInputStream(in));

			System.out.println(json.toString());

		}
	}

	class Api extends Sendable {


		public Api(Senser senser, int period) {
			super(senser, period);
		}

		@Override
		public void send() throws Exception {

			senser.update();
			HashMap<String, Object> comandMap = this.senser.getCommandMap();

			int temp = (Integer) comandMap.get("temp");

			int hit = (Integer) comandMap.get("hit");

			url = new URL(urlString + "/api?name=pch" + "&temp=" + temp + "&hit=" + hit);

			System.out.println("call api : " + url);

			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

			InputStream in = new BufferedInputStream(urlConnection.getInputStream());

			JSONObject json = new JSONObject(StringUtil.getStringFromInputStream(in));

			System.out.println(json.toString());

		}
	}

	@Override
	public void update() {
		commandMap.put("temp", rn.nextInt(20));
		commandMap.put("hit", rn.nextInt(20));
	}

}
