package play.learn.java.fx;

import java.util.Random;

import static java.lang.Math.random;
import static java.lang.Math.round;

public class RandomUtil {
	
	private static final Random rand = new Random();
	
	public static double getRandom(double variation) {
		return (random() * variation * 2 + 1 - variation);
	}
	
	public static double getGaussianRandom(double mean, double deviation) {
		return mean + deviation * rand.nextGaussian();
	}
	
	public static double getGaussianRandom(double from, double to, double mean, double deviation) {
		double result;
		do {
			result = getGaussianRandom(mean, deviation);
		} while (result < from || result > to);
		return result;
	}
	
	public static int getRandomIndex(int from, int to) {
		return (int) round(random() * (to - from)) + from;
	}
}
