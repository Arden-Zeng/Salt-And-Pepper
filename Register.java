import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * 
 * Registration GUI.
 *
 */
public class Register implements ActionListener {
	//These variables are used to create the initial product registration prompt.
	private static JFrame registrationFrame;
	private static JPanel registrationPanel;
	private static JPanel registrationInfoPanel;
	private static JLabel applicationName;
	private static JTextField productKey;
	private static ArrayList<String> keys = new ArrayList<String>(); 
	private static String key;
	private static boolean registered = false;

	private static JButton registerButton;
	private static JLabel registrationStatus;
	
	//This variable is used to lock the user out if they keep entering invalid product keys. The initial value is three.
	private static int numberOfAttemptsRemaining;

	//These variables are used to create the follow up user information collection prompt.
	private static JLabel setUpText;

	private static JTextField firstNameField;
	private static JTextField lastNameField;

	private static JLabel firstNameLabel;
	private static JLabel lastNameLabel;
	private static JLabel familySizeLabel;
	private static JLabel invalidInputLabel;
	
	private static JButton submitFirstName;
	private static JButton submitLastName;
	private static JButton submitFamilyAges;
	private static JButton submitAllergies;

	@SuppressWarnings("rawtypes")
	private static JComboBox familySizeDropdown;
	private static int numberOfMembers;
	private static JTextField [] agePrompts = new JTextField[6];
	
	private static JLabel allergiesLabel;
	private static JCheckBox peanuts;
	private static JCheckBox eggs;
	private static JCheckBox milk;
	private static JCheckBox soy;
	private static JCheckBox fish;
	private static JCheckBox shellfish;
	
	private static JButton finished;

	@SuppressWarnings("unused")
	private static User user;
	private static String firstName;
	private static String lastName;
	private static int [] allAges;
	private static int averageAgeOfFamily;
	private static HashMap<String, Boolean> allergies;
	
	/**
	 * Called only if the user has not yet registered the product.
	 * Initializes a new GUI window asking the user for a product key, then checks 
	 * if the key is valid by looking it up in a database. If it is valid, the key is 
	 * removed from the database file, and the user is taken to an information collection screen.
	 * The input is not case sensitive. If three wrong keys have been entered, 
	 * the application locks permanently (For examiner’s convenience, a reset method is 
	 * provided via a prompt that automatically opens in the console after the program has been locked).
	 */
	public Register() {
		try {
			//Creates two readers to: (a) check how many attempts the user has left to enter a correct key (b) store all valid keys in an array
			BufferedReader attemptReader = new BufferedReader(new FileReader("src/database/attemptsRemaining.txt"));
			BufferedReader keyReader = new BufferedReader(new FileReader("src/database/productKeys.txt"));
			numberOfAttemptsRemaining = Integer.parseInt(attemptReader.readLine());
			attemptReader.close();
			//This if clause is used to detect if the user should be locked out. For examining purposes, a reset prompt is included.
			if (numberOfAttemptsRemaining < 1) {
				System.out.println("Development Tool: You have entered the wrong registration code three times in total. In the real application, this would lock you out forever. This prompt is provided as a convenience to the examiner so that they may reset the application. Please enter: 0 to reset.");
				Scanner scanner = new Scanner(System.in);
				try {
					int choice = scanner.nextInt();
					if (choice == 0) {
						failledToRegister(true);
						System.out.println("Reset. Please restart the application.");
					}
				}
				catch (Exception e) {
					System.out.println("Invalid Input. Exiting.");
				}
				scanner.close();
				System.exit(0);
			}
			String line = keyReader.readLine();
			while (line != null) {
				keys.add(line.toLowerCase());
				line = keyReader.readLine();
			}
			keyReader.close();
		}
		catch(Exception e) {
			
		}
		
		//Selection sort the valid product keys in ascending order, setting up for a binary search later on.
		int min;
		String temp;

		for (int index = 0; index < keys.size()-1; index++)	{
			min = index;
			for (int scan = index+1; scan < keys.size(); scan++) {
				if (keys.get(scan).compareTo(keys.get(min)) < 0) {
					min = scan;
				}
			}
			// Swap the values
			temp = keys.get(min);
			keys.set(min, keys.get(index));
			keys.set(index, temp);
		}
		
		//Creating the initial product key prompt GUI
		registrationFrame = new JFrame("Meal Genie");
		registrationFrame.setSize(750, 500);
		registrationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		registrationPanel = new JPanel();
		registrationFrame.add(registrationPanel);
		registrationFrame.setResizable(false);
		registrationPanel.setLayout(null);
		registrationFrame.setLocationRelativeTo(null);
		
		applicationName = new JLabel("Meal Genie");
		applicationName.setFont(new Font("Calibri", Font.BOLD, 36));
		applicationName.setBounds(287, 100, 175, 50);

		
		productKey = new JTextField("Enter Product Key");
		productKey.setBounds(225, 170, 300, 20);
		
		registerButton = new JButton("Register");
		registerButton.setBackground(Color.LIGHT_GRAY);
		registerButton.addActionListener(this);
		
		registerButton.setBounds(325, 200, 100, 25);
		
		registrationStatus = new JLabel("");
		registrationStatus.setFont(new Font("Calibri", Font.BOLD, 12));
		registrationStatus.setBounds(300, 250, 250, 50);
		
		registrationPanel.add(registerButton);
		registrationPanel.add(applicationName);
		registrationPanel.add(productKey);
		registrationPanel.add(registrationStatus);
		registrationFrame.setVisible(true);
	}
	
