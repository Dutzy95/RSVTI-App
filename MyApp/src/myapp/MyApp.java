package myapp;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilCalendarModel;

public class MyApp {

	public static void main(String args[]) {
		
//		Path path = Paths.get("C:/Users/" + System.getProperty("user.name") + "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup/");
//		if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
//			try {
//				String fileName = "StartMyApp.bat";
//				File batchFile = new File(path.toString() + "/" + fileName);
//				OutputStream output = new FileOutputStream(batchFile);
//				//TODO: set jar file name as constant in Constants.java
//				String jarFilePath = new File(ClassLoader.getSystemClassLoader().getResource("Test.jar").getPath()).getAbsolutePath();
//				System.out.println(jarFilePath);
//				byte[] bytes = new String("start javaw -jar " + jarFilePath).getBytes();
//				output.write(bytes);
//				output.close();
//			} catch(FileNotFoundException fnfe) {
//				fnfe.printStackTrace();
//			} catch(IOException ioe) {
//				ioe.printStackTrace();
//			}
//		}
		
		JFrame frame = new JFrame("DatePicker");
		frame.setSize(400, 400);
		frame.setLocation(200, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UtilCalendarModel model = new UtilCalendarModel();
		//model.setDate(2014,04,20);
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "Azi");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		p.put("text.sun", "Dum");
		p.put("text.april", "APRILIE");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		// Don't know about the formatter, but there it is...
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		 
		datePicker.setVisible(true);
		frame.setLayout(new FlowLayout());
		JButton button = new JButton("Ceva");
		JLabel label = new JLabel();
		frame.add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Calendar date = (Calendar) datePicker.getModel().getValue();
				if(date != null) {
					label.setText(((Calendar) datePicker.getModel().getValue()).getTime().toString());				
				}
			}
		});
		frame.add(datePicker);
		frame.add(label);
		
		try {
			ImageIcon imageIcon = new ImageIcon(MyApp.class.getResource("/coffee-cup-icon.png"));
			final SystemTray tray = SystemTray.getSystemTray();
			final TrayIcon trayIcon = new TrayIcon(imageIcon.getImage());
			trayIcon.setImageAutoSize(true);
			tray.add(trayIcon);
			
			final PopupMenu popup = new PopupMenu();
			// Create a pop-up menu components
	        MenuItem aboutItem = new MenuItem("About");
	        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
	        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
	        Menu displayMenu = new Menu("Display");
	        MenuItem errorItem = new MenuItem("Error");
	        MenuItem warningItem = new MenuItem("Warning");
	        MenuItem infoItem = new MenuItem("Info");
	        MenuItem noneItem = new MenuItem("None");
	        MenuItem exitItem = new MenuItem("Exit");
	        
	        exitItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
					
				}
			});
	       
	        //Add components to pop-up menu
	        popup.add(aboutItem);
	        popup.addSeparator();
	        popup.add(cb1);
	        popup.add(cb2);
	        popup.addSeparator();
	        popup.add(displayMenu);
	        displayMenu.add(errorItem);
	        displayMenu.add(warningItem);
	        displayMenu.add(infoItem);
	        displayMenu.add(noneItem);
	        popup.add(exitItem);
	        
	        trayIcon.setPopupMenu(popup);
        } catch(AWTException awte) {
        	awte.printStackTrace();
        }
		
		JButton instance = new JButton("Get Instance");
		JLabel instanceLabel = new JLabel();
		frame.add(instance);
		frame.add(instanceLabel);
		
		instance.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				instanceLabel.setText(Calendar.getInstance().getTime().toString());
			}
		});
		
//		final int seconds = 5;
//		while(true) {
//			Calendar calendar = Calendar.getInstance();
//			if(calendar.get(Calendar.SECOND) % seconds == 0) {
//				try {
//					//System.out.println(calendar.getTime());
//					JOptionPane optionPane = new JOptionPane(calendar.getTime(), JOptionPane.INFORMATION_MESSAGE);
//					JDialog dialog = new JDialog();
//					dialog.setVisible(true);
//					dialog.setTitle("Test");
//					dialog.add(optionPane);
//					dialog.setSize(400, 200);
//					dialog.setLocation(500, 500);
//					dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
//					Thread.sleep(seconds * 900);
//					dialog.dispose();
//				} catch(InterruptedException ie) {
//					
//				}
//			}
//		}
		
		frame.setVisible(true);
	}
}
