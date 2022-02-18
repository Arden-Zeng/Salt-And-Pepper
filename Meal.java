
/**
 * Generic Meal object.
 *
 */
public class Meal {
	private BreakfastObject breakfast;
	private LunchObject lunch;
	private DinnerObject dinner;
	
	/**
	 * Meal object constructor.
	 * @param breakfast Parameter field requesting a BreakfastObject.
	 * @param lunch Parameter field requesting a LunchObject.
	 * @param dinner Parameter field requesting a DinnerObject.
	 * @see BreakfastObject
	 * @see LunchObject
	 * @see DinnerObject
	 */
	public Meal (BreakfastObject breakfast, LunchObject lunch, DinnerObject dinner) {
		this.breakfast = breakfast;
		this.lunch = lunch;
		this.dinner = dinner;
	}
	
	/**
	 * Getter method.
	 * @return the BreakfastObject reference.
	 * @see BreakfastObject
	 */
	public BreakfastObject getBreakfast() {
		return breakfast;
	}
	/**
	 * Getter method.
	 * @return the LunchObject reference.
	 * @see LunchObject
	 */
	public LunchObject getLunch() {
		return lunch;
	}
	/**
	 * Getter method.
	 * @return the DinnerObject reference.
	 * @see DinnerObject
	 */
	public DinnerObject getDinner() {
		return dinner;
	}
	/**
	 * Getter method.
	 * @return the total caloric value of the meal. int value.
	 */
	public int getCalories() {
		return breakfast.getCalories() + lunch.getCalories() + dinner.getCalories();
	}
	/**
	 * Getter method.
	 * @return true if the meal contains peanuts.
	 */
	public boolean containsPeanuts() {
		if (breakfast.containsPeanuts() || lunch.containsPeanuts() || dinner.containsPeanuts()) {
			return true;
		}
		return false;
	}
	/** 
	 * Getter method.
	 * @return true if the meal contains eggs.
	 */
	public boolean containsEggs() {
		if (breakfast.containsEggs() || lunch.containsEggs() || dinner.containsEggs()) {
			return true;
		}
		return false;
	}
	/**
	 * Getter method.
	 * @return true if the meal contains milk.
	 */
	public boolean containsMilk() {
		if (breakfast.containsMilk() || lunch.containsMilk() || dinner.containsMilk()) {
			return true;
		}
		return false;
	}
	/**
	 * Getter method.
	 * @return true if the meal contains soy.
	 */
	public boolean containsSoy() {
		if (breakfast.containsSoy() || lunch.containsSoy() || dinner.containsSoy()) {
			return true;
		}
		return false;
	}
	/**
	 * Getter method.
	 * @return true if the meal contains fish.
	 */
	public boolean containsFish() {
		if (breakfast.containsFish() || lunch.containsFish() || dinner.containsFish()) {
			return true;
		}
		return false;
	}
	/**
	 * Getter method.
	 * @return true if the meal contains shellfish.
	 */
	public boolean containsShellfish() {
		if (breakfast.containsShellfish() || lunch.containsShellfish() || dinner.containsShellfish()) {
			return true;
		}
		return false;
	}
	/**
	 * Getter method.
	 * @return an int array implementation of a boolean array that represents all the allergens and their respective status (present or not present).
	 */
	public int[] allergenArray () {
		int[] allergen = {booleanToInteger(containsPeanuts()),booleanToInteger(containsEggs()),booleanToInteger(containsMilk()),booleanToInteger(containsSoy()),booleanToInteger(containsFish()),booleanToInteger(containsShellfish())};
		return (allergen);
	}
	/**
	 * Local method.
	 * @param x boolean value to be converted into an integer.
	 * @return 1 if true, 0 if false.
	 */
	private int booleanToInteger(boolean x) {
		if (x) {
			return 1;
		}
		return 0;
	}
}
