import java.util.ArrayList;

public class System {

	private ArrayList<User> users;
	private Department[] departments;
	private User currentUser;
	private ArrayList<Order> orders;

	public System(){
		users = new ArrayList<User>();
		departments = new Department[5];
		orders = new ArrayList<Order>();
		startDepartaments();
		startUsers();
		this.currentUser = users.get(0);
	}

	private void startUsers(){
		for(int i=0; i<8; i++){
			String name1 = "user" + (i+1);
			String name2 = "user" + (i+9);

			Department department;
			if(i<5){
					department = departments[i];
			}
				else {
					department = departments[i-5];
				}
			
			Administrator admin = new Administrator(name1);
			Employee emp = new Employee(name2, department);
			department.addEmployee(emp);
			users.add(emp);
			users.add(admin);
		}
	}

	//REVISAR VALORES MÁXIMOS
	private void startDepartaments(){
		departments[1] = new Department("Finances", 1000);
		departments[2] = new Department("Human Resources", 500);
		departments[3] = new Department("Engeneering", 150000);
		departments[4] = new Department("Maintance", 1200);
		departments[0] = new Department("Finances", 800);
	}

	//IMPLEMENTAR DE MANEIRA QUE, AO FINAL, MOSTRE SE FOI REALIZADA A TROCA E OS USUÁRIOS ANTERIOR E NOVO
	public User changeUser(int id, String inicials) {
		for(int i=0; i<users.size(); i++){
			User user = users.get(i);
			if (user.getId() == id && user.inicials().equalsIgnoreCase(inicials)){
				User aux = currentUser;
				currentUser = user;
				return aux;
			}
		}
		return null;
	}

	//ENTRADA DE DADOS NO APP SÓ
	//IF TIPO = TRUE, CHAMA FUNCAO COM DEPARTMENT = NULL
	//ELSE PEDE DEPARTMENT PRO USUARIO
	public void newUser(String name, boolean tipo, Department departament) {
		if(tipo){
			Administrator adm = new Administrator(name);
			users.add(adm);
			return;
		}
		Employee employee = new Employee(name, departament);
		users.add(employee);
	}

	public void newOrder() {
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public Order[] ordersByDate(String date1, String date2) {
		return null;
	}

	public Order[] ordersByEmployee(Employee employee) {
		return null;
	}

	public Order[] ordersByDescription(String description) {
		return null;
	}

	public Order[] openOrders() {
		return null;
	}

	public void evaluateOrder(Order order) {

	}

	public double[] totalOrderPercentages() {
		return null;
	}

	public double[] last30DaysOrders() {
		return null;
	}

	public double[] totalsByStatus() {
		return null;
	}

	public Order highestValueOpenOrder() {
		return null;
	}

}
