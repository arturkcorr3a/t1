import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Order {

	private Department department;
	private Calendar date;
	private Calendar closureDate ;
	private int status; // -1 reprovado,0 aberto, 1 aprovado
	private final ArrayList<Item> items;
	private final User user;

	public Order(User user) {
		items = new ArrayList<>();
		this.user = user;
		date = Calendar.getInstance();
		if (user.type()) department = null;
		else department = user.getDepartment();
		status = 0;
	}

	public double total() {
		double total=0;
		for (Item items : items) {
			total += items.getTotal();
		}
		if (total > department.getMaxValue()){
			reprove();
			return -1;
		}
		return total;
	}

	public void addItem(Item item) {
		items.add(item);
	}

	public void reprove() {
		status = -1;
	}

	public void approve() {
		status = 1;
	}

	public void delete() {

	}

	public void receive(String closureDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMM/yyyy");
		try {
			this.closureDate.setTime(sdf.parse(closureDate));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public Department getDepartment() {
		return department;
	}

	public Calendar getDate() {
		return date;
	}

	public Calendar getClosureDate() {
		return closureDate;
	}

	public int getStatus() {
		return status;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public User getUser() {
		return user;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String toString() {
		return null;
	}
}
