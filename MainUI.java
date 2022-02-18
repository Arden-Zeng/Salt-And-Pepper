import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URL;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * Main GUI.
 *
 */
@SuppressWarnings("serial")
public class MainUI extends JPanel implements ActionListener {
	
	private JLabel submitSearch;
	private JTextField searchBar;
	private SearchPane searchPane;
	private JScrollPane searchResults;
	private JPanel recipieBook;
	private JFrame mainFrame;
	private JTabbedPane displayFrame;
	private JPanel settings;
	
	private JButton generate;
	private JButton [][] mealGrid;
	private JPanel mealPlanner;
	private JLabel numberOfCal;
	private JLabel error;
	
	private static JTextField firstNameField;
	private static JTextField lastNameField;

	private static JLabel firstNameLabel;
	private static JLabel lastNameLabel;
	private static JLabel familySizeLabel;
	private static JLabel invalidInputLabel;
	
	private static JButton submitAll;

	@SuppressWarnings("rawtypes")
	private static JComboBox familySizeDropdown;
	private static int numberOfMembers;
	private static JTextField [] agePrompts = new JTextField[6];
	private static int [] allAges;
	
	private static JLabel allergiesLabel;
	private static JCheckBox peanuts;
	private static JCheckBox eggs;
	private static JCheckBox milk;
	private static JCheckBox soy;
	private static JCheckBox fish;
	private static JCheckBox shellfish;
	
	private JLabel nutritionSettings;
	private JCheckBox avoidAllergens;
	private JLabel recommendedNumberOfCalories;
	private JTextField userCalorieInput;
	private JLabel invalidCalorieLabel;
		
	private static int numberOfCalories;
	private static boolean avoidAllergenFood;
	private static User user;
	private static String firstName;
	private static String lastName;
	private static int averageAgeOfFamily;
	private static HashMap<String, Boolean> allergies;
	
