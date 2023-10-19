import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * @author Artur Kalil and Eduardo Martginoni
 */
public class App {
	private final Control control = new Control();
	private final Scanner in = new Scanner(System.in);

	/**Constructor method. */
	public App(){}

	/**
	 * Shows the list of departments and asks the user to choose one.
	 * @return The chosen department amongst the 5.
	 * @clears No.
	 */
	public Department askDepartment(){
		Department[] departments = control.getDepartments();
		for(int i=0; i<5; i++){
			System.out.println("["+(i+1)+"] " + departments[i].getName());
		}

		System.out.println("Select the department:");
		int dep = in.nextInt();

		while(dep < 1 || dep > 5){
			System.out.println("ERROR: INVALID DEPARTMENT.");
			System.out.println("Select the department:");
			dep = in.nextInt();
		}

		in.next();
		return departments[dep-1];
	}

	/**
	 * Asks for an ID and makes sure its within accetable bounds.
	 * @return the verified ID.
	 * @clears No
	 */
	public int askID(){

		System.out.println("\nType the user's ID: ");
		int id = in.nextInt();

		if(id < 1000 || id > 10000){
			System.out.println("ERROR: ID MUST BE BETWEEN 1000 AND 10000.");
			askID();
		}

		in.nextLine();
		return id;

	}

	/**
	 * Asks for an ID and makes sure its has an accetable length.
	 * @return The verified incials, in upper case.
	 * @clears No
	 */
	public String askInitials(){

		System.out.println("\nType the user's initials: ");
		String initials = in.nextLine();

		if(initials.length() != 2){
			System.out.println("ERROR: INITIALS MUST CONTAIN 2 CHARACTERS.");
			askInitials();
		}

		return initials.toUpperCase();
	}

	/**
	 * Clears console and prints the user info.
	 */
	public void clear(){
		System.out.print("\033[H\033[2J");  
		System.out.flush();
		status();
	}

	/**
	 *	Changes users. If login fails 3 times, the method will exit; 
	 *	@clears Yes
	 */
	public void changeUser(){
		clear();
		System.out.println("-----LOG IN-----");

		int id = askID(), count=0;
		if(!(control.userCheck(id))){
			System.out.println("LOGIN FAILED: ID not found");
		}

		System.out.println("\nID: " + id);
	
		String initials = askInitials();
		User old = control.changeUser(id, initials);

		while(old == null && count <3){
			count ++;
			System.out.println("\nERROR: initials do not match the ID.");
			System.out.println("\nID: " + id);
			initials = askInitials();
			old = control.changeUser(id, initials);
		}

		if(count >= 3) {
			System.out.println("LOGIN FAILED: WRONG INITIALS");
			return;
		}

		System.out.println("Login was successful!");
		System.out.println("\nPrevious user: " + old.getId());
		status();
	}

	/**
	 * Evaluates an open order.
	 * @apiNote Only administrators have access to this.
	 * @clears Yes.
	 */
	public void evaluateOrder(){
		clear();
		System.out.println("-----EVALUATE OPEN ORDERS-----");
		
		if(!(control.getCurrentUser().type())){
			System.out.println("\nACCESS DENIED.\nOnly administrators have access to this.");
			return;
		}

		ArrayList<Order> orders = openOrders();
		if(orders.size() != 0){
			System.out.println("\nEnter the index to the order to be evaluated: ");
			int index = (in.nextInt() - 1), status;

			while(index < 0 || index > orders.size()){
				System.out.println("ERROR: index is not valid. Try again: ");
				index = in.nextInt();
			}
			
			System.out.println("\nOrder in evaluation:\t" + orders.get(index).toString());
			System.out.println("\t[-1] Reject order;");
			System.out.println("\t [0] Exit;");
			System.out.println("\t [1] Approve order;");
			status = in.nextInt();

			while(status < -1 || status > 1){
				System.out.println("ERROR: evaluation is not valid. Try again: ");
				status = in.nextInt();
			}

			in.nextLine();
			control.evaluateOrder(orders.get(index), status);
		}
	}

