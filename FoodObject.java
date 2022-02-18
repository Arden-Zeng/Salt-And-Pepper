/**
 * 
 * This class represents a generic food.
 *
 */
public class FoodObject {
	private boolean containsPeanuts;
	private boolean containsEggs;
	private boolean containsMilk;
	private boolean containsSoy;
	private boolean containsFish;
	private boolean containsShellfish;
	private String foodName;
	private int calories;
	private String url;
	
	/**
	 * Food object constructor.
	 * 
	 * @param containsPeanuts Parameter field requesting a boolean value which is true if the object contains peanuts.
	 * @param containsEggs Parameter field requesting a boolean value which is true if the object contains eggs.
	 * @param containsMilk Parameter field requesting a boolean value which is true if the object contains milk.
	 * @param containsSoy Parameter field requesting a boolean value which is true if the object contains soy.
	 * @param containsFish Parameter field requesting a boolean value which is true if the object contains fish.
	 * @param containsShellfish Parameter field requesting a boolean value which is true if the object contains shellfish.
	 * @param foodName Parameter field requesting the food name.
	 * @param calories Parameter field requesting the caloric value of the object.
	 * @param url Parameter field requesting the HTTPS URL of the recipe of the object.
	 */
	public FoodObject(boolean containsPeanuts, boolean containsEggs, boolean containsMilk, boolean containsSoy, boolean containsFish, boolean containsShellfish, String foodName, int calories, String url) {
		this.containsPeanuts = containsPeanuts;
		this.containsEggs = containsEggs;
		this.containsMilk = containsMilk;
		this.containsSoy = containsSoy;
		this.containsFish = containsFish;
		this.containsShellfish = containsShellfish;
		this.foodName = foodName;
		this.calories = calories;
		this.url = url;
	}
	/**
	 * Getter method.
	 * @return true if the food object contains peanuts.
	 */
	public boolean containsPeanuts() {
		return containsPeanuts;
	}
	/**
	 * Getter method.
	 * @return true if the food object contains eggs.
	 */
	public boolean containsEggs() {
		return containsEggs;
	}
	/**
	 * Getter method.
	 * @return true if the food object contains milk.
	 */
	public boolean containsMilk() {
		return containsMilk;
	}
	/**
	 * Getter method.
	 * @return true if the food object contains soy.
	 */
	public boolean containsSoy() {
		return containsSoy;
	}	
	/**
	 * Getter method.
	 * @return true if the food object contains fish.
	 */
	public boolean containsFish() {
		return containsFish;
	}
	/**
	 * Getter method.
	 * @return true if the food object contains shellfish.
	 */
	public boolean containsShellfish() {
		return containsShellfish;
	}
	/**
	 * Getter method.
	 * @return a string which holds the name of the food.
	 */
	public String getFoodName() {
		return foodName;
	}
	/**
	 * Getter method.
	 * @return an int holding the caloric value of the food.
	 */
	public int getCalories() {
		return calories;
	}
	/**
	 * Getter method.
	 * @return the HTTPS URL of the food's recipe as a String.
	 */
	public String getRecipie() {
		return url;
	}
}