	/**
	 * MainUI constructor.
	 * @param userProfile user object, used to get preferences and stored values.
	 */
	public MainUI(User userProfile) {
		user = userProfile;
		mainFrame = new JFrame("Meal Genie");
		mainFrame.setSize(750, 500);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		
		mealPlanner = new JPanel();
		mealPlanner.setLayout(null);
		JLabel mealPlannerText = new JLabel(user.getFirstName() + "'s Meal Planner");
		mealPlannerText.setFont(new Font("Calibri", Font.BOLD, 24));
		mealPlannerText.setBounds(15, 0, 750, 50);
		
		JLabel sunday = new JLabel("Sunday", SwingConstants.CENTER);
		sunday.setBounds(15,40,100,30);
		
		JLabel monday = new JLabel("Monday", SwingConstants.CENTER);
		monday.setBounds(115,40,100,30);

		JLabel tuesday = new JLabel("Tuesday", SwingConstants.CENTER);
		tuesday.setBounds(215,40,100,30);

		JLabel wednesday = new JLabel("Wednesday", SwingConstants.CENTER);
		wednesday.setBounds(315,40,100,30);

		JLabel thursday = new JLabel("Thursday", SwingConstants.CENTER);
		thursday.setBounds(415,40,100,30);

		JLabel friday = new JLabel("Friday", SwingConstants.CENTER);
		friday.setBounds(515,40,100,30);

		JLabel saturday = new JLabel("Saturday", SwingConstants.CENTER);
		saturday.setBounds(615,40,100,30);
		
	    Border border = BorderFactory.createLineBorder(Color.BLACK);
		mealGrid = new JButton[3][7];
		for (int i = 0; i < 3; i ++) {
			for (int j = 0; j < 7; j ++) {
				mealGrid[i][j] = new JButton("");
				mealGrid[i][j].setBounds(15 + (100*j), 70 + (100*i), 100, 100);
				mealGrid[i][j].setBackground(Color.LIGHT_GRAY);
				mealGrid[i][j].setBorder(border);
				mealGrid[i][j].addActionListener(this);
				mealPlanner.add(mealGrid[i][j]);
			}
		}
				
		generate = new JButton("Generate");
		generate.setBackground(Color.LIGHT_GRAY);
		generate.setBounds(615,385,100,35);
		generate.addActionListener(this);
		
		
		numberOfCal = new JLabel("Average Number Of Calories Per Day: ");
		numberOfCal.setBounds(16,385,350,25);
		
		if (DriverCode.hasSavedPlan()) {
			displayMealPlan(false);
		}
		
		error = new JLabel("Could not generate a plan using your current settings. Please change them and try again.");
		error.setBounds(16, 400, 500, 25);
		error.setForeground(Color.red);
		error.setVisible(false);

		
		displayFrame = new JTabbedPane(JTabbedPane.TOP);
		
		mealPlanner.add(numberOfCal);
		mealPlanner.add(generate);
		mealPlanner.add(error);
		mealPlanner.add(mealPlannerText);

		mealPlanner.add(sunday);
		mealPlanner.add(monday);
		mealPlanner.add(tuesday);
		mealPlanner.add(wednesday);
		mealPlanner.add(thursday);
		mealPlanner.add(friday);
		mealPlanner.add(saturday);

		
		recipieBook = new JPanel();
		recipieBook.setLayout(null);
		JPanel panelNameAndSearchBar = new JPanel();
		panelNameAndSearchBar.setBounds(0,0,750, 100);
		panelNameAndSearchBar.setLayout(null);

		
		JLabel recipieBookText = new JLabel("Recipie Book");
		recipieBookText.setFont(new Font("Calibri", Font.BOLD, 24));
		recipieBookText.setBounds(15, 0, 175, 50);
		
		submitSearch = new JLabel("Search: ");
		submitSearch.setBounds(75, 70, 50, 25);
		submitSearch.setBackground(Color.LIGHT_GRAY);

		searchBar = new JTextField("");
		searchBar.setBounds(125, 70, 100, 25);
		
		//add action listener
		
		searchPane = new SearchPane("");
		searchResults = new JScrollPane(searchPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		searchResults.setBounds(75, 100, 600, 250);
		
		panelNameAndSearchBar.add(recipieBookText);
		panelNameAndSearchBar.add(searchBar);
		panelNameAndSearchBar.add(submitSearch);
		
		recipieBook.add(panelNameAndSearchBar);
		recipieBook.add(searchResults);

		
		searchResults.getVerticalScrollBar().setUnitIncrement(20);

		
		settings = new JPanel();
		settings.setLayout(null);
		JLabel settingsText = new JLabel("Meal Genie Settings");
		settingsText.setFont(new Font("Calibri", Font.BOLD, 24));
		settingsText.setBounds(15, 0, 250, 50);
		
		
		
		settings.add(settingsText);
		
		
		
		displayFrame.addTab("Meal Planner", null, mealPlanner, null);
		displayFrame.addTab("Recipie Book", null, recipieBook, null);
		displayFrame.addTab("Meal Genie Settings", null, settings, null);
		
		
		Thread t1 = new Thread(new Runnable() {
		    String currentResult;
			@Override
		    public void run() {
		    	while (true) {
		    		try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		if (!searchBar.getText().equals(currentResult)) {
			    		currentResult = searchBar.getText();
						recipieBook.remove(searchResults);
						recipieBook.updateUI();
						searchPane = new SearchPane(searchBar.getText());
						searchResults = new JScrollPane(searchPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
						searchResults.setBounds(75, 100, 600, 250);
						searchResults.getVerticalScrollBar().setUnitIncrement(20);
						recipieBook.add(searchResults);
						recipieBook.updateUI();
		    		}

		    	}
			}
		});  
		t1.start();
		
		collectUserInfo(false);
		
		mainFrame.validate();
		mainFrame.getContentPane().add(displayFrame);
		mainFrame.setVisible(true);
		
		
		
	}
	
	/**
	 * Populates settings pane with graphical elements.
	 * @param createAdditionalAgeFields updates UI with the appropriate number of JTextFields if true.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void collectUserInfo(boolean createAdditionalAgeFields) {
		if (!createAdditionalAgeFields) {	

			firstNameLabel = new JLabel("First Name: ");
			firstNameLabel.setBounds(10, 60, 150, 25);
			
			lastNameLabel = new JLabel("Last Name: ");
			lastNameLabel.setBounds(10, 90, 150, 25);
			
			familySizeLabel = new JLabel("Family Size: ");
			familySizeLabel.setBounds(10, 120, 150, 25);
			
			firstNameField = new JTextField(user.getFirstName());
			firstNameField.setBounds(100, 60, 160, 25);
			
			lastNameField = new JTextField(user.getLastName());
			lastNameField.setBounds(100, 90, 160, 25);

			submitAll = new JButton("Submit");
			submitAll.setBounds(10, 370, 100, 25);
			submitAll.addActionListener(this);
			submitAll.setBackground(Color.LIGHT_GRAY);
			
			invalidInputLabel = new JLabel("Invalid Input! Please try again.");
			invalidInputLabel.setBounds(10, 180, 200, 25);
			invalidInputLabel.setForeground(Color.red);
			invalidInputLabel.setVisible(false);
			
			String [] familySizeOptions = {"1","2","3","4","5","6"};
			numberOfMembers = user.getAllAges().length;
			familySizeDropdown = new JComboBox(familySizeOptions);
			familySizeDropdown.setSelectedIndex(numberOfMembers-1);
			familySizeDropdown.addActionListener(this);
			familySizeDropdown.setBounds(100, 120, 160, 25);			
			String textFieldFiller = "Enter Age ";
			for (int i = 0; i < 6; i ++) {
				String modifiedFiller = textFieldFiller + (i + 1);
				if (i < user.getAllAges().length) {
					agePrompts[i] = new JTextField(Integer.toString(user.getAllAges()[i]));
				}
				else {
					agePrompts[i] = new JTextField(modifiedFiller);
				}
				agePrompts[i].setBounds(10 + (80)*(i), 150, 75, 25 );
				settings.add(agePrompts[i]);
				agePrompts[i].setVisible(false);
			}
			for (int i = 0; i < user.getAllAges().length; i ++) {
				agePrompts[i].setVisible(true);
			}
			
			allergiesLabel = new JLabel("Please check all allergies that apply to your family: ");
			allergiesLabel.setBounds(10, 210, 300, 25);
			
			peanuts = new JCheckBox("Peanuts");
			peanuts.setBounds(10, 240, 100, 25);
			peanuts.setSelected(user.getPeanuts());
			
			eggs = new JCheckBox("Eggs");
			eggs.setBounds(10, 270, 100, 25);
			eggs.setSelected(user.getEggs());
			
			milk = new JCheckBox("Milk");
			milk.setBounds(10, 300, 100, 25);
			milk.setSelected(user.getMilk());
			
			soy = new JCheckBox("Soy");
			soy.setBounds(150, 240, 100, 25);
			soy.setSelected(user.getSoy());
			
			fish = new JCheckBox("Fish");
			fish.setBounds(150, 270, 100, 25);
			fish.setSelected(user.getFish());
			
			shellfish = new JCheckBox("Shellfish");
			shellfish.setBounds(150, 300, 100, 25);
			shellfish.setSelected(user.getShellfish());
			
			JLabel settingsText = new JLabel("General Settings");
			settingsText.setFont(new Font("Calibri", Font.BOLD, 24));
			settingsText.setBounds(15, 0, 250, 50);
			
			nutritionSettings = new JLabel("Nutrition Settings");
			nutritionSettings.setFont(new Font("Calibri", Font.BOLD, 24));
			nutritionSettings.setBounds(300, 0, 300, 50);

			avoidAllergens = new JCheckBox("Avoid planning meals with allergens");
			avoidAllergens.setBounds(300, 60, 230, 25);
			avoidAllergens.setSelected(user.getAllergenStatus());
			
			numberOfCalories = user.userSetCalories();
			recommendedNumberOfCalories = new JLabel("Number of calories (Adult) per day: (Recommended: " + user.recommendedNumberOfCaloriesAdult() + ")");
			recommendedNumberOfCalories.setBounds(300, 90, 400, 25);

			userCalorieInput = new JTextField(Integer.toString(numberOfCalories));
			userCalorieInput.setBounds(300, 120, 160, 25);
			
			invalidCalorieLabel = new JLabel("Invalid Input! Please try again.");
			invalidCalorieLabel.setBounds(465, 120, 200, 25);
			invalidCalorieLabel.setForeground(Color.red);
			invalidCalorieLabel.setVisible(false);


			settings.add(firstNameField);
			settings.add(lastNameField);
			settings.add(firstNameLabel);
			settings.add(lastNameLabel);
			settings.add(submitAll);
			settings.add(familySizeLabel);
			settings.add(familySizeDropdown);
			settings.add(invalidInputLabel);
			settings.add(allergiesLabel);
			settings.add(peanuts);
			settings.add(eggs);
			settings.add(milk);
			settings.add(soy);
			settings.add(fish);
			settings.add(shellfish);
			settings.add(nutritionSettings);
			settings.add(avoidAllergens);
			settings.add(recommendedNumberOfCalories);
			settings.add(userCalorieInput);
			settings.add(invalidCalorieLabel);
			
		}
		else {
			for (int i = 0; i < agePrompts.length; i ++) {
				agePrompts[i].setVisible(false);
			}
			for (int i = 0; i < numberOfMembers; i ++) {
				agePrompts[i].setVisible(true);
			}
		}
		settings.validate();
		
	}
	
	/**
	 * Writes user inputted values to memory.
	 */
	private void saveUser() {
		try {
			final String COMMA = ",";
			FileWriter keyWriter = new FileWriter("src/database/userInformation.txt", false);
			keyWriter.write("1" + COMMA);
			keyWriter.write(firstName + COMMA);
			keyWriter.write(lastName + COMMA);
			keyWriter.write(Integer.toString(averageAgeOfFamily)+ COMMA);
			keyWriter.write("Peanuts" + COMMA + booleanToInteger(allergies.get("Peanuts"))+ COMMA);
			keyWriter.write("Eggs" + COMMA + booleanToInteger(allergies.get("Eggs"))+ COMMA);
			keyWriter.write("Milk" + COMMA + booleanToInteger(allergies.get("Milk"))+ COMMA);
			keyWriter.write("Soy" + COMMA + booleanToInteger(allergies.get("Soy"))+ COMMA);
			keyWriter.write("Fish" + COMMA + booleanToInteger(allergies.get("Fish"))+ COMMA);
			keyWriter.write("Shellfish" + COMMA + booleanToInteger(allergies.get("Shellfish"))+ COMMA);
			keyWriter.write("SPACER,");
			for (int i = 0; i < numberOfMembers; i ++) {
				keyWriter.write(Integer.toString(allAges[i]) + COMMA);
			}
			keyWriter.write(Integer.toString(numberOfCalories)+COMMA);
			keyWriter.write(booleanToString(avoidAllergenFood)+COMMA);
			keyWriter.close();

			
			
		}
		catch (Exception e) {
			
		}
		
	}
	/**
	 * Local method.
	 * @param x boolean input.
	 * @return 1 if true, 0 if false.
	 */
	private int booleanToInteger(boolean x) {
	    if (x) {
	    	return 1;
	    }
	    return 0;
	}
	
	/**
	 * Local method.
	 * @param var boolean input.
	 * @return "1" if true, "0" if false.
	 */
	private String booleanToString(boolean var) {
	    if (var) {
	    	return ("1");
	    }
	    return ("0");
	}
	/**
	 * Populates meal grid with data.
	 * @param createNewPlan creates a new plan if true, gets saved plan from database if false.
	 */
	public void displayMealPlan (boolean createNewPlan) {
		if (createNewPlan) {
			try {
				error.setVisible(false);
				LinkedList weeklyPlan = DriverCode.getWeeklyPlan();
				int dailyAverage = 0;
				for (int i = 0; i < 7; i ++) {
					dailyAverage += weeklyPlan.get(i).getCalories();
					mealGrid[0][i].setText("<html><center>" + weeklyPlan.get(i).getBreakfast().getFoodName() + "</center></html>");
					mealGrid[1][i].setText("<html><center>" + weeklyPlan.get(i).getLunch().getFoodName() + "</center></html>");
					mealGrid[2][i].setText("<html><center>" + weeklyPlan.get(i).getDinner().getFoodName() + "</center></html>");
					mealPlanner.updateUI();
				}
				dailyAverage = (int) Math.round(dailyAverage/7.0);
				numberOfCal.setText("Average Number Of Calories Per Day: " + dailyAverage);
			}
			catch (Exception e) {
				error.setVisible(true);
			}

		}
		else {
			LinkedList weeklyPlan = DriverCode.getSavedPlan();
			int dailyAverage = 0;
			for (int i = 0; i < 7; i ++) {
				dailyAverage += weeklyPlan.get(i).getCalories();
				mealGrid[0][i].setText("<html><center>" + weeklyPlan.get(i).getBreakfast().getFoodName() + "</center></html>");
				mealGrid[1][i].setText("<html><center>" + weeklyPlan.get(i).getLunch().getFoodName() + "</center></html>");
				mealGrid[2][i].setText("<html><center>" + weeklyPlan.get(i).getDinner().getFoodName() + "</center></html>");
				mealPlanner.updateUI();
			}
			dailyAverage = (int) Math.round(dailyAverage/7.0);
			numberOfCal.setText("Average Number Of Calories Per Day: " + dailyAverage);
		}
	}
	/**
	 * Used to receive user input from the GUI.
	 * @param e A semantic event which indicates that a component-defined action occurred. 
	 * This high-level event is generated by a component (such as a Button) when the 
	 * component-specific action occurs (such as being pressed).The event is passed to 
	 * every ActionListener object that registered to receive such events using the component's 
	 * addActionListener method. 
	 * @see ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitAll) {
			firstName = firstNameField.getText();
			lastName = lastNameField.getText();
			HashMap<String, Boolean> userAllergies = new HashMap<String, Boolean>();
			userAllergies.put("Peanuts", peanuts.isSelected());
			userAllergies.put("Eggs", eggs.isSelected());
			userAllergies.put("Milk", milk.isSelected());
			userAllergies.put("Soy", soy.isSelected());
			userAllergies.put("Fish", fish.isSelected());
			userAllergies.put("Shellfish", shellfish.isSelected());
			allergies = userAllergies;
			avoidAllergenFood = avoidAllergens.isSelected();
			try {
				int totalAge = 0;
				int [] ageArray = new int[numberOfMembers];
				for (int i = 0; i < numberOfMembers; i ++) {
					if (Integer.parseInt(agePrompts[i].getText()) < 1) {
						System.out.println(0/0);
					}
					totalAge += Integer.parseInt(agePrompts[i].getText());
					ageArray[i] = Integer.parseInt(agePrompts[i].getText());
				}
				invalidInputLabel.setVisible(false);
				averageAgeOfFamily = Math.round(((float)totalAge)/numberOfMembers);
				allAges = ageArray;
			}
			catch (Exception e1) {
				averageAgeOfFamily = 0;
				invalidInputLabel.setVisible(true);
			}
			try {
				if (Integer.parseInt(userCalorieInput.getText()) < 1) {
					System.out.println(0/0);
				}			
				numberOfCalories = Integer.parseInt(userCalorieInput.getText());
				invalidCalorieLabel.setVisible(false);
			}
			catch (Exception e1) {
				numberOfCalories = 0;
				invalidCalorieLabel.setVisible(true);
			}
			
			if (firstName!= null && lastName!= null && averageAgeOfFamily != 0 && allergies!= null && numberOfCalories!= 0) {
				saveUser();
				mainFrame.dispose();
				try {
					DriverCode.updateUser();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		else if (e.getSource() == familySizeDropdown) {
			numberOfMembers = Integer.parseInt((String)familySizeDropdown.getSelectedItem());
			averageAgeOfFamily = 0;
			collectUserInfo(true);	
		}
		else if (e.getSource() == generate) {
			displayMealPlan(true);
		}
		
		for (int i = 0; i < 3; i ++) {
			for (int j = 0; j < 7; j ++) {
				if (e.getSource() == mealGrid[i][j]) {
					HashMap<String, FoodObject> items = new HashMap<String, FoodObject>();
					items = DriverCode.getAllItems();
					if (mealGrid[i][j].getText().length()>0) {
						String buttonText = mealGrid[i][j].getText().split(">")[2];
						buttonText = buttonText.split("<")[0];
						String url = items.get(buttonText).getRecipie();
						try {
						    Desktop.getDesktop().browse(new URL(url).toURI());
						} catch (Exception e1) {
							
						}
					}
				}
			}
		}


		
	}


	
	
}