	/**
	 * Called when the user's product key is not found in the database of valid product key. 
	 * Decreases the number of attempts that the user has remaining by one, then writes this value 
	 * to memory, preventing restarting the program from resetting the number of attempts that the user 
	 * has to enter a valid product key.
	 * @param examinerReset A value which determines if the number of attempts should be reset 
	 * to its default value (3). It is included as a convenience for the examiner to test the program. 
	 * It should always be false, any calls with this parameter as true should be for debugging/testing purposes 
	 * <b>only</b>.
	 */
	private void failledToRegister(boolean examinerReset) {
		if (!examinerReset) {
			try {
				FileWriter attemptWriter = new FileWriter("src/database/attemptsRemaining.txt", false);
				attemptWriter.write(Integer.toString(numberOfAttemptsRemaining));
				attemptWriter.close();
				
			} catch (IOException e) {
			
			}
		}
		else {
			try {
				FileWriter attemptWriter = new FileWriter("src/database/attemptsRemaining.txt", false);
				attemptWriter.write(Integer.toString(3));
				attemptWriter.close();
				
			} catch (IOException e) {
			
			}
		}
	}
	/**
	 * Recursive binary search, determines if the user's inputted key is found in the database (which has been loaded into the keys ArrayList).
	 * @param keyToFind user's inputted key
	 * @param lowerIndex lower index of the keys ArrayList
	 * @param upperIndex upper index of the keys ArrayList
	 * @return true if found, false otherwise.
	 */
	private boolean keyValid(String keyToFind, int lowerIndex, int upperIndex) {
		int middleIndex = (lowerIndex + upperIndex)/2;
		if (keys.get(middleIndex).equals(keyToFind)) {
			return true;
		}
		else if (lowerIndex > upperIndex) {
			return false;
		}
		else if (keys.get(middleIndex).compareTo(keyToFind) > 0) {
			return keyValid(keyToFind, lowerIndex, middleIndex-1);
		}
		else {
			return keyValid(keyToFind, middleIndex + 1, upperIndex);
		}
	}
	/**
	 * Deletes the key that the user has inputted from the database as to prevent it from being reused.
	 * @param key key to delete.
	 */
	private void updateValidKeys(String key) {
		
		try {
			FileWriter keyWriter = new FileWriter("src/database/productKeys.txt", false);
			for (int i = 0; i < keys.size(); i ++) {
				if (keys.get(i).compareToIgnoreCase(key)!= 0) {
					keyWriter.write(keys.get(i).toUpperCase());
					keyWriter.write(System.getProperty("line.separator"));
				}
			}
			keyWriter.close();
		}
		catch (Exception e) {
			
		}
	}
	
