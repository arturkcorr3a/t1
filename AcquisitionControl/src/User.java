/**.
 * @author Artur Kalil e Eduardo Martginoni
 */
public abstract class User {

	protected String name;
	protected static int id_aux = 1;
	protected int id;

	public abstract Department getDepartment();
	
	/**
	 * 
	 * @return The user's ID. 
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets the user's initials directly from his name.
	 * @return A String with the first 2 letters os the User's name, in upper case.
	 */
	public String initials() {
		if (name == null) return null;
		String initials = "" + this.name.charAt(0) + this.name.charAt(1);
		return initials.toUpperCase();
	}

	/**
	 * 
	 * @return The user's name. 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets a new name to an user.
	 * @param name the new user's name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Defines the type of user based on their ID.
	 * @return false if the user is an employee; true if the user is an administrator. 
	 */
	public boolean type() {
		if (this.id <= 5000) return true;
		return false;
	}

	/**
	 * Defines the type of user based on their ID.
	 * @return A String with the type of user. 
	 */
	public String typeString(){
		if(type()) return "Administrator";
		return "Employee";
	}

	public String toString(){
		return name + "| ID: " + this.id + "-" + initials();
	}
}
