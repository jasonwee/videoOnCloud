package play.learn.java.design.command_query_responsibility_segregation;

import java.math.BigInteger;
import java.util.List;

// https://java-design-patterns.com/patterns/cqrs/
public class CQRSDemo {
	public static void main(String[] args) {
		ICommandService commands = new CommandServiceImpl();

		// Create Authors and Books using CommandService
		commands.authorCreated("eEvans", "Eric Evans", "eEvans@email.com");
		commands.authorCreated("jBloch", "Joshua Bloch", "jBloch@email.com");
		commands.authorCreated("mFowler", "Martin Fowler", "mFowler@email.com");

		commands.bookAddedToAuthor("Domain-Driven Design", 60.08, "eEvans");
		commands.bookAddedToAuthor("Effective Java", 40.54, "jBloch");
		commands.bookAddedToAuthor("Java Puzzlers", 39.99, "jBloch");
		commands.bookAddedToAuthor("Java Concurrency in Practice", 29.40, "jBloch");
		commands.bookAddedToAuthor("Patterns of Enterprise Application Architecture", 54.01, "mFowler");
		commands.bookAddedToAuthor("Domain Specific Languages", 48.89, "mFowler");
		commands.authorNameUpdated("eEvans", "Eric J. Evans");

		IQueryService queries = new QueryServiceImpl();

		// Query the database using QueryService
		Author nullAuthor = queries.getAuthorByUsername("username");
		Author eEvans = queries.getAuthorByUsername("eEvans");
		BigInteger jBlochBooksCount = queries.getAuthorBooksCount("jBloch");
		BigInteger authorsCount = queries.getAuthorsCount();
		Book dddBook = queries.getBook("Domain-Driven Design");
		List<Book> jBlochBooks = queries.getAuthorBooks("jBloch");

		System.out.printf("Author username : %s", nullAuthor);
		System.out.printf("Author eEvans : %s", eEvans);
		System.out.printf("jBloch number of books : %s", jBlochBooksCount);
		System.out.printf("Number of authors : %s", authorsCount);
		System.out.printf("DDD book : %s", dddBook);
		System.out.printf("jBloch books : %s", jBlochBooks);

		//HibernateUtil.getSessionFactory().close();
	}

}
