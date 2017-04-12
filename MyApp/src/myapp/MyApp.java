package myapp;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.dom4j.util.ProxyDocumentFactory;
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
		
//		JFrame frame = new JFrame("DatePicker");
//		frame.setSize(400, 400);
//		frame.setLocation(200, 200);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		UtilCalendarModel model = new UtilCalendarModel();
//		//model.setDate(2014,04,20);
//		// Need this...
//		Properties p = new Properties();
//		p.put("text.today", "Azi");
//		p.put("text.month", "Month");
//		p.put("text.year", "Year");
//		p.put("text.sun", "Dum");
//		p.put("text.april", "APRILIE");
//		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
//		// Don't know about the formatter, but there it is...
//		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
//		 
//		datePicker.setVisible(true);
//		frame.setLayout(new FlowLayout());
//		JButton button = new JButton("Ceva");
//		JLabel label = new JLabel();
//		frame.add(button);
//		button.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Calendar date = (Calendar) datePicker.getModel().getValue();
//				if(date != null) {
//					label.setText(((Calendar) datePicker.getModel().getValue()).getTime().toString());				
//				}
//			}
//		});
//		frame.add(datePicker);
//		frame.add(label);
//		
//		try {
//			ImageIcon imageIcon = new ImageIcon(MyApp.class.getResource("/coffee-cup-icon.png"));
//			final SystemTray tray = SystemTray.getSystemTray();
//			final TrayIcon trayIcon = new TrayIcon(imageIcon.getImage());
//			trayIcon.setImageAutoSize(true);
//			tray.add(trayIcon);
//			
//			final PopupMenu popup = new PopupMenu();
//			// Create a pop-up menu components
//	        MenuItem aboutItem = new MenuItem("About");
//	        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
//	        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
//	        Menu displayMenu = new Menu("Display");
//	        MenuItem errorItem = new MenuItem("Error");
//	        MenuItem warningItem = new MenuItem("Warning");
//	        MenuItem infoItem = new MenuItem("Info");
//	        MenuItem noneItem = new MenuItem("None");
//	        MenuItem exitItem = new MenuItem("Exit");
//	        
//	        exitItem.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					System.exit(0);
//					
//				}
//			});
//	       
//	        //Add components to pop-up menu
//	        popup.add(aboutItem);
//	        popup.addSeparator();
//	        popup.add(cb1);
//	        popup.add(cb2);
//	        popup.addSeparator();
//	        popup.add(displayMenu);
//	        displayMenu.add(errorItem);
//	        displayMenu.add(warningItem);
//	        displayMenu.add(infoItem);
//	        displayMenu.add(noneItem);
//	        popup.add(exitItem);
//	        
//	        trayIcon.setPopupMenu(popup);
//        } catch(AWTException awte) {
//        	awte.printStackTrace();
//        }
//		
//		JButton instance = new JButton("Get Instance");
//		JLabel instanceLabel = new JLabel();
//		frame.add(instance);
//		frame.add(instanceLabel);
//		
//		instance.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				instanceLabel.setText(Calendar.getInstance().getTime().toString());
//			}
//		});
		
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
		
//		frame.setVisible(true);
		
		try {
			XWPFDocument document = new XWPFDocument();
			
			String path = "D:/Tata/MyWordDocument.docx";
			File file = new File(path);
			FileOutputStream output = new FileOutputStream(file);
			
			String string = "Lorem ipsum dolor sit amet, ex nemore menandri electram has, dicat feugait liberavisse pri an. Facilis omittam postulant eu sed, ad vis harum eloquentiam. Ius ea vero timeam qualisque, malis oportere disputationi cu mel. Eam graecis maluisset at, eum timeam labitur menandri ad. Wisi consulatu cum eu, qui vidit delicata an, nec cu mollis voluptaria. Ex pri tale consectetuer, elit tation has id."
					+ "Eu euismod bonorum mei, mei debitis pertinacia repudiandae no, at vim quod adversarium. Falli ignota fabellas sea et, an adhuc dicant laboramus sed. Ut vis vide putent, per cu decore nonumy, sed eripuit suscipit scripserit ad. No pri sonet exerci, nec adipisci posidonium ut. Partem diceret theophrastus pri ea, ex quis cibo sit. Nostrud delenit iracundia ut per, duo ornatus lobortis et, ea odio graecis ius."
					+ "Id diceret fastidii convenire mel. Eu vim modo propriae sadipscing, ad eum assum nostro. Est tollit platonem sapientem te, ius ex tollit sensibus. Quem volutpat id vix, has modus referrentur ei. Nec an dicta dolore copiosae."
					+ "Et veri omnis mel, eu accommodare complectitur ius, solum prompta iuvaret quo eu. Eum modus audire tractatos ex, ius et consul pertinax. Veritus expetenda duo id. Saperet abhorreant pro an. Has regione ocurreret scriptorem an."
					+ "Usu perpetua suavitate at, sit ea nostrud postulant. Ea esse error sed, nec doctus vituperatoribus in. Ad sententiae theophrastus nam. An nec homero fastidii splendide, an hinc democritum vix."
					+ "Ex augue impedit dolorem qui, malis falli mazim in mel. At graecis percipitur dissentiet eam. Et sonet viris tibique nam. Inani possit habemus an pro."
					+ "Ad sit porro corrumpit, aliquip intellegat voluptatibus qui cu. Id semper cetero sea, te eam epicuri lobortis. Id amet zril eum, putent inimicus has cu, mel ad dico explicari. Duo at tibique definiebas, mel ne verterem insolens periculis, nec no putent delectus gubergren. Qui te nullam expetenda neglegentur."
					+ "Nec ea admodum elaboraret, harum iriure maiorum eam ne. Clita docendi atomorum ei has, et congue eirmod vidisse nec. Usu te ipsum saepe dicant, augue mucius nam et, ut sit nullam utroque nostrum. Zril sadipscing est eu, at usu erat copiosae, veri assum mel id."
					+ "Libris integre mentitum eam ex. Probo prima quo in, posse nulla nec ad. Pri at facilis molestiae disputationi, quot omnium rationibus eu cum. Odio nullam cu eam. His ad tempor corrumpit, elit disputando vix cu, sit eu voluptaria definitionem.";
			XWPFParagraph paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun run = paragraph.createRun();
			run.setText("My first generated .docx file");
			run.setBold(true);
			run.setFontSize(25);
			run.setFontFamily("Garamond");
			run.addCarriageReturn();
			run.addCarriageReturn();
			
			paragraph = document.createParagraph();
			run = paragraph.createRun();
			run.setText(string);
			run.setFontSize(15);
			run.setFontFamily("Garamond");
			paragraph.setAlignment(ParagraphAlignment.BOTH);
			
			final int INCH_TO_POINTS = 72;
			//if aspect ratio is needed use img.getHeight() and img.getWidth()
//			BufferedImage img = ImageIO.read(MyApp.class.getResourceAsStream(("/coffee-cup-icon.png")));
			paragraph = document.createParagraph();
			run = paragraph.createRun();
			run.addPicture(MyApp.class.getResourceAsStream(("/coffee-cup-icon.png")), Document.PICTURE_TYPE_PNG, "/coffee-cup-icon.png", Units.pixelToEMU(Units.pointsToPixel(2 * INCH_TO_POINTS)), Units.pixelToEMU(Units.pointsToPixel(2 * INCH_TO_POINTS)));
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			
			document.write(output);
			
			Desktop.getDesktop().open(file);
			output.close();
			document.close();
					
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
