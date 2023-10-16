import java.util.ArrayList;
import java.util.Calendar;

/**.
 * @author Artur Kalil and Eduardo Martginoni
 */
public class Order {

	private Department department;
	private Calendar date;
	private Calendar closureDate;
	/**
	 * Order Status:  [-1] Rejected - [0] Open - [1] Approved - [2] Completed
	 */
	private int status;
	private final ArrayList<Item> items;
	private final User user;

	/**
	 * Constructor method for a new order.
	 *
	 * @param user User who is registering a new order.
	 * @apiNote The order date will automatically be the date and time the order was created.
	 */
	public Order(User user) {
		items = new ArrayList<>();
		this.user = user;
		date = Calendar.getInstance();
		if (user.type()) department = null;
		else department = Employee.getDepartment();
		status = 0;
	}

	/**
	 * @return The total order sum.
	 * @apiNote If the total order sum exceeds the department maximum value, the order will be reproved and the method will return -1.
	 */
	public double total() {
		double total = 0;
		for (Item items : items) {
			total += items.getTotal();
		}
		if (total > department.getMaxValue()) {
			reject();
			return -1;
		}
		return total;
	}

	/**
	 * @param item Item it will be added to the order.
	 */
	public void addItem(Item item) {
		items.add(item);
	}

	/**
	 * Method to reject an order.
	 */
	public void reject() {
		status = -1;
	}

	/**
	 * Method to approve an order.
	 */
	public void approve() {
		status = 1;
	}

	/**
	 * Method to complete an order.
	 * @apiNote The order closure date will automatically be the date and time the method was called.
	 */
	public void receive() {
		status = 2;
		closureDate = Calendar.getInstance();
	}

	/**
	 * @return The user department that opened the order.
	 * @apiNote It will return 'null' if the order was opened by an admin.
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * @return It will return the date the order was opened.
	 * @apiNote This method returns a Calendar object, use SimpleDateFormat to format it.
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * @return It will return the date the order was completed.
	 * @apiNote This method returns a Calendar object, use SimpleDateFormat to format it.
	 */
	public Calendar getClosureDate() {
		return closureDate;
	}

	/**
	 * @return The order status.
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @return An ArrayList of the order items.
	 */
	public ArrayList<Item> getItems() {
		return items;
	}

	/**
	 * @return The user who opened the order.
	 */
	public User getUser() {
		return user;
	}

	public String toString() {
		return "Order{" +
				"department=" + department +
				", date=" + date +
				", closureDate=" + closureDate +
				", status=" + status +
				", items=" + items +
				", user=" + user +
				'}';
	}
}

