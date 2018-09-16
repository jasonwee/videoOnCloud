package play.learn.java.design.bridge;

public class Car extends Vehicle {

    @Override
    public void manufacture() {
          System.out.println("Manufactoring Car");
          workshops.stream().forEach(workshop -> workshop.work(this));
          System.out.println("Done.");
          System.out.println();
    }
    @Override
    public int minWorkTime() {
    return 10;
    }

}
