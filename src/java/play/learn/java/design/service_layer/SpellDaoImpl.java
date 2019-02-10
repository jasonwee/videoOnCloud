package play.learn.java.design.service_layer;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class SpellDaoImpl extends DaoBaseImpl<Spell> implements SpellDao {

	@Override
	public Spell findByName(String name) {
		Transaction tx = null;
		Spell result = null;
		try (Session session = getSessionFactory().openSession()) {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(persistentClass);
			criteria.add(Restrictions.eq("name", name));
			result = (Spell) criteria.uniqueResult();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		}
		return result;
	}

}
