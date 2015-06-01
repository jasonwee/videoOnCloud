package play.learn.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;import java.util.Optional;


public class LearnInteger {

	public static void main(String[] args) {

		// 1 byte = 8bit
		// 4 byte = 32bit
		// 2 power 32 = 4294967296
		// sign bit (divide by 2) = 2147483648
		
		System.out.println(Integer.MIN_VALUE);       // -2147483648
		System.out.println(Integer.MAX_VALUE); 		//  2147483647 
		// added java8
		System.out.println(Integer.BYTES);                // 4 (bytes)

		// added java8
		System.out.println(Integer.toUnsignedString(10_000));
		System.out.println(Integer.toUnsignedString(10, 8));

		// added java8
		System.out.println(Integer.parseUnsignedInt("123"));
		System.out.println(Integer.parseUnsignedInt("123", 16));
		
		// added java8
		System.out.println(Integer.hashCode(8));
		System.out.println(Integer.compareUnsigned(10, 20));
		
		// added java8
		System.out.println(Integer.toUnsignedLong(13));
		
		// added java8
		System.out.println(Integer.divideUnsigned(-10, 2));
		
		// added java8
		System.out.println(Integer.remainderUnsigned(10, 3));
		
		Integer[] arrNumbers = {1,2,3};
		List<Integer> numbers = new ArrayList<Integer>(Arrays.asList(arrNumbers));
		// integer::sum added in java8
		Optional<Integer> total = numbers.stream().reduce(Integer::sum);
		if (total.isPresent()) System.out.println(total.get());
		
		// integer::max added in java8
		Optional<Integer> max = numbers.stream().reduce(Integer::max);
		if (max.isPresent()) System.out.println(max.get());
		
		// integer::min added in java8
		Optional<Integer> min = numbers.stream().reduce(Integer::min);
		if (min.isPresent()) System.out.println(min.get());
	}

}
