package play.learn.java.design.service_layer;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public abstract class DaoBaseImpl<E extends BaseEntity> implements Dao<E> {
	@SuppressWarnings("unchecked")
	protected Class<E> persistentClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
			.getActualTypeArguments()[0];

	/*
	 * Making this getSessionFactory() instead of getSession() so that it is the
	 * responsibility of the caller to open as well as close the session (prevents
	 * potential resource leak).
	 */
	protected SessionFactory getSessionFactory() {
		return HibernateUtil.getSessionFactory();
	}

	@Override
	public E find(Long id) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		E result = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(persistentClass);
			criteria.add(Restrictions.idEq(id));
			result = (E) criteria.uniqueResult();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public void persist(E entity) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.persist(entity);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public E merge(E entity) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		E result = null;
		try {
			tx = session.beginTransaction();
			result = (E) session.merge(entity);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public void delete(E entity) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(entity);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public List<E> findAll() {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		List<E> result = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(persistentClass);
			result = criteria.list();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
		return result;
	}
}