	/**
	 * Prints the highest value open order stats.
	 * @clears Yes
	 * @apiNote Only administrators hace access to this
	 */
	public void highestValueOpenOrder(){
		clear();
		System.out.println("-----HIGHEST VALUE OPEN ORDER-----\n");

		if(!(control.getCurrentUser().type())){
			System.out.println("\nACCESS DENIED.\nOnly administrators have access to this.");
			return;
		}

		System.out.println(control.highestValueOpenOrder());
	}

	/**
	 * Prints the last 30 days comlpeted orders stats.
	 * @clears Yes
	 * @apiNote Only administrators hace access to this
	 */
	public void last30DaysCompletedOrders(){
		clear();
		System.out.println("-----LAST 30 DAYS COMPLETED ORDERS-----\n");

		if(!(control.getCurrentUser().type())){
			System.out.println("\nACCESS DENIED.\nOnly administrators have access to this.");
			return;
		}

		System.out.println(control.last30DaysCompletedOrders());
	}

	/**
	 * Prints the last 30 days opened orders stats.
	 * @clears Yes
	 * @apiNote Only administrators hace access to this
	 */
	public void last30DaysOpenOrders(){
		clear();
		System.out.println("-----LAST 30 DAYS OPENED ORDERS-----\n");

		if(!(control.getCurrentUser().type())){
			System.out.println("\nACCESS DENIED.\nOnly administrators have access to this.");
			return;
		}

		System.out.println(control.last30DaysOpenedOrders());
	}

	/**
	 * Creates a new Item to add to the Order.
	 * @return The created Item.
	 * @clears No.
	 */
	public Item newItem(){
		System.out.println("\n\n-----NEW ITEM-----\n");

		System.out.println("Enter the item description: ");
		String description = in.nextLine();

		System.out.println("Enter the item's unitary price: ");
		double price = in.nextDouble();
		
		while(price <= 0){
			System.out.println("ERROR: Price must be a positive number.\nEnter correct price: ");
			price = in.nextDouble();
		}
		
		System.out.println("Enter the ordered quantity: ");
		int quant = in.nextInt();
		
		while(quant <= 0){
			System.out.println("ERROR: Quantity must be a positive number.\nEnter correct price: ");
			quant = in.nextInt();
		}
		in.nextLine();

		return new Item(description, price, quant);
	}

	/**
	 * Creates a new order for the current user with the current date.
	 * @clears Yes.
	 */
	public void newOrder(){
		clear();
		System.out.println("-----NEW ORDER-----");

		Order order = control.newOrder();

		int op, count=1;
		do{
			System.out.println("\nEnter the [" + count + "] item: ");
			Item addedItem = newItem();
			
			boolean flag = true;
			if(!(control.getCurrentUser().type())){
				if((order.total() + addedItem.getTotal()) <= control.getCurrentUser().getDepartment().getMaxValue()){
					order.addItem(addedItem);
					System.out.println("Item successfuly added!");
				}
					else{
						System.out.printf("\nERROR: Total amount exceeds your department limit of R$ %.2f.", control.getCurrentUser().getDepartment().getMaxValue());
						flag = false;
					}
			}
				else{
					order.addItem(addedItem);
					System.out.println("Item successfuly added!");
				}

			System.out.println("\nCurrent order so far:");
			if(count>1 || flag == true){
				for(int i=0; i<count; i++){
					System.out.println(order.getItems().get(i).toString());
				}
			}
			
			System.out.printf("\n--TOTAL: %.2f", order.total());


			if(flag) count++;
			System.out.println("\nPress\n\t[0] to finish order;\n\t[1] to add another item.");
			op = in.nextInt();

			while(op != 0 && op != 1){
				System.out.println("ERROR: " + op + " is not a valid option. Try again.\n");
				op = in.nextInt();
			}

			in.nextLine();
		}while(op == 1);
	}

