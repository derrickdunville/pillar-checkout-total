package pillar.item;

public abstract class AbstractItem<V> {
	private String name;
	private double price;
	private double markdown;
	
	public AbstractItem(String itemName, double itemPrice) {
		if(itemName == null || itemName.equals("")) {
			throw new IllegalArgumentException("Item name cannot be null or empty String");
		}
		
		if(itemPrice == 0.00 || itemPrice < 0) {
			throw new IllegalArgumentException("Item price cannot be null and must be greater");
		}
		
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
