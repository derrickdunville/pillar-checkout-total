package pillar.item;

public abstract class AbstractItem<V> {
	private String name;
	private double price;
	
	public AbstractItem(String itemName, double itemPrice) {
		this.name = itemName;
		this.price = itemPrice;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public abstract double getSubTotal(V units);
}
