package play.learn.java.design.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class PersonSpecifications {
	public static class AgeBetweenSpec implements Specification<Person> {

		private int from;

		private int to;

		public AgeBetweenSpec(int from, int to) {
			this.from = from;
			this.to = to;
		}

		@Override
		public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

			return cb.between(root.get("age"), from, to);

		}

	}

	/**
	 * Name specification
	 *
	 */
	public static class NameEqualSpec implements Specification<Person> {

		public String name;

		public NameEqualSpec(String name) {
			this.name = name;
		}

		/**
		 * Get predicate
		 */
		public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

			return cb.equal(root.get("name"), this.name);

		}
	}

}
