package pillar.item;

public class QuantifiedItem extends AbstractItem<Integer> {

	private int specialTriggerQuantity; 
	private int specialDiscountedQuantity;
	private double specialDiscountPercent;
	
	public QuantifiedItem(String itemName, double itemPrice) {
		super(itemName, itemPrice);
		this.specialTriggerQuantity = 0;
		this.specialDiscountedQuantity = 0;
		this.specialDiscountPercent = 0.0;
	}
	
	public void setSpecial(int triggerQuantity, int discountedQuantity, double discountPercent) {
		this.specialTriggerQuantity = triggerQuantity;
		this.specialDiscountedQuantity = discountedQuantity;
		this.specialDiscountPercent = discountPercent;				
	}
	
	public int getSpecialTriggerQuantity() {
		return this.specialTriggerQuantity;
	}
	
	public int getSpecialDiscountedQuantity() {
		return this.specialDiscountedQuantity;
	}
	
	public double getSpecialDiscountPercent() {
		return this.specialDiscountPercent;
	}

	public double getSubTotal(Integer quantity) {
		return this.getPrice() * quantity;
	}
}
