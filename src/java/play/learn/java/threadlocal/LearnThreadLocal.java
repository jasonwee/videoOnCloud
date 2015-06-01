package play.learn.java.threadlocal;

public class LearnThreadLocal {
	
	private ThreadLocal<String> threadLocal = new ThreadLocal<String>();
	
	
	public LearnThreadLocal() {

		// should be null
		System.out.println(threadLocal.get());
		
		threadLocal.set("a thread local value");
		threadLocal.set("another thread local value");
		
		System.out.println(threadLocal.get());
		
		
		
		threadLocal = new ThreadLocal<String>() {

			@Override
			protected String initialValue() {
				// TODO Auto-generated method stub
				return "Initial value";
			}
			
		};
		// initial value should not be null anymore
		System.out.println(threadLocal.get());
		
		// java 8
		threadLocal = ThreadLocal.withInitial(() -> "initial value 123");
		System.out.println(threadLocal.get());
		
		
	}
	
	public static void main(String[] args) {
		// new LearnThreadLocal();
		
		MyRunnable sharedRunnableInstance = new MyRunnable();
		
		Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);

        thread1.start();
        thread2.start();
	}
	
    public static class MyRunnable implements Runnable {

        private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

        @Override
        public void run() {
        	// no need synchrnized
            threadLocal.set( (int) (Math.random() * 100D) );
    
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
    
            System.out.println(threadLocal.get());
        }
    }


}
