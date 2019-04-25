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
		
		double subTotal = 0.0;
		
		if(specialTriggerQuantity > 0) {
			int timesSpecialCanBeApplied = (int) Math.floor(quantity/(specialTriggerQuantity + specialDiscountedQuantity));
			int remainingQuantity = quantity % (specialTriggerQuantity + specialDiscountedQuantity);
			subTotal = timesSpecialCanBeApplied*(this.getPrice()*this.specialTriggerQuantity + this.getPrice()*specialDiscountedQuantity*(1-specialDiscountPercent/100)) + this.getPrice()*remainingQuantity; 
		} else {
			subTotal = this.getPrice() * quantity;
		}
		
		return subTotal;
	}
}
