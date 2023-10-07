public class Employee extends User {

	private Department department;

	public Employee(String name, Department department) {
		super.name = name;
		this.department = department;
		super.id = 5000 + User.id_aux;
		User.id_aux = 0;
	}

	public Department getDepartment() {
		return department;
	}

	@Override
	public String toString(){
		return super.toString() + " | Department: " + this.department;
	}
}
