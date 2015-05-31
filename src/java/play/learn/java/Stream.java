package play.learn.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html
// http://www.oracle.com/technetwork/articles/java/architect-streams-pt2-2227132.html
public class Stream {


	public static void main(String[] args) {
		// ============== java 7 ================
		/*
		List<Transaction> groceryTransactions = new ArrayList<>();
		for (Transaction t : transactions) {
			if (t.getType() == Transaction.GROCERY) {
				groceryTransactions.add(t);
			}			
		}
		Collections.sort(groceryTransactions, new Comparator() {

			@Override
			public int compare(Transaction t1, Transaction t2) {
				return t2.getValue().compareTo(t1.getValue());
			}
		});
		List<Integer> transactionIds = new ArrayList<>();
		for (Transaction t : groceryTrasactions) {
			transactionIds.add(t.getId());
		}
		*/
		// ============== java 7 =================
		
		// ============== java 8 =================
		/*
		List<Integer> transactionsIds = transactions.stream()
				.filter(t -> t.getType() == Transaction.GROCERY)
				.sorted(comparing(Transaction::getValue).reversed())
				.map(Transaction::getId)
				.collect(toList());
		*/
		// ============== java 8 =================
		
		List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,0);
		List<Integer> twoEvenSquares = numbers.stream()
				.filter(n -> {
					System.out.println("filtering " + n);
					return n % 2 == 0;
					})
				.map(n -> {
					System.out.println("mapping " + n);
					return n * n;	
					})
				.limit(2)
				.collect(Collectors.toList());
		
		List<String> words = Arrays.asList("Oracle", "Java", "Magazine");
		List<Integer> wordLengths = words.stream().map(String::length).collect(Collectors.toList());
		System.out.println(wordLengths);
		
		int sum = numbers.stream().reduce(0, (a,b) -> a + b);
		System.out.println(sum);
		
		int product = numbers.stream().reduce(1, (a,b) -> a * b);
		System.out.println(product);
		product = numbers.stream().reduce(1, Integer::max);
		System.out.println(product);
		
		try {
			Files.lines(Paths.get("src/resources/log4j.properties"))
			                .map(line -> line.split("\\s+")) // Stream<String[]>
			                .flatMap(Arrays::stream) // Stream<String>
			                .distinct()
			                .forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// -- stream and reducer
		Integer[] intArray = {1,2,3,4,5,6,7,8,9};
		List<Integer> listOfIntegers = new ArrayList<Integer>(Arrays.asList(intArray));
		
		System.out.println("Sum of integers: " + listOfIntegers.stream().reduce(Integer::sum).get());
		// -- stream and reducer
		
		// java 8
		System.out.println(String.join(",", "1", "2"));
		
		List<String> joinUS = new ArrayList<String>();
		joinUS.add("we");
		joinUS.add("join");
		joinUS.add("together");
		joinUS.add("now");
		System.out.println(String.join("-", joinUS));
		
		// java 8 unchecked io exception.
		// see unchecked io exception use here https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html
		throw new java.io.UncheckedIOException("test uncheck io exception", new IOException("from io exeption"));
		

		
		
	}

}
