package play.learn.java.design.command_query_responsibility_segregation;

import java.math.BigInteger;
import java.util.List;


public interface IQueryService {

	Author getAuthorByUsername(String username);

	Book getBook(String title);

	List<Book> getAuthorBooks(String username);

	BigInteger getAuthorBooksCount(String username);

	BigInteger getAuthorsCount();

}
