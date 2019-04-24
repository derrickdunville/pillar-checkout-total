package pillar;

public class Item {
	private String name;
	private double price;
	
	public Item(String itemName, double itemPrice) {
		this.name = itemName;
		this.price = itemPrice;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getPrice() {
		return this.price;
	}
}
