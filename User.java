import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Generic user object template.
 *
 */
public class User {
	private String firstName;
	private String lastName;
	private int [] ages;
	private int averageAgeOfFamily;
	private HashMap<String, Boolean> allergies;
	private int calories;
	private boolean avoidAllergen;
	
	/**
	 * @param firstName parameter field requesting the first name of the user.
	 * @param lastName parameter field requesting the last name of the user.
	 * @param averageAgeOfFamily parameter field requesting the average age of the family.
	 * @param allAges parameter field requesting the ages of each individual family member.
	 * @param allergies parameter field requesting allergies that the family has.
	 * @param calories parameter field requesting the desired calories that the user wishes to consume per day.
	 * @param avoidAllergen parameter field requesting if the user wishes to remove meals that contain allergens.
	 */
	public User(String firstName, String lastName, int averageAgeOfFamily, ArrayList<Integer> allAges, HashMap<String, Boolean> allergies, int calories, boolean avoidAllergen) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.averageAgeOfFamily = averageAgeOfFamily;
		Collections.reverse(allAges);
		ages = new int[allAges.size()];
		for (int i = 0; i < allAges.size(); i ++) {
			ages[i] = allAges.get(i);
		}
		this.allergies = allergies;
		this.calories = calories;
		this.avoidAllergen = avoidAllergen;
	}
	/**
	 * Getter method.
	 * @return int value representing the number of calories that the user is recommended per day.
	 */
	public int recommendedNumberOfCaloriesAdult() {
		if (averageAgeOfFamily < 50) {
			return 2000;
		}
		else {
			return 1000;
		}
	}
	/**
	 * Getter method.
	 * @return an int array implementation of a boolean array that represents all the allergens and their respective status (allergic or not allergic).
	 */
	public int[] allergenArray() {
		int[] allergen = {booleanToInteger(getPeanuts()),booleanToInteger(getEggs()),booleanToInteger(getMilk()),booleanToInteger(getSoy()),booleanToInteger(getFish()),booleanToInteger(getShellfish())};
		return (allergen);
	}
	/**
	 * Getter method.
	 * @return true if the user wishes to remove meals that contain foods that they are allergic to.
	 */
	public boolean getAllergenStatus() {
		return avoidAllergen;
	}
	/**
	 * Getter method.
	 * @return the number of daily calories that the user has set.
	 */
	public int userSetCalories() {
		return calories;
	}
	/**
	 * Getter method.
	 * @return the first name of the user as a String.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Getter method.
	 * @return the last name of the user as a String.
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Getter method.
	 * @return an int representing the average age of the family.
	 */
	public int getAverageAge() {
		return averageAgeOfFamily;
	}
	/**
	 * Getter method.
	 * @return hashmap matrix representing all the allergens and their respective status (allergic or not allergic).
	 */
	public HashMap<String, Boolean> getAllergies() {
		return allergies;
	}
	/**
	 * Getter method.
	 * @return true if the user is allergic to peanuts.
	 */
	public boolean getPeanuts() {
		return allergies.get("Peanuts");
	}
	/**
	 * Getter method.
	 * @return true if the user is allergic to eggs.
	 */
	public boolean getEggs() {
		return allergies.get("Eggs");
	}
	/**
	 * Getter method.
	 * @return true if the user is allergic to milk.
	 */
	public boolean getMilk() {
		return allergies.get("Milk");
	}
	/**
	 * Getter method.
	 * @return true if the user is allergic to soy.
	 */
	public boolean getSoy() {
		return allergies.get("Soy");
	}
	/**
	 * Getter method.
	 * @return true if the user is allergic to fish.
	 */
	public boolean getFish() {
		return allergies.get("Fish");
	}
	/**
	 * Getter method.
	 * @return true if the user is allergic to shellfish.
	 */
	public boolean getShellfish() {
		return allergies.get("Shellfish");
	}
	/**
	 * Getter method.
	 * @return an int array storing all the age values of the family.
	 */
	public int[] getAllAges() {
		return ages;
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
