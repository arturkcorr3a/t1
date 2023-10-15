import java.util.ArrayList;

/**
 * @author Artur Kalil and Eduardo Martginoni
 */
public class Department{

	private final double maxValue;
	private final String name;
	private final ArrayList<Employee> employees;

	/**
	 * Constructor method for the departments.
	 * @param name Name of the department.
	 * @param maxValue Maximum order value allowed for the department.
	 */
	public Department(String name, double maxValue){
		this.employees = new ArrayList<>();
		this.name = name;
		this.maxValue = maxValue;
	}

	/**
	 * @return Maximum order value allowed.
	 */
	public double getMaxValue() {
		return maxValue;
	}

	/**
	 * @return Department name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return An ArrayList of employees in this department.
	 */
	public ArrayList<Employee> getEmployees() {
		return employees;
	}

	/**
	 * @param employee Employee who will be added to the department.
	 */
	public void addEmployee(Employee employee){
		employees.add(employee);
	}
}
