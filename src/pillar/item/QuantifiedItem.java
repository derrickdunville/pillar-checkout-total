package pillar.item;

public class QuantifiedItem extends AbstractItem<Integer> {

	private int specialTriggerQuantity; 
	private int specialDiscountedQuantity;
	private double specialDiscountPercent;
	private double specialDiscountPrice; 
	private int specialQuanitityLimit; 
	
	public QuantifiedItem(String itemName, double itemPrice) {
		super(itemName, itemPrice);
		specialTriggerQuantity = 0;
		specialDiscountedQuantity = 0;
		specialDiscountPercent = 0.0;
		specialQuanitityLimit = 0;
	}
	
	public void setSpecial(int triggerQuantity, int discountedQuantity, double discountPercent) {
		if(0.0 <= discountPercent && 100.00 >= discountPercent && triggerQuantity >= 0 && discountedQuantity >= 0) {
			specialTriggerQuantity = triggerQuantity;
			specialDiscountedQuantity = discountedQuantity;
			specialDiscountPercent = discountPercent;
			specialDiscountPrice = 0.0;
		}
	}
	
	public void setSpecial(int triggerQuantity, double discountPrice) {
		if(triggerQuantity == 0 && discountPrice == 0.00) {
			specialTriggerQuantity = 0;
			specialDiscountPrice = 0.0;
			return;
		}
		
		if(discountPrice > 0 && triggerQuantity > 0 && triggerQuantity*getPrice() > discountPrice) {
			specialTriggerQuantity = triggerQuantity;
			specialDiscountPrice = discountPrice;
			specialDiscountedQuantity = 0;
			specialDiscountPercent = 0.0;
		}
		
	}
	
	public void setSpecialLimit(int limit) {
		specialQuanitityLimit = limit;
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
	
	public int getSpecialLimit() {
		return specialQuanitityLimit;
	}
	
	public double getSubTotal(Integer quantity) {
		// price * # of trigger quantities * trigger quantity + price * discount quantities * (1 - discount percent/100) + price * remaining quantity
		
		double subTotal = 0.0;
		
		// Special is set to form of Buy N, get M at X% Off
		if(specialTriggerQuantity > 0 && specialDiscountedQuantity > 0) {
			int timesSpecialCanBeApplied = (int) Math.floor(quantity/(specialTriggerQuantity + specialDiscountedQuantity));
			int remainingQuantity = quantity % (specialTriggerQuantity + specialDiscountedQuantity);
			if(quantity <= specialQuanitityLimit || specialQuanitityLimit == 0) {
				subTotal = getPrice()*(timesSpecialCanBeApplied*(specialTriggerQuantity + specialDiscountedQuantity*(1-specialDiscountPercent/100)) + remainingQuantity);	
			} else {
				int restrictedRemainingQuantity = timesSpecialCanBeApplied*(specialDiscountedQuantity + specialTriggerQuantity) + remainingQuantity - specialQuanitityLimit;
				subTotal = getPrice()*(Math.floor(specialQuanitityLimit/(specialTriggerQuantity + specialDiscountedQuantity))*(specialTriggerQuantity + specialDiscountedQuantity*(1-specialDiscountPercent/100)) + restrictedRemainingQuantity);	
			}
			
		// Special is set to form N for $X 
		} else if (specialTriggerQuantity > 0 && specialDiscountPrice > 0) {
			int timesSpecialCanBeApplied = (int) Math.floor(quantity/specialTriggerQuantity);
			int remainingQuantity = quantity % (specialTriggerQuantity);
			if(quantity <= specialQuanitityLimit || specialQuanitityLimit == 0) {
				subTotal = timesSpecialCanBeApplied*specialDiscountPrice + getPrice()*remainingQuantity;
			} else {
				int restrictedRemainingQuantity = timesSpecialCanBeApplied*specialTriggerQuantity + remainingQuantity - specialQuanitityLimit;
				subTotal = Math.floor(specialQuanitityLimit/specialTriggerQuantity)*specialDiscountPrice + getPrice()*restrictedRemainingQuantity;
			}
			
			
		// No Special set
		} else {
			subTotal = this.getPrice() * quantity;
		}
		
		return subTotal;
	}
}
