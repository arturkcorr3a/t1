import java.util.ArrayList;

public class System {

	private ArrayList<User> users;
	private Department[] departments;
	private User currentUser;
	private ArrayList<Order> orders;

	public void startUsers() {
	}

	public void startDepartaments() {
	}

	public User changeUser(int id, String inicials) {
		return null;
	}

	public void newUser(String name, int tipo, Department departament) {
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