	/**
	 * Populates registrationInfoPanel with graphical elements.
	 * @param createAdditionalAgeFields updates UI with the appropriate number of JTextFields if true.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void collectUserInfo(boolean createAdditionalAgeFields) {
		if (!createAdditionalAgeFields) {	
			registrationFrame.remove(registrationPanel);
			registrationFrame.repaint();
	
			registrationInfoPanel = new JPanel();
			registrationInfoPanel.setLayout(null);
	
			setUpText = new JLabel("Set Up Your Meal Genie: ");
			setUpText.setFont(new Font("Calibri", Font.BOLD, 24));
			setUpText.setBounds(10, 10, 300, 50);
			firstNameLabel = new JLabel("First Name: ");
			firstNameLabel.setBounds(10, 60, 150, 25);
			
			lastNameLabel = new JLabel("Last Name: ");
			lastNameLabel.setBounds(10, 90, 150, 25);
			
			familySizeLabel = new JLabel("Family Size: ");
			familySizeLabel.setBounds(10, 120, 150, 25);
			
			firstNameField = new JTextField("Please enter your first name");
			firstNameField.setBounds(100, 60, 160, 25);
			
			lastNameField = new JTextField("Please enter your last name");
			lastNameField.setBounds(100, 90, 160, 25);

			submitFirstName = new JButton("Submit");
			submitFirstName.setBounds(270, 60, 100, 25);
			submitFirstName.addActionListener(this);
			submitFirstName.setBackground(Color.LIGHT_GRAY);
			
			submitLastName = new JButton("Submit");
			submitLastName.setBounds(270, 90, 100, 25);
			submitLastName.addActionListener(this);
			submitLastName.setBackground(Color.LIGHT_GRAY);

			submitFamilyAges = new JButton("Submit");
			submitFamilyAges.setBounds(10, 180, 100, 25);
			submitFamilyAges.addActionListener(this);
			submitFamilyAges.setBackground(Color.LIGHT_GRAY);
			
			invalidInputLabel = new JLabel("Invalid Input! Please try again.");
			invalidInputLabel.setBounds(120, 180, 200, 25);
			invalidInputLabel.setForeground(Color.red);
			invalidInputLabel.setVisible(false);
			
			String [] familySizeOptions = {"1","2","3","4","5","6"};
			numberOfMembers = 1;
			familySizeDropdown = new JComboBox(familySizeOptions);
			familySizeDropdown.setSelectedIndex(0);
			familySizeDropdown.addActionListener(this);
			familySizeDropdown.setBounds(100, 120, 160, 25);			
			String textFieldFiller = "Enter Age ";
			for (int i = 0; i < 6; i ++) {
				String modifiedFiller = textFieldFiller + (i + 1);
				agePrompts[i] = new JTextField(modifiedFiller);
				agePrompts[i].setBounds(10 + (80)*(i), 150, 75, 25 );
				registrationFrame.add(agePrompts[i]);
				agePrompts[i].setVisible(false);
			}
			agePrompts[0].setVisible(true);
			
			allergiesLabel = new JLabel("Please check all allergies that apply to your family: ");
			allergiesLabel.setBounds(10, 210, 300, 25);
			
			peanuts = new JCheckBox("Peanuts");
			peanuts.setBounds(10, 240, 100, 25);
			
			eggs = new JCheckBox("Eggs");
			eggs.setBounds(10, 270, 100, 25);
			
			milk = new JCheckBox("Milk");
			milk.setBounds(10, 300, 100, 25);
			
			soy = new JCheckBox("Soy");
			soy.setBounds(150, 240, 100, 25);
			
			fish = new JCheckBox("Fish");
			fish.setBounds(150, 270, 100, 25);
			
			shellfish = new JCheckBox("Shellfish");
			shellfish.setBounds(150, 300, 100, 25);
			
			submitAllergies = new JButton("Submit");
			submitAllergies.setBounds(10, 330, 100, 25);
			submitAllergies.addActionListener(this);
			submitAllergies.setBackground(Color.LIGHT_GRAY);
			
			finished = new JButton("Continue");
			finished.setBounds(10, 370, 100, 25);
			finished.addActionListener(this);
			finished.setBackground(Color.LIGHT_GRAY);		
			
			registrationInfoPanel.add(setUpText);
			registrationInfoPanel.add(firstNameField);
			registrationInfoPanel.add(lastNameField);
			registrationInfoPanel.add(firstNameLabel);
			registrationInfoPanel.add(lastNameLabel);
			registrationInfoPanel.add(submitFirstName);
			registrationInfoPanel.add(submitLastName);
			registrationInfoPanel.add(familySizeLabel);
			registrationInfoPanel.add(familySizeDropdown);
			registrationInfoPanel.add(submitFamilyAges);
			registrationInfoPanel.add(invalidInputLabel);
			registrationInfoPanel.add(allergiesLabel);
			registrationInfoPanel.add(peanuts);
			registrationInfoPanel.add(eggs);
			registrationInfoPanel.add(milk);
			registrationInfoPanel.add(soy);
			registrationInfoPanel.add(fish);
			registrationInfoPanel.add(shellfish);
			registrationInfoPanel.add(submitAllergies);
			registrationInfoPanel.add(finished);

			registrationFrame.add(registrationInfoPanel);
		}
		else {
			for (int i = 0; i < agePrompts.length; i ++) {
				agePrompts[i].setVisible(false);
			}
			for (int i = 0; i < numberOfMembers; i ++) {
				agePrompts[i].setVisible(true);
			}
		}
		registrationFrame.validate();
		
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
			
			if (averageAgeOfFamily < 50) {
				keyWriter.write(Integer.toString(2000) + COMMA);
			}
			else {
				keyWriter.write(Integer.toString(1000) + COMMA);
			}
			keyWriter.write(Integer.toString(1));
			keyWriter.close();
			
		}
		catch (Exception e) {
			
		}
		
	}
	/**
	 * Local method.
	 * @param var boolean input.
	 * @return "1" if true, "0" if false.
	 */
	private int booleanToInteger(boolean x) {
	    if (x) {
	    	return 1;
	    }
	    return 0;
	}
	
