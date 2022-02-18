import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * main class that initiates and handles much of the back end logic of the application. Starts threads and other processes to handle GUI intensive activities.
 *
 */
public class DriverCode {
	private static User user;
	private final static int numberOfRecipies = 72;
	private static HashMap<String, FoodObject> allItems = new HashMap<String, FoodObject>();
	private static HashMap<String, BreakfastObject> breakfastItems = new HashMap<String, BreakfastObject>();
	private static HashMap<String, LunchObject> lunchItems = new HashMap<String, LunchObject>();
	private static HashMap<String, DinnerObject> dinnerItems = new HashMap<String, DinnerObject>();
	private static ArrayList<String> allNames = new ArrayList<String>();
	private static ArrayList<String> breakfastNames = new ArrayList<String>();
	private static ArrayList<String> lunchNames = new ArrayList<String>();
	private static ArrayList<String> dinnerNames = new ArrayList<String>();
	private static String[] userProfile = null;
	private static LinkedList allMeals;
	private static LinkedList validMeals;





	/**
	 * main method.
	 * @param args
	 * @throws InterruptedException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		try {
			BufferedReader userProfileReader = new BufferedReader(new FileReader("src/database/userInformation.txt"));
			userProfile = userProfileReader.readLine().split(",");
			userProfileReader.close();
		} catch (Exception e) {

		}

		if (!userProfile[0].equals("1")) {
			@SuppressWarnings("unused")
			Register registrationUI = new Register();
			while (!userProfile[0].equals("1")) {
				Thread.sleep(2000);
				try {
					BufferedReader userProfileReader = new BufferedReader(
							new FileReader("src/database/userInformation.txt"));
					userProfile = userProfileReader.readLine().split(",");
					userProfileReader.close();
				} catch (Exception e) {
				}
			}
		}
		updateUser();
	}
	
	/**
	 * Creates a new user instance with updated information.
	 * @throws FileNotFoundException
	 */
	public static void updateUser() throws FileNotFoundException {
		try {
			BufferedReader userProfileReader = new BufferedReader(
					new FileReader("src/database/userInformation.txt"));
			userProfile = userProfileReader.readLine().split(",");
			userProfileReader.close();
		} catch (Exception e) {
		}
		String firstName = userProfile[1];
		String lastName = userProfile[2];
		int averageAgeOfFamily = Integer.parseInt(userProfile[3]);
		HashMap<String, Boolean> allergies = new HashMap<String, Boolean>();
		allergies.put(userProfile[4], stringToBoolean(userProfile[5]));
		allergies.put(userProfile[6], stringToBoolean(userProfile[7]));
		allergies.put(userProfile[8], stringToBoolean(userProfile[9]));
		allergies.put(userProfile[10], stringToBoolean(userProfile[11]));
		allergies.put(userProfile[12], stringToBoolean(userProfile[13]));
		allergies.put(userProfile[14], stringToBoolean(userProfile[15]));
		
		ArrayList<Integer> allAges = new ArrayList<Integer>();
		for (int i = userProfile.length-3; i > -1; i --) {
			try {
				allAges.add(Integer.parseInt(userProfile[i]));
			}
			catch (Exception e) {
				break;
			}
		}
		
		int calories = Integer.parseInt(userProfile[userProfile.length-2]);
		boolean avoidAllergen = stringToBoolean(userProfile[userProfile.length-1]);
		
		user = new User(firstName, lastName, averageAgeOfFamily, allAges, allergies, calories, avoidAllergen);

		String[][] allFoodDatabaseEntries = new String[numberOfRecipies][10];
		BufferedReader foodReader = new BufferedReader(new FileReader("src/database/allRecipies.txt"));
		for (int i = 0; i < numberOfRecipies; i++) {
			try {
				String[] line = foodReader.readLine().split(",");
				allFoodDatabaseEntries[i] = line.clone();

			} catch (Exception e) {
			}
		}
		try {
			foodReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < numberOfRecipies; i++) {
			if (allFoodDatabaseEntries[i][0].equals("b")) {
				breakfastItems.put(allFoodDatabaseEntries[i][7], new BreakfastObject(
						stringToBoolean(allFoodDatabaseEntries[i][1]), stringToBoolean(allFoodDatabaseEntries[i][2]),
						stringToBoolean(allFoodDatabaseEntries[i][3]), stringToBoolean(allFoodDatabaseEntries[i][4]),
						stringToBoolean(allFoodDatabaseEntries[i][5]), stringToBoolean(allFoodDatabaseEntries[i][6]),
						allFoodDatabaseEntries[i][7], Integer.parseInt(allFoodDatabaseEntries[i][8]), 
						allFoodDatabaseEntries[i][9]));
				breakfastNames.add(allFoodDatabaseEntries[i][7]);
				allNames.add(allFoodDatabaseEntries[i][7]);
			} else if (allFoodDatabaseEntries[i][0].equals("l")) {
				lunchItems.put(allFoodDatabaseEntries[i][7], new LunchObject(
						stringToBoolean(allFoodDatabaseEntries[i][1]), stringToBoolean(allFoodDatabaseEntries[i][2]),
						stringToBoolean(allFoodDatabaseEntries[i][3]), stringToBoolean(allFoodDatabaseEntries[i][4]),
						stringToBoolean(allFoodDatabaseEntries[i][5]), stringToBoolean(allFoodDatabaseEntries[i][6]),
						allFoodDatabaseEntries[i][7], Integer.parseInt(allFoodDatabaseEntries[i][8]), 
						allFoodDatabaseEntries[i][9]));
				lunchNames.add(allFoodDatabaseEntries[i][7]);
				allNames.add(allFoodDatabaseEntries[i][7]);
			} else {
				dinnerItems.put(allFoodDatabaseEntries[i][7], new DinnerObject(
						stringToBoolean(allFoodDatabaseEntries[i][1]), stringToBoolean(allFoodDatabaseEntries[i][2]),
						stringToBoolean(allFoodDatabaseEntries[i][3]), stringToBoolean(allFoodDatabaseEntries[i][4]),
						stringToBoolean(allFoodDatabaseEntries[i][5]), stringToBoolean(allFoodDatabaseEntries[i][6]),
						allFoodDatabaseEntries[i][7], Integer.parseInt(allFoodDatabaseEntries[i][8]), 
						allFoodDatabaseEntries[i][9]));
				dinnerNames.add(allFoodDatabaseEntries[i][7]);
				allNames.add(allFoodDatabaseEntries[i][7]);
			}
		}
		allItems.putAll(breakfastItems);
		allItems.putAll(lunchItems);
		allItems.putAll(dinnerItems);
		
		getValidMeals();
		
		@SuppressWarnings("unused")
		MainUI mainUI = new MainUI(user);
	}
	