	/**
	 * Creates a new user. New login is optional.
	 * @clears Yes
	 */
	public void newUser(){
		clear();

		System.out.println("-----NEW USER-----");

		System.out.println("Select the type of the new user: ");
		char type;
		do{
			System.out.println("[A] for Administrator;");
			System.out.println("[E] for Employee;");
			type = in.nextLine().charAt(0);
		}while(type != 'a' && type != 'A' && type != 'e' && type != 'E');

		System.out.println("Insert the name of the new user: ");
		String name = in.nextLine();

		User user = null;

		if(type == 'A' || type == 'a'){
			user = control.newUser(name);
		}

		if(type == 'E' || type == 'e'){
			Department department = askDepartment();
			user = control.newUser(name, department);
		}

		clear();
		System.out.println("NEW USER SUCCESSFULLY CREATED\n" + user.toString());
		
		System.out.println("\nDo you wish to login to " + user.getId() + "?");
		System.out.println("[0] No");
		System.out.println("[1] Yes");
		
		int change = in.nextInt();
		if(change == 1){
			control.changeUser(user.getId(), user.initials());
			status();
		}
			else if(change == 0){
				status();
			}
				else{
					System.out.println("Login failed.");
					status();
				}
	}

	/**
	 * Prints the complete list of orders of open orders.
	 * @clears No
	 * @apiNote Only administrators hace access to this
	 * @return null if user is not an administrator, or if there are not any open orders
	 */
	public ArrayList<Order> openOrders(){
		System.out.println("-----OPEN ORDERS-----\n");

		if(!(control.getCurrentUser().type())){
			System.out.println("\nACCESS DENIED.\nOnly administrators have access to this.");
			return null;
		}

		ArrayList<Order> orders = control.openOrders();
		if(orders.size() == 0){
			System.out.println("There are not any open orders.");
			return null;
		}

		System.out.println("\n");
		for(int i=0; i<orders.size(); i++){
			System.out.println((i+1) + " - " + orders.get(i));
		}
		return orders;
	}
	
	/**
	 * Prints a complete list with all the orders containing an item matching said description
	 * @clears Yes
	 * @apiNote Only administrators hace access to this
	 */
	public void ordersByDescription(){
		clear();
		System.out.println("-----ORDERS BY DESCRIPTION-----\n");

		if(!(control.getCurrentUser().type())){
			System.out.println("\nACCESS DENIED.\nOnly administrators have access to this.");
			return;
		}

		System.out.println("Type the item's description: ");
		String description = in.nextLine();

		ArrayList<Order> orders = control.ordersByDescription(description);
		if(orders == null){
			System.out.println("There are not any orders containing an item matching the description.");
			return;
		}

		System.out.println("\n");
		for(int i=0; i<orders.size(); i++){
			System.out.println((i+1) + " - " + orders.get(i));
		}
	}

	/**
	 * Prints the orders in a period of time.
	 * @clears Yes.
	 * @apiNote Only administrators have access to this.
	 */
	public void ordersByDate() {
		clear();
		System.out.println("-----ORDERS BY PERIOD OF TIME-----\n\n");

		if (!(control.getCurrentUser().type())) {
			System.out.println("\nACCESS DENIED.\nOnly administrators have access to this.");
			return;
		}


		System.out.println("Enter the minimum date:  \n(pattern: dd/mm/yyyy)");
		String minS = in.nextLine();

		System.out.println("Enter the maximum date:  \n(pattern: dd/mm/yyyy)");
		String maxS = in.nextLine();
		Calendar min;
		Calendar max;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			min = Calendar.getInstance();
			max = Calendar.getInstance();

			min.setTime(sdf.parse(minS));
			max.setTime(sdf.parse(maxS));
		} catch (ParseException e) {
			System.out.println("Error: Date Incorrect Pattern");
			return;
		}

		ArrayList<Order> orders = control.ordersByDate(min, max);
		if (orders.size() == 0) {
			System.out.println("There are any orders in that period.");
			return;
		}

