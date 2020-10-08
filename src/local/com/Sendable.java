package local.com;

import java.net.URL;

public abstract class Sendable {

	public int period;

	protected URL url;
	protected Senser senser;

	public Sendable(Senser senser, int period) {
		this.period = period;
		this.senser = senser;
	}

	public abstract void send() throws Exception;

}
