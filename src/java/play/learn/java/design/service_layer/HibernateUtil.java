package play.learn.java.design.service_layer;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {

	/**
	 * The cached session factory
	 */
	private static volatile SessionFactory sessionFactory;

	private HibernateUtil() {
	}

	/**
	 * Create the current session factory instance, create a new one when there is
	 * none yet.
	 *
	 * @return The session factory
	 */
	public static synchronized SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				sessionFactory = new Configuration().addAnnotatedClass(Wizard.class).addAnnotatedClass(Spellbook.class)
						.addAnnotatedClass(Spell.class)
						.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
						.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
						.setProperty("hibernate.current_session_context_class", "thread")
						.setProperty("hibernate.show_sql", "false").setProperty("hibernate.hbm2ddl.auto", "create-drop")
						.buildSessionFactory();
			} catch (Throwable ex) {
				System.out.printf("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}
		return sessionFactory;
	}

	/**
	 * Drop the current connection, resulting in a create-drop clean database next
	 * time. This is mainly used for JUnit testing since one test should not
	 * influence the other
	 */
	public static void dropSession() {
		getSessionFactory().close();
		sessionFactory = null;
	}

}
