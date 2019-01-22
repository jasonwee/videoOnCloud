package play.learn.java.design.unit_of_work;

public class Student {
	private final Integer id;
	private final String name;
	private final String address;

	/**
	 * @param id
	 *            student unique id
	 * @param name
	 *            name of student
	 * @param address
	 *            address of student
	 */
	public Student(Integer id, String name, String address) {
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}
}
