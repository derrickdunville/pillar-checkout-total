package pillar.item;

import pillar.item.exception.RangeException;

public class WeightedItem extends AbstractItem<Double> {

	private double specialTriggerWeight; 
	private double specialDiscountedWeight;
	private double specialDiscountPercent;
	
	public WeightedItem(String itemName, double itemPrice) throws RangeException {
		super(itemName, itemPrice);
		specialTriggerWeight = 0.0;
		specialDiscountedWeight = 0.0;
		specialDiscountPercent = 0.0;
	}
	
	public void setSpecial(double triggerWeight, double discountedWeight, double discountPercent) {
		specialTriggerWeight = triggerWeight;
		specialDiscountedWeight = discountedWeight;
		specialDiscountPercent = discountPercent;
	}

	@Override
	public double getSubTotal(Double weight) {
		double subTotal = 0.0;
		
		// Special is set to form of Buy N, get M at X% Off
		if(specialTriggerWeight > 0) {
			int timesSpecialCanBeApplied = (int) Math.floor(weight/(specialTriggerWeight + specialDiscountedWeight));
			double remainingWeight = weight % (specialTriggerWeight + specialDiscountedWeight);
			subTotal = getPrice()*(timesSpecialCanBeApplied*(specialTriggerWeight + specialDiscountedWeight*(1-specialDiscountPercent/100)) + remainingWeight);	
			
		// No Special set
		} else {
			subTotal = this.getPrice() * weight;
		}
		
		return subTotal;
	}
}