	/**
	 * Populates two Linked Lists. (1) allMeals contains all the possible meals (2) validMeals prunes the data by potentially removing meals that contain allergens and meals that do not fall within 250 calories of the user set daily value.
	 */
	public static void getValidMeals() {
		
		allMeals = new LinkedList();
		validMeals = new LinkedList();
		int userCalories = user.userSetCalories();
		
		for (int breakfastIndex = 0; breakfastIndex < breakfastItems.size(); breakfastIndex ++) {
			for (int lunchIndex = 0; lunchIndex < lunchItems.size(); lunchIndex ++) {
				for (int dinnerIndex = 0; dinnerIndex < dinnerItems.size(); dinnerIndex ++) {
					allMeals.add(new Meal(breakfastItems.get(breakfastNames.get(breakfastIndex)), lunchItems.get(lunchNames.get(lunchIndex)), dinnerItems.get(dinnerNames.get(dinnerIndex)) ));
				}
			}
		}
		
		if (user.getAllergenStatus()) {
			int[] userAllergy = user.allergenArray();
			for (int i = 0; i < allMeals.size(); i ++) {
				int[] foodAllergy = allMeals.get(i).allergenArray();
				boolean safe = true;
				for (int j = 0; j < 5; j ++) {
					if (userAllergy[j] == 1 && foodAllergy[j] == 1) {
						safe = false;
						break;
					}	
				}
				if (safe) {
					if (allMeals.get(i).getCalories() > userCalories - 250 && allMeals.get(i).getCalories() < userCalories + 250) {
						validMeals.add(allMeals.get(i));
					} 
				}
			}
		}
		else {
			for (int i = 0; i < allMeals.size(); i ++) {
				if (allMeals.get(i).getCalories() > userCalories - 250 && allMeals.get(i).getCalories() < userCalories + 250) {
					validMeals.add(allMeals.get(i));
				}
			}
		}
		

	}
	
	/**
	 * Getter method.
	 * @return true if there is a saved plan in the database.
	 */
	public static boolean hasSavedPlan() {
		try {
			BufferedReader planReader = new BufferedReader(new FileReader("src/database/mealPlan.txt"));
			String line = planReader.readLine();
			planReader.close();
			if (line == null) {
				return false;
			}
			return true;
		}
		catch (Exception e) {
			
		}
		return false;
	}
		