	@Override
	/**
	 * Used to receive user input from the GUI.
	 * @param e A semantic event which indicates that a component-defined action occurred. 
	 * This high-level event is generated by a component (such as a Button) when the 
	 * component-specific action occurs (such as being pressed).The event is passed to 
	 * every ActionListener object that registered to receive such events using the component's 
	 * addActionListener method. 
	 * @see ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {
		//Logic for when the user submits their product key.
		if (e.getSource() == registerButton) {
			key = productKey.getText();
			if(keyValid(key.toLowerCase(), 0, keys.size()-1)) {
				collectUserInfo(false);
				registered = true;
			}
			else {
				numberOfAttemptsRemaining --;
				failledToRegister(false);
				if (numberOfAttemptsRemaining < 1) {
					System.exit(0);
				}
				registrationStatus.setForeground(Color.red);
				registrationStatus.setText("Failed: " + numberOfAttemptsRemaining + " attempt(s) remaining.");

			}
		}
		if (registered) {
			//Logic for when user submits their information
			if (e.getSource() == submitFirstName) {
				firstName = firstNameField.getText();
			}
			else if (e.getSource() == submitLastName) {
				lastName = lastNameField.getText();
			}			
			else if (e.getSource() == familySizeDropdown) {
				numberOfMembers = Integer.parseInt((String)familySizeDropdown.getSelectedItem());
				collectUserInfo(true);	
			}
			else if (e.getSource() == submitFamilyAges) {
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
			}
			else if (e.getSource() == submitAllergies) {
				HashMap<String, Boolean> userAllergies = new HashMap<String, Boolean>();
				userAllergies.put("Peanuts", peanuts.isSelected());
				userAllergies.put("Eggs", eggs.isSelected());
				userAllergies.put("Milk", milk.isSelected());
				userAllergies.put("Soy", soy.isSelected());
				userAllergies.put("Fish", fish.isSelected());
				userAllergies.put("Shellfish", shellfish.isSelected());
				allergies = userAllergies;
			}
			else if (e.getSource() == finished) {
				updateValidKeys(key);
				saveUser();
				registrationFrame.dispose();
			}
			if (firstName!= null && lastName!= null && averageAgeOfFamily != 0 && allergies!= null) {
				finished.setEnabled(true);
			}
			else {
				finished.setEnabled(false);
			}
		}
	}

	

}
