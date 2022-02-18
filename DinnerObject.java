/**
 * 
 * This class represents a generic dinner food.
 *
 */
public class DinnerObject extends FoodObject {
	/**
	 * Dinner object constructor.
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
	
	public DinnerObject(boolean containsPeanuts, boolean containsEggs, boolean containsMilk, boolean containsSoy, boolean containsFish, boolean containsShellfish, String foodName, int calories, String url) {
		super(containsPeanuts, containsEggs, containsMilk, containsSoy, containsFish, containsShellfish, foodName, calories, url);
	}
}