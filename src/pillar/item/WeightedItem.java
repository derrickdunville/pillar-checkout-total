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
	
	public void setSpecial(double triggerWeight, double discountedWeight, double discountPercent) throws RangeException {
		if(triggerWeight == 0.0 && discountedWeight == 0.0 && discountPercent == 0.0) {
			specialTriggerWeight = 0.0;
			specialDiscountedWeight = 0.0;
			specialDiscountPercent = 0.0;
			return;
		}
		if(triggerWeight == 0.0 || discountedWeight == 0.0 || discountPercent == 0.0) {
			throw new RangeException("trigger weight, discounted weight and discount percent must either all be 0.0 or all greater than 0.0");
		}
		
		if(triggerWeight < 0) throw new RangeException("trigger weight must be greater than 0.0");
		
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
