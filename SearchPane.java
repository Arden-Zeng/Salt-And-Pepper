import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Template for displaying food search results.
 *
 */
@SuppressWarnings("serial")
public class SearchPane extends JPanel implements ActionListener{
	
	private static ArrayList<String> allNames;
	@SuppressWarnings("unused")
	private static boolean firstRun = true;
	private static JButton [] allButtons;
	private static HashMap<String, FoodObject> items;
	
	/**
	 * Displays all food objects that contain key.
	 * @param key used to filter food objects that contain it.
	 */
	@SuppressWarnings("unchecked")
	SearchPane(String key) {
		items = DriverCode.getAllItems();
        allNames = DriverCode.getAllNames();
		ArrayList<String> searchResults = new ArrayList<String>();
		for (int i = 0; i < allNames.size(); i ++) {
			if(allNames.get(i).toLowerCase().contains(key.toLowerCase())) {
				searchResults.add(allNames.get(i));
			}
		}
		
        if (key.equals("")) {
        	searchResults = (ArrayList<String>) allNames.clone();
        }
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
		setLayout(new BoxLayout(this, SwingConstants.VERTICAL));
		allButtons = new JButton[searchResults.size()];
		for(int i = 0; i < searchResults.size(); i++) {
            box.add(Box.createVerticalStrut(10));
        	JButton buttonFormat = new JButton(searchResults.get(i));
        	buttonFormat.setBackground(Color.LIGHT_GRAY);
        	buttonFormat.setAlignmentX(Component.CENTER_ALIGNMENT);
        	buttonFormat.addActionListener(this);
            box.add(buttonFormat);
            allButtons[i] = buttonFormat;
        }
        box.add(Box.createVerticalStrut(10));
        add(box);
        firstRun = false;

	}

	/**
	 * Handles back end logic when a recipie's button is clicked. Determines which recipe's buttons was clicked and opens the correct recipe in the default browser.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < allButtons.length; i ++) {
			if (e.getSource() == allButtons[i]) {
				String url = items.get(allButtons[i].getText()).getRecipie();
				try {
				    Desktop.getDesktop().browse(new URL(url).toURI());
				} catch (Exception e1) {
					
				}
			}
		}
	}
	
	
}