	/**
	 * Getter method.
	 * @return a Linked List populated using the plan saved in the database.
	 */
	public static LinkedList getSavedPlan() {
		
		String [][] stringSavedPlan = new String[7][3];

		
		try {
			BufferedReader planReader = new BufferedReader(new FileReader("src/database/mealPlan.txt"));
			for (int i = 0; i < 7; i ++) {
				stringSavedPlan[i] = planReader.readLine().split(",");
			}
			planReader.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LinkedList savedMealPlan = new LinkedList();
		for (int i = 0; i < 7; i ++) {
			savedMealPlan.add(new Meal(breakfastItems.get(stringSavedPlan[i][0]), lunchItems.get(stringSavedPlan[i][1]), dinnerItems.get(stringSavedPlan[i][2])));
		}
		return savedMealPlan;
		
		
	}
	
	/**
	 * Getter method that also saves the created Linked List into memory.
	 * @return a new Linked List populated randomly with valid Meal objects.
	 */
	public static LinkedList getWeeklyPlan() {

		LinkedList weeklyMealPlan = new LinkedList();

		
		
		String previousBreakfast = "";
		String previousLunch = "";
		String previousDinner = "";
		for (int i = 0; i < 7; i ++) {
			int random = (int) Math.floor(Math.random() * (validMeals.size()));
			Meal currentSelection = validMeals.get(random);
			String breakfast = currentSelection.getBreakfast().getFoodName();
			String lunch = currentSelection.getLunch().getFoodName();
			String dinner = currentSelection.getDinner().getFoodName();
			
			int numberOfAttempts = 0;
			while (breakfast.equals(previousBreakfast) || lunch.equals(previousLunch) || dinner.equals(previousDinner) ) {
				random = (int) Math.floor(Math.random() * (validMeals.size()));
				currentSelection = validMeals.get(random);
				breakfast = currentSelection.getBreakfast().getFoodName();
				lunch = currentSelection.getLunch().getFoodName();
				dinner = currentSelection.getDinner().getFoodName();
				numberOfAttempts ++;
				if (numberOfAttempts > 1000) {
					System.out.println(0/0);
				}

			}
			previousBreakfast = "" + breakfast;
			previousLunch = "" + lunch;
			previousDinner = "" + dinner;
			
			weeklyMealPlan.add(validMeals.get(random));
		}
		
		final String COMMA = ",";
		try {
			FileWriter keyWriter = new FileWriter("src/database/mealPlan.txt", false);
			for (int i = 0; i < 7; i ++) {
				keyWriter.write(weeklyMealPlan.get(i).getBreakfast().getFoodName() + COMMA);
				keyWriter.write(weeklyMealPlan.get(i).getLunch().getFoodName() + COMMA);
				keyWriter.write(weeklyMealPlan.get(i).getDinner().getFoodName());
				keyWriter.write(System.getProperty( "line.separator" ));
			}
			keyWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return weeklyMealPlan;

	}
	
	/**
	 * Local method used to convert strings to boolean values.
	 * @param input string "0" or other.
	 * @return false if 0, otherwise true.
	 */
	private static boolean stringToBoolean(String input) {
		if (input.equals("0")) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Getter method.
	 * @return int value. The total number of recipes present in the recipe book.
	 */
	public static int getNumberOfRecipies() {
		return numberOfRecipies;
	}
	
	/**
	 * Getter method.
	 * @return Hashmap containing all the BreakfastObjects where the key is the name of the food.
	 */
	public static HashMap <String, BreakfastObject> getBreakfastMap() {
		return breakfastItems;
	}
	/**
	 * Getter method.
	 * @return Hashmap containing all the LunchObjects where the key is the name of the food.
	 */
	public static HashMap <String, LunchObject> getLunchMap() {
		return lunchItems;
	}
	/**
	 * Getter method.
	 * @return Hashmap containing all the DinnerObjects where the key is the name of the food.
	 */
	public static HashMap <String, DinnerObject> getDinnerMap() {
		return dinnerItems;
	}
	/**
	 * Getter method.
	 * @return HashMap containing all the FoodObjects where the key is the name of the food.
	 */
	public static HashMap <String, FoodObject> getAllItems() {
		return allItems;
	}
	/**
	 * Getter method
	 * @return a string ArrayList that contains the name of all the food objects.
	 */
	public static ArrayList<String> getAllNames() {
		return allNames;
	}

}
