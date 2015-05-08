package play.learn.java;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html
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
		
		
	}

}
