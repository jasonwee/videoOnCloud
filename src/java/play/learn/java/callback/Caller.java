package play.learn.java.callback;

public class Caller {
	
	public static class CallBackImpl implements CallBack {          //class that implements the method to callback defined in the interface
	    public void methodToCallBack() {
	        System.out.println("I've been called back");
	    }
	}
	
    public void register(CallBack callback) {
        callback.methodToCallBack();
    }

    public static void main(String[] args) {
        Caller caller = new Caller();
        CallBack callBack = new CallBackImpl();       //because of the interface, the type is Callback even thought the new instance is the CallBackImpl class. This alows to pass different types of classes that have the implementation of CallBack interface
        caller.register(callBack);
    }

}
