/**.
 * @author Artur Kalil e Eduardo Martginoni
 */
public class Administrator extends User {

	/**
	 * Constructor method for new administrator.
	 * @param name new administrator's name.
	 */
	public Administrator(String name) {
		super.name = name;
		super.id = 1000 + User.id_aux;
		User.id_aux ++;
	}
}
