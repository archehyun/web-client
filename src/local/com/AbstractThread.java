package local.com;


public abstract class AbstractThread implements Runnable {

	protected Thread thread;

	protected boolean flag = false;

	public void start() {
		if (thread == null) {
			flag = true;
			thread = new Thread(this);
			thread.start();
		}
	}

	public void stop() {
		flag = false;
		thread = null;

	}

}
