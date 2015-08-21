package play.learn.java.design.facade;

public class ComputerFacade {
	private CPU processor;
	private Memory ram;
	private HardDrive hd;
	
	public ComputerFacade() {
		this.processor = new CPU();
		this.ram = new Memory();
		this.hd = new HardDrive();
	}

	public void start() {
		processor.freeze();
		ram.load("x01a2b7e9", hd.read("BOOT_SECTOR", "SECTOR_SIZE"));
		processor.jump("x01a2b7e9");
		processor.execute();
	}

}
