package play.learn.java.design.service;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// this is also adapter pattern
public abstract class AbstractService implements Service {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected AtomicBoolean started = new AtomicBoolean(false);
	protected AtomicBoolean inited = new AtomicBoolean(false);

	@Override
	public void init() {
		if (!inited.get()) {
			initService();
			inited.set(true);
			logger.debug("{} initialized.", this);
		}
	}

	@Override
	public void start() {
		// Init service if it has not done so.
		if (!inited.get()) {
			init();
		}
		// Start service now.
		if (!started.get()) {
			startService();
			started.set(true);
			logger.debug("{} started.", this);
		}

	}

	@Override
	public void stop() {
		if (started.get()) {
			stopService();
			started.set(false);
			logger.debug("{} stopped.", this);
		}

	}

	@Override
	public void destroy() {
		// Stop service if it is still running.
		if (started.get()) {
			stop();
		}
		// Destroy service now.
		if (inited.get()) {
			destroyService();
			inited.set(false);
			logger.debug("{} destroyed.", this);
		}
	}

	@Override
	public boolean isInited() {
		return inited.get();
	}

	@Override
	public boolean isStarted() {
		return started.get();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[id=" + System.identityHashCode(this) + "]";
	}

	protected void initService() {
	}

	protected void startService() {
	}

	protected void stopService() {
	}

	protected void destroyService() {
	}

}
