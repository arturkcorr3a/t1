import java.util.ArrayList;
import java.util.Scanner;

public class App {
	private System1 system = new System1();
	private static final Scanner in = new Scanner(System.in);

/* IDEIAS:
 * 1. MÉTODOS PARA PEGAR DEPARTAMENTO E USUÁRIO A PARTIR DE STRINGS/NÚMEROS OU MOSTRANDO
 * 		LISTAS COM ÍNDICES E PEDINDO PELO ÍNCIDE
 * 
 */

 	/**
	 * Prints the current user info.
	 * @clears No
	 */
	public void status(){
		User user = system.getCurrentUser();
		System.out.println("CURRENT USER: " + user.toString() + "\nTYPE:" + user.typeString() + "\n");
	}

	/**
	 *	Changes users. If login fails 3 times, the method will exit; 
	 *	@clears Yes
	 */
	public void changeUser(){
		clear();
		System.out.println("-----LOG IN-----");

		int id = askID(), count=0;
		if(!(system.userCheck(id))){
			System.out.println("LOGIN FAILED: ID not found");
		}

		System.out.println("\nID: " + id);
	
		String inicials = askInicials();
		User old = system.changeUser(id, inicials);

		while(old == null && count <3){
			count ++;
			System.out.println("\nERROR: Inicials do not match the ID.");
			System.out.println("\nID: " + id);
			inicials = askInicials();
			old = system.changeUser(id, inicials);
		}

		if(count >= 3) {
			System.out.println("LOGIN FAILED: WRONG INICIALS");
			return;
		}

		System.out.println("Login was successful!");
		System.out.println("\nPrevious user: " + old.getId());
		status();
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
			type = (char)in.nextByte();
			in.nextLine();
		}while(type != 'a' && type != 'A' && type != 'e' && type != 'E');

		System.out.println("Insert the name of the new user: ");
		String name = in.nextLine();

		User user = null;

		if(type == 'A' || type == 'a'){
			user = system.newUser(name);
		}

		if(type == 'E' || type == 'e'){
			Department department = askDepartment();
			user = system.newUser(name, department);
		}

		clear();
		System.out.println("NEW USER SUCCESSFULY CREATED\n" + user.toString());
		
		System.out.println("\nDo you wish to login to " + user.getId() + "?");
		System.out.println("[0] Yes");
		System.out.println("[1] No");
		
		int change = in.nextInt();
		if(change == 0){
			system.changeUser(user.getId(), user.inicials());
			status();
		}
			else if(change == 1){
				status();
			}
				else{
					System.out.println("Login failed.");
					status();
				}
	}

	/**
	 * Prints the indexed user list.
	 * @clears No
	 * @apiNote Only admins have access to this.
	 */
	public void showUserList(){
		ArrayList<User> users = system.getUsers();
		int size = users.size();

		System.out.println("\n-----USER LIST-----");

		for(int i=0; i<size; i++){
			System.out.println(i + "-" + users.get(i).toString());
		}
	}

	/**
	 * Shows the list of departments and asks the user to choose one.
	 * @return The chosen department amongst the 5.
	 */
	public Department askDepartment(){
		Department[] departments = system.getDepartments();
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
	 * @return the verified incials, in upper case.
	 */
	public String askInicials(){

		System.out.println("\nType the user's inicials: ");
		String inicials = in.nextLine();

		if(inicials.length() != 2){
			System.out.println("ERROR: INICIALS MUST CONTAIN 2 CHARACTERS.");
			askInicials();
		}

		return inicials.toUpperCase();
	}

	/**
	 * Clears console.
	 */
	public void clear(){
		System.out.print("\033[H\033[2J");  
		System.out.flush();
	}

	public void run() {
	}
}
