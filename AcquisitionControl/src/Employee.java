/**.
 * @author Artur Kalil e Eduardo Martginoni
 */
public class Employee extends User {

	private Department department;

	/**
	 * Constructor method for a new employee.
	 * @apiNote parameter department is an object; must be correctly implemented in App to work propperly  
	 * @param name Name of the new employee.
	 * @param department Department of the new employee.
	 */
	public Employee(String name, Department department) {
		super.name = name;
		this.department = department;
		super.id = 5000 + User.id_aux;
		User.id_aux ++;
	}

	/**
	 * @apiNote Returns a 'Department' object; to return the String, use 'toString()' method.
	 * @return Employee's department.
	 */
	public Department getDepartment() {
		return this.department;
	}

	@Override
	public String toString(){
		return super.toString() + " | Department: " + this.department.getName();
	}
}
