import java.util.ArrayList;

/**
 * Implements methods that manage the system
 * @apiNote Without I/O; that must be done in de App.java class.
 * @author Artur Kalil e Eduardo Martginoni
 */
public class System1 {

//NOTA PRO EDUARDO: depois que tu acabar a classe Order dá uma olhada nos métodos que retornam Order[]
//					pra ver se eu não fiz bosta 

	private ArrayList<User> users;
	private Department[] departments;
	private User currentUser;
	private ArrayList<Order> orders;

	/**
	 * Constructor method for the System.
	 * Inicializes the users, orders and departments arrays.
	 * Creates 5 departments and 16 users.
	 */
	public System1(){
		users = new ArrayList<User>();
		departments = new Department[5];
		orders = new ArrayList<Order>();
		startDepartaments();
		startUsers();
		this.currentUser = users.get(0);
	}

	/**
	 * Inicializes the System with 16 users: 8 administrators and 8 employees.
	 */
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

	/**
	 * Inicializes the System with 5 departments.
	 */
	private void startDepartaments(){
		departments[0] = new Department("Marketing", 800);
		departments[1] = new Department("Finances", 1000);
		departments[2] = new Department("Human Resources", 500);
		departments[3] = new Department("Engeneering", 150000);
		departments[4] = new Department("Maintance", 1200);
	}

	/**
	 * Login to another user, through it's id and inicials.
	 * @param id ID of the logging user.
	 * @param inicials First two letters of the logging user.
	 * @return The logged out user 
	 * @null If the login was unsuccessful.
	 * Or if there is no user matching that ID. 
	 */
	public User changeUser(int id, String inicials) {
		if(!(userCheck(id))) return null;

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

	/**
	 * Gets the User by its ID
	 * @param id user's id
	 * @return User object with matching ID
	 * @null if the user does not exist
	 */
	public User userByID(int id){
		if(!(userCheck(id))) return null;
		int tam = users.size();
		for(int i=0; i<tam; i++){
			if(users.get(i).getId() == id) return users.get(i);
		}
		return null;
	}

	public Department[] getDepartments(){
		return departments;
	}

	/**
	 * Creates a new user for an administrator.
	 * @apiNote Use changeUser() method to change users. 
	 * @param name New user's name.
	 * @return New User object.
	 */
	public User newUser(String name) {
		Administrator adm = new Administrator(name);
		users.add(adm);
		return adm;
	}

	/**
	 * Creates a new user for an employee.
	 * @apiNote Use changeUser() method to change users. 
	 * @param name New user's name.
	 * @param department New user's department.
	 * @return New User object.
	 */
	public User newUser(String name, Department department) {
		Employee employee = new Employee(name, department);
		users.add(employee);
		return employee;
	}

	//DESENVOLVER E DOCUMENTAR JAVADOC
	public void newOrder() {
		return;
	}

	/**
	 * @return logged in user.
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	//DESENVOLVER E DOCUMENTAR JAVADOC
	public Order[] ordersByDate(String date1, String date2) {	
		return null;
	}

	/**
	 * @return User's list.
	 */
	public ArrayList<User> getUsers() {
		return users;
	}

	/**
	 * checks if the user exists in the database
	 * @param user User to be checked.
	 * @return true if the user exists;
	 * false if it does not.
	 */
	public boolean userCheck(User user){
		int tam = users.size();
		for(int i=0; i<tam; i++){
			if(user == users.get(i)) return true;
		}
		return false;
	}

	/**
	 * Checks if the user exists in the database (by their ID)
	 * @param id id of the User to be checked.
	 * @return true if the user exists;
	 * false if it does not.
	 */
	public boolean userCheck(int id){
		int tam = users.size();
		for(int i=0; i<tam; i++){
			if(id == users.get(i).getId()) return true;
		}
		return false;
	}

	/**
	 * Gets all the orders made by an specific user.
	 * @apiNote Only administrators can get this info.
	 * @param user User 'under investigation'.
	 * @return An static array with the users Orders.
	 * @null If the current user is not an administrator;
	 * If the user does not exist; 
	 * If the user had not made any orders.
	 */
	public Order[] ordersByUser(User user){
		if(!(currentUser.type() || userCheck(user))) return null;

		int size = orders.size(), count = 0;
		
		for(int i=0; i<size; i++){
			if(orders.get(i).getUser() == user) count++;
		}
		
		if(count == 0) return null;
		
		Order[] uOrders = new Order[count];
		int j=0;
		for(int i=0; i<count; i++){
			if(orders.get(i).getUser() == user){
				uOrders[j] = orders.get(i);
				j++;
			}
		}

		return uOrders;
	}

	/**
	 * Gets all the orders that contains items that match the description.
	 * @apiNote Only administrators can get this info.
	 * @param description ordered item's description that needs to be searched.
	 * @return An ArrayList containing all the orders with the specified item.
	 * @null If the current user is not an administrator;
	 * If there are not any orders with the item.
	 */
	public ArrayList<Order> ordersByDescription(String description) {
		if(!(currentUser.type())) return null;

		int oSize = orders.size(), iSize;
		ArrayList<Order> dOrders = new ArrayList<Order>();
		Order orderI;
		ArrayList<Item> orderItems;

		for(int i=0; i<oSize; i++){
			orderI = orders.get(i);
			orderItems = orders.get(i).getItems();
			iSize = orderItems.size();
			for(int j=0; j<iSize; j++){
				if(orderItems.get(j).getDescription().equalsIgnoreCase(description)) dOrders.add(orderI);
			}
		}
		
		if(dOrders.size() == 0) return null;
		return dOrders;
	}

	//ver depois de feita a Order qual número é qual status
	//DESENVOLVER E DOCUMENTAR JAVADOC
	public Order[] openOrders() {
		return null;
	}
	
	//ver depois de feita a Order qual número é qual status
	//DESENVOLVER E DOCUMENTAR JAVADOC
	public void evaluateOrder(Order order) {
		return;
	}

	//DESENVOLVER E DOCUMENTAR JAVADOC
	public double[] totalOrderPercentages() {
		return null;
	}

	//DESENVOLVER E DOCUMENTAR JAVADOC
	public double[] last30DaysOrders() {
		return null;
	}

	//DESENVOLVER E DOCUMENTAR JAVADOC
	public double[] totalsByStatus() {
		return null;
	}

	//DESENVOLVER E DOCUMENTAR JAVADOC
	public Order highestValueOpenOrder() {
		return null;
	}

}
