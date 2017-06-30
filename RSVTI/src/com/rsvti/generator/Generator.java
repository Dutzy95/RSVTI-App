package com.rsvti.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.database.entities.EmployeeDueDateDetails;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.entities.TestQuestion;
import com.rsvti.database.services.DBServices;

public class Generator {
	
	public static void generateWordFile() {
		try {
			
			String jarFilePath = Utils.getJarFilePath();
			
			XWPFDocument document = new XWPFDocument();
			
			String currentDate = new SimpleDateFormat(DBServices.getDatePattern()).format(Calendar.getInstance().getTime());
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
			
//			final int INCH_TO_POINTS = 72;
			//if aspect ratio is needed use img.getHeight() and img.getWidth()
//			BufferedImage img = ImageIO.read(MyApp.class.getResourceAsStream(("/coffee-cup-icon.png")));
			paragraph = document.createParagraph();
			run = paragraph.createRun();
//			run.addPicture(MyApp.class.getResourceAsStream(("/coffee-cup-icon.png")), Document.PICTURE_TYPE_PNG, "/coffee-cup-icon.png", Units.pixelToEMU(Units.pointsToPixel(2 * INCH_TO_POINTS)), Units.pixelToEMU(Units.pointsToPixel(2 * INCH_TO_POINTS)));
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
	
	public static File generateTest(int nrOfQuestions, EmployeeDueDateDetails employeeDetails, boolean generatePdf) {
		File file = null;
		try {
			String jarFilePath = Utils.getJarFilePath();
			file = new File(jarFilePath + "docs\\teste\\" + employeeDetails.getFirmName());
			file.mkdir();
			file = new File(jarFilePath + "docs\\teste\\" + employeeDetails.getFirmName() + "\\" + "Examinare " + employeeDetails.getEmployee().getTitle() 
					+ " - " + employeeDetails.getEmployee().getLastName() + " " + employeeDetails.getEmployee().getFirstName() + ".docx");
			
			FileOutputStream output = new FileOutputStream(file);
			
			
			XWPFDocument document = new XWPFDocument();
			document.createStyles();
			document.createHeaderFooterPolicy();
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();
			
			run.setFontFamily(Constants.GENERATED_FILE_FONT_FAMILY);
			run.setText("LOCUL DESFĂȘURĂRII EXAMINĂRII");
			addTabs(run, 3);
			run.setText("EXAMINATOR");
			run.addBreak();
			run.setBold(true);
			run = paragraph.createRun();
			run.setFontFamily(Constants.GENERATED_FILE_FONT_FAMILY);
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
			run.setFontFamily(Constants.GENERATED_FILE_FONT_FAMILY);
			run.setText("Admis / Respins");
			run.setBold(true);
			run.setFontSize(14);
			
			paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			run = paragraph.createRun();
			run.setFontFamily(Constants.GENERATED_FILE_FONT_FAMILY);
			run.setText("Test");
			run.setFontSize(32);
			run.setBold(true);
			paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			run = paragraph.createRun();
			run.setFontFamily(Constants.GENERATED_FILE_FONT_FAMILY);
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
				run.setFontFamily(Constants.GENERATED_FILE_FONT_FAMILY);
				run.setText((i+1) + ". " + question.getQuestion());
				paragraph = document.createParagraph();
				run = paragraph.createRun();
				run.setFontFamily(Constants.GENERATED_FILE_FONT_FAMILY);
				run.addTab();
				run.setText("a. " + answers.get(0));
				paragraph = document.createParagraph();
				run = paragraph.createRun();
				run.setFontFamily(Constants.GENERATED_FILE_FONT_FAMILY);
				run.addTab();
				run.setText("b. " + answers.get(1));
				paragraph = document.createParagraph();
				run = paragraph.createRun();
				run.setFontFamily(Constants.GENERATED_FILE_FONT_FAMILY);
				run.addTab();
				run.setText("c. " + answers.get(2));
				run.addBreak();
			}
			
			//Generate PDF if needed
			if(generatePdf) {
				//generate PDF
			}
			
			document.write(output);
			
			//Save to backup directory if there is one selected
			if(!DBServices.getBackupPath().equals("")) {
				File backupFile = new File(DBServices.getBackupPath() + "\\procese verbale" + employeeDetails.getFirmName());
				backupFile.mkdir();
				backupFile = new File(DBServices.getBackupPath() + "\\procese verbale\\" + employeeDetails.getFirmName());
				backupFile.mkdir();
				backupFile = new File(DBServices.getBackupPath() + "\\procese verbale" + employeeDetails.getFirmName() + "\\" + "Examinare " + employeeDetails.getEmployee().getTitle() 
						+ " - " + employeeDetails.getEmployee().getLastName() + " " + employeeDetails.getEmployee().getFirstName() + ".docx");
				FileOutputStream backupOutput = new FileOutputStream(backupFile);
				document.write(backupOutput);
			}
			
			document.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return file;
	}
	
	public static File generateExcelTable(Firm firm) {
		File file = null;
		
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Utilaje");
			
			Object[] rigs = firm.getRigs().toArray();
			
			int rowCount = 0;
			
			SimpleDateFormat dateFormat = new SimpleDateFormat(DBServices.getDatePattern());
			
			String jarFilePath = Utils.getJarFilePath();
			file = new File(jarFilePath + "docs\\tabele utilaje\\" + firm.getFirmName() + ".xlsx");
			
			XSSFFont titleFont = workbook.createFont();
			titleFont.setBold(true);
			titleFont.setFontName(Constants.GENERATED_FILE_FONT_FAMILY);
			titleFont.setFontHeight(12);
			
			XSSFFont bodyFont = workbook.createFont();
			bodyFont.setFontName(Constants.GENERATED_FILE_FONT_FAMILY);
			bodyFont.setFontHeight(11);
			
			Row row = sheet.createRow(rowCount++);
			
			Cell cell = row.createCell(0);
			cell.setCellValue("Nume utilaj");
			cell.setCellStyle(getCellStyle(sheet, titleFont, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THIN));
			cell = row.createCell(1);
			cell.setCellValue("Data inspecției");
			cell.setCellStyle(getCellStyle(sheet, titleFont, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THIN, BorderStyle.THIN));
			cell = row.createCell(2);
			cell.setCellValue("Perioada prelungirii");
			cell.setCellStyle(getCellStyle(sheet, titleFont, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THIN, BorderStyle.THIN));
			cell = row.createCell(3);
			cell.setCellValue("Data scadenței");
			cell.setCellStyle(getCellStyle(sheet, titleFont, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THIN, BorderStyle.THICK));
			
			for(int i = 0; i < rigs.length-1; i++) {
				row = sheet.createRow(rowCount++);
				
				cell = row.createCell(0);
				cell.setCellValue(((Rig) rigs[i]).getRigName());
				cell.setCellStyle(getCellStyle(sheet, bodyFont, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THICK));
				cell = row.createCell(1);
				cell.setCellValue(dateFormat.format(((Rig) rigs[i]).getRevisionDate()));
				cell.setCellStyle(getCellStyle(sheet, bodyFont, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THICK));
				cell = row.createCell(2);
				int authorizationExtension = ((Rig) rigs[i]).getAuthorizationExtension();
				cell.setCellValue(authorizationExtension + (authorizationExtension > 1 ? " ani" : " an"));
				cell.setCellStyle(getCellStyle(sheet, bodyFont, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THICK));
				cell = row.createCell(3);
				cell.setCellValue(dateFormat.format(((Rig) rigs[i]).getDueDate()));
				cell.setCellStyle(getCellStyle(sheet, bodyFont, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THICK));
			}
			
			//bottom row of cells
			row = sheet.createRow(rowCount++);
			
			cell = row.createCell(0);
			cell.setCellValue(((Rig) rigs[rigs.length-1]).getRigName());
			cell.setCellStyle(getCellStyle(sheet, bodyFont, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THICK));
			cell = row.createCell(1);
			cell.setCellValue(dateFormat.format(((Rig) rigs[rigs.length-1]).getRevisionDate()));
			cell.setCellStyle(getCellStyle(sheet, bodyFont, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THICK));
			cell = row.createCell(2);
			int authorizationExtension = ((Rig) rigs[rigs.length-1]).getAuthorizationExtension();
			cell.setCellValue(authorizationExtension + (authorizationExtension > 1 ? " ani" : " an"));
			cell.setCellStyle(getCellStyle(sheet, bodyFont, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THICK));
			cell = row.createCell(3);
			cell.setCellValue(dateFormat.format(((Rig) rigs[rigs.length-1]).getDueDate()));
			cell.setCellStyle(getCellStyle(sheet, bodyFont, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THICK));
			
			for(int i = 0; i < 4; i++) {
				sheet.setColumnWidth(i, 20*256);
			}
			
			FileOutputStream output = new FileOutputStream(file);
			workbook.write(output);
			if(!DBServices.getBackupPath().equals("")) {
				File backupFile = new File(DBServices.getBackupPath() + "\\tabele utilaje");
				backupFile.mkdir();
				backupFile = new File(DBServices.getBackupPath() + "\\tabele utilaje\\" + firm.getFirmName() + ".xlsx");
				FileOutputStream backupOutput = new FileOutputStream(backupFile);
				workbook.write(backupOutput);
			}
			
			workbook.close();
			
		} catch(Exception e) { 
			e.printStackTrace();
		}
		return file;
	}
	
	private static CellStyle getCellStyle(XSSFSheet sheet, XSSFFont font, BorderStyle top, BorderStyle bottom, BorderStyle left, BorderStyle right) {
		CellStyle style = sheet.getWorkbook().createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFont(font);
		style.setBorderBottom(bottom);
		style.setBorderTop(top);
		style.setBorderLeft(left);
		style.setBorderRight(right);
		return style;
	}
}
