package play.learn.gpu.aparapi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.concurrent.CyclicBarrier;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.amd.aparapi.Range;
import com.amd.aparapi.device.Device;
import com.amd.aparapi.device.OpenCLDevice;
import com.amd.aparapi.opencl.OpenCL;
import com.amd.aparapi.opencl.OpenCLAdapter;


@com.amd.aparapi.opencl.OpenCL.Resource("play.learn.gpu.aparapi.mande2.cl")
interface MandelBrot extends OpenCL<MandelBrot> {
	MandelBrot createMandleBrot(
			Range range,
			@Arg("scale") float scale,
			@Arg("offsetx") float offsetx,
			@Arg("offsety") float offsety,
			@GlobalWriteOnly("rgb") int[] rgb);
}

class JavaMandelBrot extends OpenCLAdapter<MandelBrot> implements MandelBrot {
	final int MAX_ITERATIONS = 64;
	
	final int pallette[] = new int[] {
	         -65536,
	         -59392,
	         -53248,
	         -112640,
	         -106752,
	         -166144,
	         -160256,
	         -219904,
	         -279552,
	         -339200,
	         -399104,
	         -985344,
	         -2624000,
	         -4197376,
	         -5770496,
	         -7343872,
	         -8851712,
	         -10425088,
	         -11932928,
	         -13375232,
	         -14817792,
	         -16260096,
	         -16719602,
	         -16720349,
	         -16721097,
	         -16721846,
	         -16722595,
	         -16723345,
	         -16724351,
	         -16725102,
	         -16726110,
	         -16727119,
	         -16728129,
	         -16733509,
	         -16738889,
	         -16744269,
	         -16749138,
	         -16754006,
	         -16758619,
	         -16762976,
	         -16767077,
	         -16771178,
	         -16774767,
	         -16514932,
	         -15662970,
	         -14942079,
	         -14221189,
	         -13631371,
	         -13107088,
	         -12648342,
	         -12320669,
	         -11992995,
	         -11796393,
	         -11665328,
	         -11993019,
	         -12386248,
	         -12845011,
	         -13303773,
	         -13762534,
	         -14286830,
	         -14745588,
	         -15269881,
	         -15728637,
	         -16252927,
	         0
	};
	
	  @Override 
	  public MandelBrot createMandleBrot(Range range, float scale, float offsetx, float offsety, int[] rgb) {

		      final int width = range.getGlobalSize(0);
		      final int height = range.getGlobalSize(1);
		      for (int gridy = 0; gridy < height; gridy++) {
		         for (int gridx = 0; gridx < width; gridx++) {
		            final float x = ((((gridx) * scale) - ((scale / 2.0f) * width)) / width) + offsetx;
		            final float y = ((((gridy) * scale) - ((scale / 2.0f) * height)) / height) + offsety;
		            int count = 0;
		            float zx = x;
		            float zy = y;
		            float new_zx = 0.0f;
		            for (; (count < MAX_ITERATIONS) && (((zx * zx) + (zy * zy)) < 8.0f); count++) {
		               new_zx = ((zx * zx) - (zy * zy)) + x;
		               zy = ((2.0f * zx) * zy) + y;
		               zx = new_zx;
		            }
		            rgb[gridx + (gridy * width)] = pallette[count];

		         }
		      }
		      return (this);
		   }
}

class JavaMandelBrotMultiThread extends OpenCLAdapter<MandelBrot> implements MandelBrot {
	
	final int MAX_ITERATIONS = 64;
	
