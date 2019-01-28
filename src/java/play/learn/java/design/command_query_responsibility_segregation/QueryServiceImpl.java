package play.learn.java.design.command_query_responsibility_segregation;

import java.math.BigInteger;
import java.util.List;

public class QueryServiceImpl implements IQueryService {

	//private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Override
	public Author getAuthorByUsername(String username) {
		Author authorDTo = null;
		//try (Session session = sessionFactory.openSession()) {
			//SQLQuery sqlQuery = session
			//		.createSQLQuery("SELECT a.username as \"username\", a.name as \"name\", a.email as \"email\""
			//				+ "FROM Author a where a.username=:username");
			//sqlQuery.setParameter("username", username);
			//authorDTo = (Author) sqlQuery.setResultTransformer(Transformers.aliasToBean(Author.class)).uniqueResult();
		//}
		return authorDTo;
	}

	@Override
	public Book getBook(String title) {
		Book bookDTo = null;
		//try (Session session = sessionFactory.openSession()) {
			//SQLQuery sqlQuery = session.createSQLQuery(
					//"SELECT b.title as \"title\", b.price as \"price\"" + " FROM Book b where b.title=:title");
			//sqlQuery.setParameter("title", title);
			//bookDTo = (Book) sqlQuery.setResultTransformer(Transformers.aliasToBean(Book.class)).uniqueResult();
		//}
		return bookDTo;
	}

	@Override
	public List<Book> getAuthorBooks(String username) {
		List<Book> bookDTos = null;
		/*
		try (Session session = sessionFactory.openSession()) {
			SQLQuery sqlQuery = session.createSQLQuery("SELECT b.title as \"title\", b.price as \"price\""
					+ " FROM Author a , Book b where b.author_id = a.id and a.username=:username");
			sqlQuery.setParameter("username", username);
			bookDTos = sqlQuery.setResultTransformer(Transformers.aliasToBean(Book.class)).list();
		}
		*/
		return bookDTos;
	}

	@Override
	public BigInteger getAuthorBooksCount(String username) {
		BigInteger bookcount = null;
		//try (Session session = sessionFactory.openSession()) {
			//SQLQuery sqlQuery = session.createSQLQuery("SELECT count(b.title)"
				//	+ " FROM  Book b, Author a where b.author_id = a.id and a.username=:username");
			//sqlQuery.setParameter("username", username);
			//bookcount = (BigInteger) sqlQuery.uniqueResult();
		//}
		return bookcount;
	}

	@Override
	public BigInteger getAuthorsCount() {
		BigInteger authorcount = null;
		/*
		try (Session session = sessionFactory.openSession()) {
			SQLQuery sqlQuery = session.createSQLQuery("SELECT count(id) from Author");
			authorcount = (BigInteger) sqlQuery.uniqueResult();
		}
		*/
		return authorcount;
	}

}
