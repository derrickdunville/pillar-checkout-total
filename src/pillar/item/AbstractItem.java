package pillar.item;

public abstract class AbstractItem<V> {
	private String name;
	private double price;
	private double markdown;
	
	public AbstractItem(String itemName, double itemPrice) {
		this.name = itemName;
		this.price = itemPrice;
		this.markdown = 0.0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getPrice() {
		return this.price - this.markdown;
	}
	
	public void setMarkdown(double markdownAmount) {
		this.markdown = markdownAmount;
	}
	
	public abstract double getSubTotal(V units);
}
