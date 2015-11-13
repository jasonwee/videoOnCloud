package play.learn.java.concurrent;

import java.util.concurrent.Exchanger;

public class ExchangerRunnable implements Runnable {

	Exchanger exchanger = null;
	Object object = null;

	public ExchangerRunnable(Exchanger exchanger, Object object) {
		this.exchanger = exchanger;
		this.object = object;
	}

	public void run() {
		try {
			Object previous = this.object;

			this.object = this.exchanger.exchange(this.object);

			System.out.println(Thread.currentThread().getName() + " exchanged "
					+ previous + " for " + this.object);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
