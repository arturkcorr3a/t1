import java.util.ArrayList;
import java.util.Calendar;

/**
 * Implements methods that manage the system
 * @apiNote Without I/O; that must be done in de App.java class.
 * @author Artur Kalil and Eduardo Martginoni
 */
public class Control {

	private ArrayList<User> users;
	private Department[] departments;
	private User currentUser;
	private ArrayList<Order> orders;

	/**
	 * Constructor method for the System.
	 * Inicializes the users, orders and departments arrays.
	 * Creates 5 departments and 16 users.
	 */
	public Control(){
		users = new ArrayList<User>();
		departments = new Department[5];
		orders = new ArrayList<Order>();
		startDepartments();
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
	private void startDepartments(){
		departments[0] = new Department("Marketing", 800);
		departments[1] = new Department("Finances", 1000);
		departments[2] = new Department("Human Resources", 500);
		departments[3] = new Department("Engineering", 150000);
		departments[4] = new Department("Maintenance", 1200);
	}

	/**
	 * Login to another user, through its id and initials.
	 * @param id ID of the logging user.
	 * @param initials First two letters of the logging user.
	 * @return The logged-out user
	 * @null If the login was unsuccessful.
	 * Or if there is no user matching that ID. 
	 */
	public User changeUser(int id, String initials) {
		if(!(userCheck(id))) return null;

		for(int i=0; i<users.size(); i++){
			User user = users.get(i);
			if (user.getId() == id && user.initials().equalsIgnoreCase(initials)){
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

	/**
	 * @return the 5 departments array
	 */
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

	/**
	 * Method to create a new Order
	 * @return the order created.
	 */
	public Order newOrder() {
		Order order = new Order(currentUser);
		orders.add(order);
		return order;
	}

	/**
	 * @return logged in user.
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	public ArrayList<Order> ordersByDate(Calendar min, Calendar max){
		ArrayList<Order> dOrders = new ArrayList<>();
		for (int i=0; i<orders.size();i++){
			if (orders.get(i).getDate().after(min) && orders.get(i).getDate().before(max)){
				dOrders.add(orders.get(i));
			}
		}
		return dOrders;
	}

	/**
	 * @return Users list.
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
	 * Gets all the orders made by a specific user.
	 * @apiNote Only administrators can get this info.
	 * @param user User 'under investigation'.
	 * @return A static array with the users Orders.
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

	/**
	 * Method to see all open orders.
	 * @return An ArrayList that contains all open orders.
	 */
	public ArrayList<Order> openOrders() {
		ArrayList<Order> openOrders = new ArrayList<>();
		for (int i=0; i<orders.size(); i++){
			if (orders.get(i).getStatus()==0) openOrders.add(orders.get(i));
		}
		return openOrders;
	}

	/**
	 * Method to evaluate an order.
	 * @param order The order to be evaluated.
	 * @param status New status of the order
	 */
	public void evaluateOrder(Order order, int status) {
			if (status==1) order.approve();
			else if (status==-1) order.reject();
	}

	/**
	 * Method to see the total number of orders separated into approved orders and rejected orders with percentages.
	 * @return A String with the information.
	 */
	public String totalOrderPercentages() {
		int total = orders.size(), countA=0, countR=0;
		for(int i=0; i<total;i++){
			if (orders.get(i).getStatus()==1) countA++;
			else if(orders.get(i).getStatus()==-1) countR++;
		}

		return "Number of Orders: " + total +
				"\nApproved Orders: [" + countA + "] " + 100*countA/total + "%" +
				"\nRejected Orders: [" + countR + "] " + 100*countR/total + "%";
	}

	/** Method to see all orders opened in the last 30 days and their average order value.
	 * @return A string with the information.
	 */
	public String last30DaysOpenedOrders() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, -31);

		int count=0, total = orders.size();
		double sum=0;

		if (total ==0) return "There is no order registered in the system.";
		
		for (int i=0; i<total; i++){
			if (orders.get(i).getDate().after(date)) {
				sum += orders.get(i).total();
				count ++;
			}
			if (count==0) return "No orders have been opened in the last 30 days.";
		}
		return "Orders opened in the last 30 days: " + count +
				"\nAverage order value: $" + String.format("%.2f",sum/total) ;
	}

	/** Method to see all completed orders in the last 30 days and their average order value.
	 * @return A string with the information.
	 */
	public String last30DaysCompletedOrders() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, -31);

		int count=0, total = orders.size();
		double sum=0;

		if (total ==0) return "There is no order registered in the system.";

		for (int i=0; i<total; i++){
			if (orders.get(i).getClosureDate().after(date)) {
				sum += orders.get(i).total();
				count ++;
			}
			if (count==0) return "No orders have been completed in the last 30 days.";
		}
		return "Orders completed in the last 30 days: " + count +
				"\nAverage order value: $" + String.format("%.2f",sum/total) ;
	}

	/**
	 * Method to see the orders total sum separated by each status.
	 * @return A string with the information.
	 */
	public String totalsByStatus() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, -31);
		double sumR=0, sumO=0, sumA=0, sumC=0;

		for (int i=0; i<orders.size(); i++){
			if (orders.get(i).getDate().after(date)) {
				if (orders.get(i).getStatus()==-1) sumR += orders.get(i).total();
				else if (orders.get(i).getStatus()==0) sumO += orders.get(i).total();
				else if (orders.get(i).getStatus()==1) sumA += orders.get(i).total();
				else sumC += orders.get(i).total();
				}
			}
		return "-- TOTAL VALUE OF EACH CATEGORY -- \n" +
				"\nRejected Orders: $" + String.format("%.2f",sumR) +
				"\nOpen Orders: $" + String.format("%.2f",sumO) +
				"\nApproved Orders: $" + String.format("%.2f",sumA) +
				"\nCompleted Orders: $" + String.format("%.2f",sumC);
	}

	/**
	 * Method to see the information of the open order with the highest value.
	 * @return A string with the information.
	 */
	public String highestValueOpenOrder() {
		int index=0;
		for (int i=0; i<orders.size()-1;i++){
			if (orders.get(i).total() > orders.get(i+1).total()) index = i;
		}
		return orders.get(index).toString();
	}

	/**
	 * Method to see all approved orders.
	 * @return An ArrayList that contains all approved orders.
	 */
	public ArrayList<Order> approvedOrders() {
		ArrayList<Order> openOrders = new ArrayList<>();
		for (int i=0; i<orders.size(); i++){
			if (orders.get(i).getStatus()==1) openOrders.add(orders.get(i));
		}
		return openOrders;
	}
	
	/**
	 * 
	 * @param order the order to be cancelled
	 * @apiNote only the user who made the order can cancel it
	 */
	public void cancelOrder(Order order){
		if(currentUser == order.getUser()){
			orders.remove(order);
		}
	}

	public ArrayList<Order> myOrders(){
		
		ArrayList<Order> myOrders = new ArrayList<Order>();
		for(int i=0; i<orders.size(); i++){
			if(orders.get(i).getUser() == currentUser){
				myOrders.add(orders.get(i));
			}
		}

		return myOrders;
	}
}
