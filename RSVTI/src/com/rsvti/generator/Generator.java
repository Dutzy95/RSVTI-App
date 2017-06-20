package com.rsvti.generator;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;

import com.rsvti.database.entities.EmployeeDueDateDetails;
import com.rsvti.database.entities.TestQuestion;
import com.rsvti.database.services.DBServices;
import com.rsvti.main.Constants;
import com.rsvti.main.Utils;

public class Generator {
	
	public static void generateWordFile() {
		try {
			
			String jarFilePath = Utils.getJarFilePath();
			
			XWPFDocument document = new XWPFDocument();
			
			String currentDate = new SimpleDateFormat(Constants.DATE_FORMAT).format(Calendar.getInstance().getTime());
			File file = new File(jarFilePath + "docs\\" + currentDate);
			file.mkdir();
			file = new File(jarFilePath + "docs\\" + currentDate + "\\" + "MyWordDocument.docx");
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
	//		BufferedImage img = ImageIO.read(MyApp.class.getResourceAsStream(("/coffee-cup-icon.png")));
			paragraph = document.createParagraph();
			run = paragraph.createRun();
	//		run.addPicture(MyApp.class.getResourceAsStream(("/coffee-cup-icon.png")), Document.PICTURE_TYPE_PNG, "/coffee-cup-icon.png", Units.pixelToEMU(Units.pointsToPixel(2 * INCH_TO_POINTS)), Units.pixelToEMU(Units.pointsToPixel(2 * INCH_TO_POINTS)));
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			
			document.write(output);
			
//			Desktop.getDesktop().open(file);
			output.close();
			document.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void addTabs(XWPFRun run, int nrOfTabs) {
		for(int i = 0; i < nrOfTabs; i++) {
			run.addTab();
		}
	}
	
	public static File generateTest(int nrOfQuestions, EmployeeDueDateDetails employeeDetails) {
		File file = null;
		try {
			String jarFilePath = Utils.getJarFilePath();
			file = new File(jarFilePath + "tests\\" + employeeDetails.getFirmName());
			file.mkdir();
			file = new File(jarFilePath + "tests\\" + employeeDetails.getFirmName() + "\\" + "Examinare " + employeeDetails.getEmployee().getTitle() 
					+ " - " + employeeDetails.getEmployee().getLastName() + " " + employeeDetails.getEmployee().getFirstName() + ".docx");
			FileOutputStream output = new FileOutputStream(file);
			
			XWPFDocument document = new XWPFDocument();
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();
			
			run.setText("LOCUL DESFĂȘURĂRII EXAMINĂRII");
			addTabs(run, 4);
			run.setText("EXAMINATOR");
			run.addBreak();
			run.setBold(true);
			run = paragraph.createRun();
			run.setText("Firma: " + employeeDetails.getFirmName());
			addTabs(run, 6);
			run.setText("Nume: Bogdan");
			run.addBreak();
			run.setText("Adresă: " + employeeDetails.getFirmAddress());
			addTabs(run, 6);
			run.setText("Prenume: Radu-Victor");
			run.addBreak();
			run.setText("Nume: " + employeeDetails.getEmployee().getLastName());
			addTabs(run, 6);
			run.setText("Semnătură: __________________");
			run.addBreak();
			run.setText("Prenume: " + employeeDetails.getEmployee().getFirstName());
			run.addBreak();
			run.setText("Data: ________________________");
			run.addBreak();
			run.setText("Semnătură: ___________________");
			addTabs(run, 5);
			run = paragraph.createRun();
			run.setText("Admis / Respins");
			run.setBold(true);
			run.setFontSize(14);
			run = paragraph.createRun();
			
			paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			run = paragraph.createRun();
			run.setText("Test");
			run.setFontSize(32);
			run.setBold(true);
			paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			run = paragraph.createRun();
			run.setText("Examinare anuala " + employeeDetails.getEmployee().getTitle());
			run.setFontSize(20);
			paragraph = document.createParagraph();
			
			List<TestQuestion> questions = DBServices.getTestQuestionsOfType(employeeDetails.getEmployee().getTitle());
			Collections.shuffle(questions);
			
			for(int i = 0; i < nrOfQuestions; i++) {
				TestQuestion question = questions.get(i);
				List<String> answers = question.getAnswers();
				Collections.shuffle(answers);
				
				paragraph = document.createParagraph();
				paragraph.setAlignment(ParagraphAlignment.BOTH);
				run = paragraph.createRun();
				run.setText((i+1) + ". " + question.getQuestion());
				paragraph = document.createParagraph();
				run = paragraph.createRun();
				run.addTab();
				run.setText("a. " + answers.get(0));
				paragraph = document.createParagraph();
				run = paragraph.createRun();
				run.addTab();
				run.setText("b. " + answers.get(1));
				paragraph = document.createParagraph();
				run = paragraph.createRun();
				run.addTab();
				run.setText("c. " + answers.get(2));
				run.addBreak();
			}
			
			document.write(output);
			document.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return file;
	}
}
