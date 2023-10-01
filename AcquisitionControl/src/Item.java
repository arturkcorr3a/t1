public class Item {

	private double price;
	private int quant;
	private String description;
	private double total;

	public Item(String description, double price, int quant) {
		if(price > 0) this.price = price;
		if(quant > 0) this.quant = quant;
			else this.quant = 1;
		this.description = description;
		total(); 
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		if(price > 0) this.price = price;
		total();
	}

	public int getQuant() {
		return quant;
	}

	public void setQuant(int quant) {
		if(quant > 0) this.quant = quant;
			else this.quant = 1;
		total();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getTotal(){
		total();
		return this.total;
	}

	private void total(){
		this.total = this.price * this.quant;
	}

	public String toString() {
		return "Description: " + description + String.format("\nPrice: R$%.2f; Quantity: %d; Total: R$%.2f", price, quant, total);
	}
}
