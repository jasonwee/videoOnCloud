package play.learn.java.design.composite;

public class FinancialDepartment implements Department {
	 
    private Integer id;
    private String name;
 
    public FinancialDepartment(int i, String string) {
		// TODO Auto-generated constructor stub
	}

	public void printDepartmentName() {
        System.out.println(getClass().getSimpleName());
    }
 
    // standard constructor, getters, setters
}

