import java.util.ArrayList;

public class Order {

	private Department department;
	private String date;
	private String closureDate;
	private int status;
	private ArrayList<Item> items;
	private User user;

	public double total() {
		return 0;
	}

	public Order(String date) {
	}

	public void reprove() {
	}

	public int receive(String closureDate) {
		return 0;
	}

	//getters
	//setters?

	public String toString() {
		return null;
	}

	public void approve() {
	}

	public void delete() {
	}

}
