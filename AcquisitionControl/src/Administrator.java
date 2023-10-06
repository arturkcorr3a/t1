public class Administrator extends User {

	public Administrator(String name) {
		super.name = name;
		super.id = 1000 + User.id_aux;
		User.id_aux ++;
	}
}