	final int pallette[] = new int[] {
	         -65536,
	         -59392,
	         -53248,
	         -112640,
	         -106752,
	         -166144,
	         -160256,
	         -219904,
	         -279552,
	         -339200,
	         -399104,
	         -985344,
	         -2624000,
	         -4197376,
	         -5770496,
	         -7343872,
	         -8851712,
	         -10425088,
	         -11932928,
	         -13375232,
	         -14817792,
	         -16260096,
	         -16719602,
	         -16720349,
	         -16721097,
	         -16721846,
	         -16722595,
	         -16723345,
	         -16724351,
	         -16725102,
	         -16726110,
	         -16727119,
	         -16728129,
	         -16733509,
	         -16738889,
	         -16744269,
	         -16749138,
	         -16754006,
	         -16758619,
	         -16762976,
	         -16767077,
	         -16771178,
	         -16774767,
	         -16514932,
	         -15662970,
	         -14942079,
	         -14221189,
	         -13631371,
	         -13107088,
	         -12648342,
	         -12320669,
	         -11992995,
	         -11796393,
	         -11665328,
	         -11993019,
	         -12386248,
	         -12845011,
	         -13303773,
	         -13762534,
	         -14286830,
	         -14745588,
	         -15269881,
	         -15728637,
	         -16252927,
	         0	
	};

