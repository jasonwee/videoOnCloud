package play.learn.java.dependencyinjection;

//https://en.wikipedia.org/wiki/Dependency_injection
//An example without dependency injection
public class NoDependencyInjection {
	
    // Internal reference to the service used by this client
    //private Service service;

    // Constructor
    NoDependencyInjection() {
        // Specify a specific implementation in the constructor instead of using dependency injection
        //this.service = new ServiceExample();
    }

    // Method within this client that uses the services
    public String greet() {
        //return "Hello " + service.getName();
    	return null;
    }

}
