package pillar.item;

public class QuantifiedItem extends AbstractItem<Integer> {

	private int specialTriggerQuantity; 
	private int specialDiscountedQuantity;
	private double specialDiscountPercent;
	private double specialDiscountPrice; 
	
	public QuantifiedItem(String itemName, double itemPrice) {
		super(itemName, itemPrice);
		specialTriggerQuantity = 0;
		specialDiscountedQuantity = 0;
		specialDiscountPercent = 0.0;
	}
	
	public void setSpecial(int triggerQuantity, int discountedQuantity, double discountPercent) {
		specialTriggerQuantity = triggerQuantity;
		specialDiscountedQuantity = discountedQuantity;
		specialDiscountPercent = discountPercent;				
	}
	
	public void setSpecial(int triggerQuantity, double discountPrice) {
		specialTriggerQuantity = triggerQuantity;
		specialDiscountPrice = discountPrice;
	}
	
	public int getSpecialTriggerQuantity() {
		return specialTriggerQuantity;
	}
	
	public int getSpecialDiscountedQuantity() {
		return specialDiscountedQuantity;
	}
	
	public double getSpecialDiscountPercent() {
		return specialDiscountPercent;
	}

	public double getDiscountPrice() {
		return specialDiscountPrice;
	}
	
	public double getSubTotal(Integer quantity) {
		// price * # of trigger quantities * trigger quantity + price * discount quantities * (1 - discount percent/100) + price * remaining quantity
		
		double subTotal = 0.0;
		
		// Special is set to form of Buy N, get M at X% Off
		if(specialTriggerQuantity > 0 && specialDiscountedQuantity > 0) {
			int timesSpecialCanBeApplied = (int) Math.floor(quantity/(specialTriggerQuantity + specialDiscountedQuantity));
			int remainingQuantity = quantity % (specialTriggerQuantity + specialDiscountedQuantity);
			subTotal = this.getPrice()*(timesSpecialCanBeApplied*(specialTriggerQuantity + specialDiscountedQuantity*(1-specialDiscountPercent/100)) + remainingQuantity); 
		} else if (specialTriggerQuantity > 0 && specialDiscountPrice > 0) {
			int timesSpecialCanBeApplied = (int) Math.floor(quantity/specialTriggerQuantity);
			int remainingQuantity = quantity % (specialTriggerQuantity);
			subTotal = timesSpecialCanBeApplied*specialDiscountPrice + this.getPrice()*remainingQuantity; 
		} else {
			subTotal = this.getPrice() * quantity;
		}
		
		return subTotal;
	}
}
