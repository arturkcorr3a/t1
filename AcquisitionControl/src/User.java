public abstract class User {

	protected String name;
	protected static int id_aux = 1;
	protected int id;

	public String inicials() {
		if (name == null) return null;
		String inicials = "" + this.name.charAt(0) + this.name.charAt(1);
		return inicials.toUpperCase();
	}

	//false: Employee; true: Admin
	public boolean type() {
		if (this.id <= 5000) return true;
		return false;
	}

	public String typeString(){
		if(type()) return "Employee";
		return "Administrator";
	}

	public String getName() {
		return this.name;
	}

	public int getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString(){
		return "Name: " + name + " | ID: " + this.id + "-" + inicials();
	}
}
