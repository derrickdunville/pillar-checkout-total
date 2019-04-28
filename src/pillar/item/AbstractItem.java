package pillar.item;

public abstract class AbstractItem<V> {
	private String name;
	private double price;
	private double markdown;
	
	public AbstractItem(String itemName, double itemPrice) {
		if(itemName == null || itemName.equals("")) {
			throw new IllegalArgumentException("Item name cannot be null or empty String");
		}
		if(itemPrice <= 0) {
			throw new IllegalArgumentException("Item price must be greater than zero");
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
		if(markdownAmount >= getPrice()) {
			throw new IllegalArgumentException("Markdown amount must be less than item price.");
		}
		this.markdown = markdownAmount;
	}
		
	public abstract double getSubTotal(V units);
}
