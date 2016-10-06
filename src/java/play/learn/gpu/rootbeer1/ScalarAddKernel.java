package play.learn.gpu.rootbeer1;


import org.trifort.rootbeer.runtime.Kernel;

public class ScalarAddKernel implements Kernel {
	
	private int[] array;
	private int index;
	
	public ScalarAddKernel(int[] array, int index) {
		this.array = array;
		this.index = index;
	}

	@Override
	public void gpuMethod() {
		array[index] += 1;
	}
}
