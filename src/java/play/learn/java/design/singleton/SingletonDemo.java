package play.learn.java.design.singleton;

// https://java-design-patterns.com/patterns/singleton/
public class SingletonDemo {
	public static void main(String[] args) {

		// eagerly initialized singleton
		IvoryTower ivoryTower1 = IvoryTower.getInstance();
		IvoryTower ivoryTower2 = IvoryTower.getInstance();
		System.out.printf("ivoryTower1=%s", ivoryTower1);
		System.out.printf("ivoryTower2=%s", ivoryTower2);

		// lazily initialized singleton
		ThreadSafeLazyLoadedIvoryTower threadSafeIvoryTower1 = ThreadSafeLazyLoadedIvoryTower.getInstance();
		ThreadSafeLazyLoadedIvoryTower threadSafeIvoryTower2 = ThreadSafeLazyLoadedIvoryTower.getInstance();
		System.out.printf("threadSafeIvoryTower1=%s", threadSafeIvoryTower1);
		System.out.printf("threadSafeIvoryTower2=%s", threadSafeIvoryTower2);

		// enum singleton
		EnumIvoryTower enumIvoryTower1 = EnumIvoryTower.INSTANCE;
		EnumIvoryTower enumIvoryTower2 = EnumIvoryTower.INSTANCE;
		System.out.printf("enumIvoryTower1=%s", enumIvoryTower1);
		System.out.printf("enumIvoryTower2=%s", enumIvoryTower2);

		// double checked locking
		ThreadSafeDoubleCheckLocking dcl1 = ThreadSafeDoubleCheckLocking.getInstance();
		System.out.printf(dcl1.toString());
		ThreadSafeDoubleCheckLocking dcl2 = ThreadSafeDoubleCheckLocking.getInstance();
		System.out.printf(dcl2.toString());

		// initialize on demand holder idiom
		InitializingOnDemandHolderIdiom demandHolderIdiom = InitializingOnDemandHolderIdiom.getInstance();
		System.out.printf(demandHolderIdiom.toString());
		InitializingOnDemandHolderIdiom demandHolderIdiom2 = InitializingOnDemandHolderIdiom.getInstance();
		System.out.printf(demandHolderIdiom2.toString());
	}
}
