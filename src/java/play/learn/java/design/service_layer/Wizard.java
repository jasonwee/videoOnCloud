package play.learn.java.design.service_layer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "WIZARD")
public class Wizard extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "WIZARD_ID")
	private Long id;

	private String name;

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Spellbook> spellbooks;

	public Wizard() {
		spellbooks = new HashSet<>();
	}

	public Wizard(String name) {
		this();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Spellbook> getSpellbooks() {
		return spellbooks;
	}

	public void setSpellbooks(Set<Spellbook> spellbooks) {
		this.spellbooks = spellbooks;
	}

	public void addSpellbook(Spellbook spellbook) {
		spellbook.getWizards().add(this);
		spellbooks.add(spellbook);
	}

	@Override
	public String toString() {
		return name;
	}

}
