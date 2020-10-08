package local.com;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

public abstract class Senser extends AbstractThread {

	protected String id;

	public final int START = 1;

	public final int STOP = 2;

	protected HashMap<String, Object> commandMap;

	public HashMap<String, Object> getCommandMap() {
		return commandMap;
	}

	protected URL url;

	protected String urlString;

	private List<IFMonitor> linsters;

	public void addLisnter(IFMonitor monitor) {
		linsters.add(monitor);
	}

	public Senser(String id, String url) {
		this.id = id;
		urlString = url;
		commandMap = new HashMap<String, Object>();
		linsters = new LinkedList<IFMonitor>();
	}

	public void login() throws Exception {

		try {
			url = new URL(urlString + "/login?name=pch");

			System.out.println("regist:" + url);

			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

			InputStream in = new BufferedInputStream(urlConnection.getInputStream());

			JSONObject json = new JSONObject(StringUtil.getStringFromInputStream(in));

			System.out.println(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("not resist");
		}
	}

	public void regist() throws Exception
	{

	}

	public void heartbeat() throws Exception {

	}

	public abstract void update();

	public String getID() {
		// TODO Auto-generated method stub
		return id;
	}



}
