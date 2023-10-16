/**.
 * @author Artur Kalil e Eduardo Martginoni
 */
public abstract class User {

	protected String name;
	protected static int id_aux = 1;
	protected int id;

	/**
	 * Gets the user's inicials directly from his name.
	 * @return A String with the first 2 letters os the User's name, in upper case.
	 */
	public String inicials() {
		if (name == null) return null;
		String inicials = "" + this.name.charAt(0) + this.name.charAt(1);
		return inicials.toUpperCase();
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
		if(type()) return "Employee";
		return "Administrator";
	}

	/**
	 * 
	 * @return The user's name. 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @return The user's ID. 
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Sets a new name to an user.
	 * @param name the new user's name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return "Name: " + name + "\t| ID:\t" + this.id + "-" + inicials();
	}
}