		System.out.println("\n");
		for (int i = 0; i < orders.size(); i++) {
			System.out.println((i + 1) + " - " + orders.get(i).toString());
		}
	}

	/**
	 * Prints the complete list of orders of a determined user
	 * @clears Yes
	 * @apiNote Only administrators hace access to this
	 */
	public void ordersByUser(){
		clear();
		System.out.println("-----ORDERS BY USER-----\n");

		if(!(control.getCurrentUser().type())){
			System.out.println("\nACCESS DENIED.\nOnly administrators have access to this.");
			return;
		}

		showUserList();
		int id = askID();

		User user = control.userByID(id);
		if(user == null){
			System.out.println("ERROR: USER DOES NOT EXIST.");
			return;
		}

		Order[] orders = control.ordersByUser(user);
		if(orders == null){
			System.out.println("The requested user has not made any orders.");
			return;
		}

		System.out.println("\n");
		for(int i=0; i<orders.length; i++){
			System.out.println((i+1) + " - " + orders[i].toString());
		}
	}

	public void run(){
		clear();
		int menu;
		do {
			System.out.println("\n-----MAIN MENU-----\n");

			System.out.printf("Hello, %s.\n\nWhat do you wish to do next?\n", control.getCurrentUser().getName());
			System.out.println("\t[0] Exit;\n\t[1] Log in to a different user;");
			System.out.println("\t[2] Evaluate an open order;\n\t[3] Show highest value open order;");
			System.out.println("\t[4] Show last 30 days completed orders;\n\t[5] Show last 30 days open orders;");
			System.out.println("\t[6] Place a new order;\n\t[7] Create a new user;");
			System.out.println("\t[8] Show all open orders;\n\t[9] Search orders by item description;");
			System.out.println("\t[10] Search orders by date;\n\t[11] Search orders by requesting user;");
			System.out.println("\t[12] Show totals by order status;\n\t[13] Show total order percentages;");
			menu = in.nextInt();
			in.nextLine();

			switch(menu){
				case 0: break;
				case 1: changeUser(); break;
				case 2:	evaluateOrder(); break;
				case 3: highestValueOpenOrder(); break;
				case 4: last30DaysCompletedOrders(); break;
				case 5: last30DaysOpenOrders(); break;
				case 6: newOrder(); break;
				case 7: newUser(); break;
				case 8: clear(); openOrders(); break;
				case 9: ordersByDescription(); break;
				case 10: ordersByDate(); break;
				case 11: ordersByUser(); break;
				case 12: totalsByStatus(); break;
				case 13: totalOrderPercentages(); break;
				default: System.out.println("ERROR: selected option is not valid. Try again.");
			}
		} while (menu != 0);

		System.out.println("\n\nSystem exited.");
	}

	/**
	 * Prints the indexed user list.
	 * @clears No
	 * @apiNote Only admins have access to this.
	 */
	public void showUserList(){
		if(!(control.getCurrentUser().type())){
			System.out.println("\nACCESS DENIED.\nOnly administrators have access to this.");
			return;
		}

		ArrayList<User> users = control.getUsers();
		int size = users.size();

		System.out.println("\n-----USER LIST-----");

		for(int i=0; i<size; i++){
			System.out.println(i + " - " + users.get(i).toString());
		}
	}

 	/**
	 * Prints the current user info.
	 * @clears No
	 */
	public void status(){
		System.out.println("- - - - - ACQUISITION CONTROL SYSTEM - - - - -\n");
		User user = control.getCurrentUser();
		System.out.println("CURRENT USER: " + user.toString() + "\nTYPE:" + user.typeString() + "\n");
	}

	/**
	 * Prints the orders stats, divided by status.
	 * @clears Yes
	 * @apiNote Only administrators hace access to this
	 */
	public void totalsByStatus(){
		clear();
		System.out.println("-----ORDER STATS BY STATUS-----\n");

		if(!(control.getCurrentUser().type())){
			System.out.println("\nACCESS DENIED.\nOnly administrators have access to this.");
			return;
		}

		System.out.println(control.totalsByStatus());
	}

	/**
	 * Prints the orders stats.
	 * @clears Yes
	 * @apiNote Only administrators hace access to this
	 */
	public void totalOrderPercentages(){
		clear();
		System.out.println("-----TOTAL ORDER PERCENTAGES-----\n");

		if(!(control.getCurrentUser().type())){
			System.out.println("\nACCESS DENIED.\nOnly administrators have access to this.");
			return;
		}

		System.out.println(control.totalOrderPercentages());
	}
}
