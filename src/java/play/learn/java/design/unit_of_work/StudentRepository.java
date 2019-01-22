package play.learn.java.design.unit_of_work;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentRepository implements IUnitOfWork<Student> {

	private Map<String, List<Student>> context;
	private StudentDatabase studentDatabase;

	/**
	 * @param context
	 *            set of operations to be perform during commit.
	 * @param studentDatabase
	 *            Database for student records.
	 */
	public StudentRepository(Map<String, List<Student>> context, StudentDatabase studentDatabase) {
		this.context = context;
		this.studentDatabase = studentDatabase;
	}

	@Override
	public void registerNew(Student student) {
		System.out.printf("Registering %s for insert in context.", student.getName());
		register(student, IUnitOfWork.INSERT);
	}

	@Override
	public void registerModified(Student student) {
		System.out.printf("Registering %s for modify in context.", student.getName());
		register(student, IUnitOfWork.MODIFY);

	}

	@Override
	public void registerDeleted(Student student) {
		System.out.printf("Registering %s for delete in context.", student.getName());
		register(student, IUnitOfWork.DELETE);
	}

	private void register(Student student, String operation) {
		List<Student> studentsToOperate = context.get(operation);
		if (studentsToOperate == null) {
			studentsToOperate = new ArrayList<>();
		}
		studentsToOperate.add(student);
		context.put(operation, studentsToOperate);
	}

	/**
	 * All UnitOfWork operations are batched and executed together on commit only.
	 */
	@Override
	public void commit() {
		if (context == null || context.size() == 0) {
			return;
		}
		System.out.println("Commit started");
		if (context.containsKey(IUnitOfWork.INSERT)) {
			commitInsert();
		}

		if (context.containsKey(IUnitOfWork.MODIFY)) {
			commitModify();
		}
		if (context.containsKey(IUnitOfWork.DELETE)) {
			commitDelete();
		}
		System.out.println("Commit finished.");
	}

	private void commitInsert() {
		List<Student> studentsToBeInserted = context.get(IUnitOfWork.INSERT);
		for (Student student : studentsToBeInserted) {
			System.out.printf("Saving %s to database.", student.getName());
			studentDatabase.insert(student);
		}
	}

	private void commitModify() {
		List<Student> modifiedStudents = context.get(IUnitOfWork.MODIFY);
		for (Student student : modifiedStudents) {
			System.out.printf("Modifying %s to database.", student.getName());
			studentDatabase.modify(student);
		}
	}

	private void commitDelete() {
		List<Student> deletedStudents = context.get(IUnitOfWork.DELETE);
		for (Student student : deletedStudents) {
			System.out.printf("Deleting %s to database.", student.getName());
			studentDatabase.delete(student);
		}
	}
}
