import java.util.ArrayList;

public class Department{

	private double maxValue;
	private String name;
	private ArrayList<Employee> employees;

	public Department(String name, double maxValue){
		this.employees = new ArrayList<Employee>();
		this.name = name;
		this.maxValue = maxValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Employee> getEmployees() {
		return employees;
	}
}
