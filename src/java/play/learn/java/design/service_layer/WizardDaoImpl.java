package play.learn.java.design.service_layer;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class WizardDaoImpl extends DaoBaseImpl<Wizard> implements WizardDao {
	@Override
	public Wizard findByName(String name) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		Wizard result = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(persistentClass);
			criteria.add(Restrictions.eq("name", name));
			result = (Wizard) criteria.uniqueResult();
			for (Spellbook s : result.getSpellbooks()) {
				s.getSpells().size();
			}
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
}
