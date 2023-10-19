/**.
 * @author Artur Kalil and Eduardo Martginoni
 */
public class Item {

	private double price;
	private int quant;
	private String description;
	/**
	 * Total sum of the items price i.e. the price times the quantity.
	 */
	private double total;

	/**
	 * @apiNote The total sum is updated automatically.
	 * @param description Item description.
	 * @param price Item price.
	 * @param quant Quantity of items.
	 */
	public Item(String description, double price, int quant) {
		if(price > 0) this.price = price;
		if(quant > 0) this.quant = quant;
			else this.quant = 1;
		this.description = description;
		total(); 
	}

	/**
	 * @return Item description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return The item price.
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @return Quantity of items.
	 */
	public int getQuant() {
		return quant;
	}

	/**
	 * @return The total sum of the items price.
	 */
	public double getTotal(){
		total();
		return this.total;
	}

	/**
	 * @param description Item description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @apiNote The total sum is updated automatically.
	 * @param price Item price.
	 */
	public void setPrice(double price) {
		if(price > 0) this.price = price;
		total();
	}

	/**
	 * @apiNote The total sum is updated automatically.
	 * @param quant Quantity of items.
	 */
	public void setQuant(int quant) {
		if(quant > 0) this.quant = quant;
			else this.quant = 1;
		total();
	}

	public String toString() {
		return "Description: " + description + String.format("\nPrice: R$%.2f; Quantity: %d; Total: R$%.2f", price, quant, total);
	}

	/**
	 * This method updates the total sum of the items price i.e. the price times the quantity.
	 */
	private void total(){
		this.total = this.price * this.quant;
	}

}