	@Override
	public MandelBrot createMandleBrot(Range range, float scale, float offsetx, float offsety, int[] rgb) {
		
		int width = range.getGlobalSize(0);
		final int height = range.getGlobalSize(1);
		final int threadCount = 8;
		final Thread[] threads = new Thread[threadCount];
		final CyclicBarrier barrier = new CyclicBarrier(threadCount + 1);
		
		for (int thread = 0; thread < threadCount; thread++ ) {
			final int threadId = thread;
			final int groupHeight = height / threadCount;
			(threads[threadId] = new Thread(new Runnable() {
				
				@Override
				public void run() {
					for (int gridy = threadId * groupHeight; gridy < ((threadId + 1) * groupHeight); gridy++) {
					for (int gridx = 0; gridx < width; gridx++) {
						float x = ((((gridx) * scale) - ((scale / 2.0f) * width )) / width) + offsetx;
						float y = ((((gridy) * scale) - ((scale / 2.0f) * height)) / height) + offsety;
						int count = 0;
						float zx = x;
						float zy = y;
						float new_zx = 0.0f;
						for (; (count < MAX_ITERATIONS) && (((zx * zx) + (zy * zy)) < 8.0f); count++) {
							new_zx = ((zx * zx) - (zy * zy)) + x;
							zy = ((2.0f * zx) * zy) + y;
							zx = new_zx;
						}
						rgb[gridx + (gridy * width)] = pallette[count];
					}					
				}
				try {
					barrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			})).start();
		}
        try {
        	barrier.await();
       } catch (final Exception e) {
    	   e.printStackTrace();
       }
        return (this);
     }
}

/**
 * java -Djava.library.path=../.. -Dcom.amd.aparapi.executionMode=$1 -classpath ../../aparapi.jar:extension.jar play.learn.gpu.aparapi.MandelExample
 *
 */
public class MandelExample {
	
	public static volatile Point to = null;
	
	public static MandelBrot mandelBrot = null;
	
	public static MandelBrot gpuMandelBrot = null;
	
	public static MandelBrot javaMandelBrot = null;
	
	public static MandelBrot javaMandelBrotMultiThread= null;

	public static void main(String[] args) {
		final JFrame frame = new JFrame("MandelBrot");
		
		// width of mandelbrot view
		final int width = 768;
		
		// height of mandelbrot view.
		final int height = 768;
		
		// image for mandelbrot view
		final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		final Object framePaintedDoorBell = new Object();
		final JComponent viewer = new JComponent() {

			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(image, 0, 0, width, height, this);
				synchronized (framePaintedDoorBell) {
					framePaintedDoorBell.notify();
				}
			}
		};
		
		// set the size of JComponent which displays Mandelbrot image
		viewer.setPreferredSize(new Dimension(width, height));
		
		final Object userClickDoorBell = new Object();
		
		// Mouse listener which reads the user clicked zoom-in point on the Mandelbrot view
		viewer.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				to = e.getPoint();
				synchronized (userClickDoorBell) {
					userClickDoorBell.notify();
				}
			}
		});
		
		final JPanel controlPanel = new JPanel(new FlowLayout());
		
		final String[] choices = new String[] {
				"Java Sequential",
				"Java Threads",
				"GPU OpenCL"
		};
		
		final JComboBox startButton = new JComboBox(choices);
		
		startButton.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				final String item = (String) startButton.getSelectedItem();
				
				if (item.equals(choices[2])) {
					mandelBrot = gpuMandelBrot;
				} else if (item.equals(choices[0])) {
					mandelBrot = javaMandelBrot;
				} else if (item.equals(choices[1])) {
					mandelBrot = javaMandelBrotMultiThread;
				}
			}
		});
		controlPanel.add(startButton);
		
		controlPanel.add(new JLabel("FPS"));
		final JTextField framesPerSecondTextField = new JTextField("0", 5);
		
		controlPanel.add(framesPerSecondTextField);
		
		// swing housework to create the frame
		frame.getContentPane().add(viewer);
		frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		// extract the underlying RGB buffer from the image.
		// pass this to the kernel so it operates directly on the RGB buffer of the image
		
		final int[] imageRgb = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		/** Mutable values of scale, offsetx and offsety sot hat we can modify the zoom level and position of a view. */
		float scale = .0f;
		
		float offsetx = .0f;
		
		float offsety = .0f;
		Device device = Device.best();
		if (device instanceof OpenCLDevice) {
			final OpenCLDevice openclDevice = (OpenCLDevice) device;
			
			System.out.println("max memory = " + openclDevice.getGlobalMemSize());
			System.out.println("max mem alloc size = " + openclDevice.getMaxMemAllocSize());
			gpuMandelBrot = openclDevice.bind(MandelBrot.class);
		}
		
		javaMandelBrot = new JavaMandelBrot();
		javaMandelBrotMultiThread = new JavaMandelBrotMultiThread();
		mandelBrot = javaMandelBrot;
		final float defaultScale = 3f;
		scale = defaultScale;
		offsetx = -1f;
		offsety = 0f;
		final Range range = device.createRange2D(width, height);
		mandelBrot.createMandleBrot(range, scale, offsetx, offsety, imageRgb);
		viewer.repaint();
		
		// window listener to dispose Kernel resources on user exit.
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				//mandelBrot.dispose();
				System.exit(0);
			}
		});
		
		while (true) {
			// wait for the user to click somewhere
			while (to == null) {
				synchronized (userClickDoorBell) {
					try {
						userClickDoorBell.wait();
					} catch (final InterruptedException ie) {
						ie.getStackTrace();
					}
				}
			}
			
			float x = -1f;
			float y = 0f;
			final float tox = ((float) (to.x - (width / 2)) / width) * scale;
			final float toy = ((float) (to.y - (width / 2)) / width) * scale;
			
			// this is how many frames we will display as we zoom in and out.
			final int frames = 128;
			long startMillis = System.currentTimeMillis();
			
			int frameCount = 0;
			for (int sign = -1; sign < 2; sign +=2 ) {
				for (int i = 0; i < (frames - 4); i++) {
					scale = scale + ((sign * defaultScale) / frames);
					x = x - (sign * (tox / frames));
					y = y - (sign * (toy / frames));
					offsetx = x;
					offsety = y;
					mandelBrot.createMandleBrot(range, scale, offsetx, offsety, imageRgb);
					viewer.repaint();
					synchronized (framePaintedDoorBell) {
						try {
							framePaintedDoorBell.wait();
						} catch (final InterruptedException ie) {
							ie.getStackTrace();
						}
					}
					frameCount++;
					final long endMillis = System.currentTimeMillis();
					final long elapsedMillis = endMillis - startMillis;
					if (elapsedMillis > 1000) {
						framesPerSecondTextField.setText("" + ((frameCount * 1000) / elapsedMillis));
						frameCount = 0;
						startMillis = endMillis;
					}
				}
			}
			
			// reset zoom-in point.
			to = null;
		}

	}

}
