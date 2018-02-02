package com.rsvti.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
//import org.docx4j.Docx4J;
//import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.database.entities.EmployeeWithDetails;
import com.rsvti.database.entities.EmployeeTestResults;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.LoggedTest;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.entities.TestQuestion;
import com.rsvti.database.services.DBServices;

import javafx.scene.control.CheckBox;

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
	
	private static XWPFRun createRunSize14TimesNR(XWPFParagraph paragraph, String text, boolean bold) {
		XWPFRun run = paragraph.createRun();
		run.setText(text);
		run.setBold(bold);
		run.setFontSize(14);
		run.setFontFamily(Constants.GENERATED_FILE_FONT_FAMILY);
		return run;
	}
	
	private static XWPFRun createRunTimesNR(XWPFParagraph paragraph, String text, boolean bold, int fontSize) {
		XWPFRun run = paragraph.createRun();
		run.setText(text);
		run.setBold(bold);
		run.setFontSize(fontSize);
		run.setFontFamily(Constants.GENERATED_FILE_FONT_FAMILY);
		return run;
	}
	
	public static XWPFParagraph createParagraphForCertificate(XWPFDocument document, ParagraphAlignment alignment) {
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(alignment);
		paragraph.setSpacingAfter(0);
		paragraph.setSpacingBefore(0);
		paragraph.setSpacingLineRule(LineSpacingRule.EXACT);
		return paragraph;
	}
	
	public static void generateOneCertificate(XWPFDocument document, EmployeeWithDetails employee, int registrationNumber, Date registrationDate, 
			Date issueDate, boolean choice1, boolean choice2, boolean choice3, boolean choice4, String rsvti, String executiveName) {
		SimpleDateFormat format = new SimpleDateFormat(Constants.GENERATED_FILE_DATE_FORMAT);
		XWPFParagraph paragraph = createParagraphForCertificate(document, ParagraphAlignment.BOTH);
		XWPFRun run;
		createRunSize14TimesNR(paragraph, "   " + employee.getFirmName(), false);
		document.createParagraph();	//empty line
		paragraph = createParagraphForCertificate(document, ParagraphAlignment.CENTER);
		createRunSize14TimesNR(paragraph, "ADEVERINȚĂ", true);
		paragraph = createParagraphForCertificate(document, ParagraphAlignment.CENTER);
		createRunSize14TimesNR(paragraph, "Nr ", false);
		createRunSize14TimesNR(paragraph, registrationNumber + "", false);
		createRunSize14TimesNR(paragraph, " / ", false);
		createRunSize14TimesNR(paragraph, format.format(registrationDate), false);
		document.createParagraph();	//empty line
		paragraph = createParagraphForCertificate(document, ParagraphAlignment.BOTH);
		run = createRunSize14TimesNR(paragraph, "", false);
		run.addTab();	//indent for paragraph beginning
		createRunSize14TimesNR(paragraph, "Prin prezenta se adeverește că Dl/D-na ", false);
		createRunSize14TimesNR(paragraph, employee.getEmployee().getLastName() + " " + employee.getEmployee().getFirstName(), true);
		createRunSize14TimesNR(paragraph, " născut(ă) la data de ", false);
		createRunSize14TimesNR(paragraph, format.format(employee.getEmployee().getBirthDate()), true);
		createRunSize14TimesNR(paragraph, " în localitatea ", false);
		createRunSize14TimesNR(paragraph, employee.getEmployee().getBirthCity(), true);
		createRunSize14TimesNR(paragraph, ", domiciliat(ă) în ", false);
		createRunSize14TimesNR(paragraph, employee.getEmployee().getHomeAddress(), true);
		createRunSize14TimesNR(paragraph, ", județ ", false);
		createRunSize14TimesNR(paragraph, employee.getEmployee().getHomeRegion(), true);
		createRunSize14TimesNR(paragraph, ", CI. seria ", false);
		createRunSize14TimesNR(paragraph, employee.getEmployee().getIdCode(), true);
		createRunSize14TimesNR(paragraph, ", nr. ", false);
		createRunSize14TimesNR(paragraph, employee.getEmployee().getIdNumber(), true);
		createRunSize14TimesNR(paragraph, ", CNP. ", false);
		createRunSize14TimesNR(paragraph, employee.getEmployee().getPersonalIdentificationNumber(), true);
		createRunSize14TimesNR(paragraph, ", a fost instruit ca ", false);
		String employeeTitle = employee.getEmployee().getTitle();
		createRunSize14TimesNR(paragraph, employeeTitle.toUpperCase(), false);
		int cnt = 0;
		if (employeeTitle.equals("manevrant")) {
			createRunSize14TimesNR(paragraph, " să deservească mecanismele de ridicat: ", false);
			if(choice1) {
				createRunSize14TimesNR(paragraph, "transpalet hidraulic", false);
				cnt++;
			}
			if(choice2) {
				createRunSize14TimesNR(paragraph, cnt == 1 ? ", stivuitor manual" : " stivuitor manual", false);
				cnt++;
			}
			if(choice3) {
				createRunSize14TimesNR(paragraph, cnt >= 1 ? ", palan manual ( Q" : " palan manual ( Q", false);
				run = createRunSize14TimesNR(paragraph, "max", false);
				run.setSubscript(VerticalAlign.SUBSCRIPT);
				createRunSize14TimesNR(paragraph, " <= 1t )", false);
				cnt++;
			}
			if(choice4) {
				createRunSize14TimesNR(paragraph, cnt >=1 ? ",  electropalan ( Q" : " electropalan ( Q", false);
				run = createRunSize14TimesNR(paragraph, "max", false);
				run.setSubscript(VerticalAlign.SUBSCRIPT);
				createRunSize14TimesNR(paragraph, " <= 1t )", false);
			}
			createRunSize14TimesNR(paragraph, ".", false);
		} else {
			createRunSize14TimesNR(paragraph, ", să deservească mașinile de ridicat stipulate în procesul verbal.", false);
		}
		emptyLines(document, 2);
		paragraph = createParagraphForCertificate(document, ParagraphAlignment.LEFT);
		run = createRunSize14TimesNR(paragraph, "", false);
		createRunSize14TimesNR(paragraph, getStringOfSpaces(16) + "DIRECTOR" + getStringOfSpaces(17), true);
		run = createRunSize14TimesNR(paragraph, "", false);
		createRunSize14TimesNR(paragraph, getStringOfSpaces(18) + "RSVTI", true);
		paragraph = createParagraphForCertificate(document, ParagraphAlignment.LEFT);
		run = createRunSize14TimesNR(paragraph, "", false);
		createRunSize14TimesNR(paragraph, getStringOfSpaces((82/2-executiveName.length())/2) + executiveName + getStringOfSpaces((82/2-executiveName.length())/2), false);
		run = createRunSize14TimesNR(paragraph, "", false);
		createRunSize14TimesNR(paragraph, getStringOfSpaces((82/2-rsvti.length())/2) + rsvti + getStringOfSpaces((82/2-rsvti.length())/2), false);
		emptyLines(document, 2);
		paragraph = createParagraphForCertificate(document, ParagraphAlignment.LEFT);
		createRunSize14TimesNR(paragraph, "Eliberată la data de " + format.format(issueDate), false);
		paragraph = createParagraphForCertificate(document, ParagraphAlignment.LEFT);
		createRunSize14TimesNR(paragraph, "Valabil un an de la data emiterii.", false);
	}
	
	public static File generateCertificate(EmployeeWithDetails employee, int registrationNumber, Date registrationDate, Date issueDate, 
			boolean choice1, boolean choice2, boolean choice3, boolean choice4, String rsvti, String executiveName) {
		File file = null;
		try {
			String jarFilePath = Utils.getJarFilePath();
			
			XWPFDocument document = new XWPFDocument();
			CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
		    CTPageMar pageMar = sectPr.addNewPgMar();
		    pageMar.setLeft(BigInteger.valueOf(720L));
		    pageMar.setTop(BigInteger.valueOf(500L));
		    pageMar.setRight(BigInteger.valueOf(720L));
		    pageMar.setBottom(BigInteger.valueOf(500L));
			
			String currentDate = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT).format(Calendar.getInstance().getTime());
			file = new File(jarFilePath + "docs\\adeverințe\\" + currentDate);
			file.mkdir();
			file = new File(jarFilePath + "docs\\adeverințe\\" + currentDate + "\\Adeverință - " + employee.getEmployee().getLastName() + " " + 
					employee.getEmployee().getFirstName() + ".docx");
			FileOutputStream output = new FileOutputStream(file);
			
			generateOneCertificate(document, employee, registrationNumber, registrationDate, issueDate, choice1, choice2, choice3, choice4, rsvti, executiveName);
			emptyLines(document, 2);
			generateOneCertificate(document, employee, registrationNumber, registrationDate, issueDate, choice1, choice2, choice3, choice4, rsvti, executiveName);
			
			document.write(output);
			output.close();
			
			if(!DBServices.getBackupPath().isEmpty()) {
				File backupFile = new File(DBServices.getBackupPath() + "\\adeverințe");
				backupFile.mkdir();
				backupFile = new File(DBServices.getBackupPath() + "\\adeverințe\\" + currentDate);
				backupFile.mkdir();
				backupFile = new File(DBServices.getBackupPath() + "\\adeverințe\\" + currentDate + "\\Adeverință - " + employee.getEmployee().getLastName() + " " + 
					employee.getEmployee().getFirstName() + ".docx");
				FileOutputStream backupOutput = new FileOutputStream(backupFile);
				document.write(backupOutput);
			}
			
			document.close();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
		return file;
	}
	
	private static void addTabs(XWPFRun run, int nrOfTabs) {
		for(int i = 0; i < nrOfTabs; i++) {
			run.addTab();
		}
	}
	
	private static void emptyLines(XWPFDocument document, int nrOfLines) {
		for(int i = 0; i < nrOfLines; i++) {
			document.createParagraph();
		}
	}
	
	private static String getStringOfSpaces(int nrOfSpaces) {
		String tmp = "";
		for(int i = 0; i < nrOfSpaces*1.5; i++) {
			tmp += " ";
		}
		return tmp;
	}
	
	private static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if ( rowIndex == fromRow ) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
	
	private static void mergeCellsHorizontally(XWPFTable table, int row, int fromCol, int toCol) {
        for (int colIndex = fromCol; colIndex <= toCol; colIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(colIndex);
            if ( colIndex == fromCol ) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
	
	private static void widenTableCells(XWPFTable table, int col, int size) {
		for (int i = 0; i < table.getNumberOfRows(); i++) {
			XWPFTableCell cell = table.getRow(i).getCell(col);
			cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(size));
		}
	}
	
	private static void setCellParagraph(XWPFParagraph paragraph) {
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		paragraph.setSpacingAfter(0);
		paragraph.setSpacingBefore(0);
		paragraph.setSpacingLineRule(LineSpacingRule.EXACT);
	}
	
	public static File generateTestResultsReport(Firm firm, String registrationNumber, Date registrationDate, String employeeTitle, 
			List<EmployeeTestResults> employees, CheckBox choice1, CheckBox choice2, CheckBox choice3, CheckBox choice4, String rsvti, String executiveName) {
		File file = null;
		try {
			String jarFilePath = Utils.getJarFilePath();
			final int tableMaxLength = 13;
			
			XWPFDocument document = new XWPFDocument();
			CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
		    CTPageMar pageMar = sectPr.addNewPgMar();
		    pageMar.setLeft(BigInteger.valueOf(720L));
		    pageMar.setTop(BigInteger.valueOf(500L));
		    pageMar.setRight(BigInteger.valueOf(720L));
		    pageMar.setBottom(BigInteger.valueOf(500L));
			
			String currentDate = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT).format(Calendar.getInstance().getTime());
			file = new File(jarFilePath + "docs\\procese verbale\\rezultate examinare\\" + currentDate);
			file.mkdir();
			file = new File(jarFilePath + "docs\\procese verbale\\rezultate examinare\\" + currentDate + "\\" + firm.getFirmName() + ".docx");
			FileOutputStream output = new FileOutputStream(file);
			
			SimpleDateFormat format = new SimpleDateFormat(Constants.GENERATED_FILE_DATE_FORMAT);
			
			XWPFRun run;
			XWPFParagraph paragraph = createParagraphForCertificate(document, ParagraphAlignment.LEFT);
			createRunSize14TimesNR(paragraph, firm.getFirmName(), true);
			emptyLines(document, 5);
			paragraph = createParagraphForCertificate(document, ParagraphAlignment.CENTER);
			createRunSize14TimesNR(paragraph, "PROCES VERBAL NR. " + registrationNumber + " / " + format.format(registrationDate), true);
			emptyLines(document, 1);
			paragraph = createParagraphForCertificate(document, ParagraphAlignment.CENTER);
			createRunSize14TimesNR(paragraph, "CU REZULTATELE OBȚINUTE LA EXAMINAREA PERIODICĂ A PERSONALULUI CA " + employeeTitle.toUpperCase() + ", PENTRU URMĂTORII CANDIDAȚI", false);
			
			XWPFTable table = document.createTable();
			CTTblPr tblPr = table.getCTTbl().getTblPr();
		    CTJc jc = (tblPr.isSetJc() ? tblPr.getJc() : tblPr.addNewJc());
		    jc.setVal(STJc.CENTER);
		    
			XWPFTableRow tableTitle = table.getRow(0);
			tableTitle.getCell(0).removeParagraph(0);
			tableTitle.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);
		    paragraph = tableTitle.getCell(0).addParagraph();
		    setCellParagraph(paragraph);
		    createRunTimesNR(paragraph, "Nr. Crt", true, 10);
		    tableTitle.addNewTableCell().removeParagraph(0);
		    tableTitle.getCell(1).setVerticalAlignment(XWPFVertAlign.CENTER);
		    paragraph = tableTitle.getCell(1).addParagraph();
		    setCellParagraph(paragraph);
		    createRunTimesNR(paragraph, "Numele și prenumele", true, 10);
		    tableTitle.addNewTableCell().removeParagraph(0);
		    tableTitle.getCell(2).setVerticalAlignment(XWPFVertAlign.CENTER);
		    paragraph = tableTitle.getCell(2).addParagraph(); 
		    setCellParagraph(paragraph);
		    createRunTimesNR(paragraph, "CNP", true, 10);
		    tableTitle.addNewTableCell().removeParagraph(0);
		    tableTitle.getCell(3).setVerticalAlignment(XWPFVertAlign.CENTER);
		    paragraph = tableTitle.getCell(3).addParagraph(); 
		    setCellParagraph(paragraph);
		    createRunTimesNR(paragraph, "Nr adeverință", true, 10);
		    tableTitle.addNewTableCell().removeParagraph(0);
		    tableTitle.getCell(4).setVerticalAlignment(XWPFVertAlign.CENTER);
		    paragraph = tableTitle.getCell(4).addParagraph();
		    setCellParagraph(paragraph);
		    createRunTimesNR(paragraph, "Tip instalație", true, 10);
		    tableTitle.addNewTableCell().removeParagraph(0);
		    tableTitle.getCell(5).setVerticalAlignment(XWPFVertAlign.CENTER);
		    paragraph = tableTitle.getCell(5).addParagraph();
		    setCellParagraph(paragraph);
		    createRunTimesNR(paragraph, "Rezultatul Examinării", true, 10);
		    tableTitle.addNewTableCell();
		    
		    XWPFTableRow dataRow = table.createRow();
		    dataRow.getCell(5).removeParagraph(0);
		    dataRow.getCell(5).setVerticalAlignment(XWPFVertAlign.CENTER);
		    paragraph = dataRow.getCell(5).addParagraph();
		    setCellParagraph(paragraph);
		    createRunTimesNR(paragraph, "Teoretic", true, 10);
		    dataRow.getCell(6).removeParagraph(0);
		    dataRow.getCell(6).setVerticalAlignment(XWPFVertAlign.CENTER);
		    paragraph = dataRow.getCell(6).addParagraph();
		    setCellParagraph(paragraph);
		    createRunTimesNR(paragraph, "Practic", true, 10);
		    
		    
			for(int i = 1; i < employees.size() + 1; i++) {
				dataRow = table.createRow();
				dataRow.getCell(0).removeParagraph(0);
				dataRow.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);
			    paragraph = dataRow.getCell(0).addParagraph();
			    setCellParagraph(paragraph);
			    createRunTimesNR(paragraph, i + "", false, 10);
			    dataRow.getCell(1).removeParagraph(0);
			    dataRow.getCell(1).setVerticalAlignment(XWPFVertAlign.CENTER);
			    paragraph = dataRow.getCell(1).addParagraph();
			    setCellParagraph(paragraph);
			    createRunTimesNR(paragraph, employees.get(i-1).getEmployeeName(), false, 10);
			    dataRow.getCell(2).removeParagraph(0);
			    dataRow.getCell(2).setVerticalAlignment(XWPFVertAlign.CENTER);
			    paragraph = dataRow.getCell(2).addParagraph();
			    setCellParagraph(paragraph);
			    createRunTimesNR(paragraph, employees.get(i-1).getPersonalIdentificationNumber(), false, 10);
			    dataRow.getCell(3).removeParagraph(0);
			    dataRow.getCell(3).setVerticalAlignment(XWPFVertAlign.CENTER);
			    paragraph = dataRow.getCell(3).addParagraph();
			    setCellParagraph(paragraph);
			    createRunTimesNR(paragraph, i + "", false, 10);
			    dataRow.getCell(5).removeParagraph(0);
			    dataRow.getCell(5).setVerticalAlignment(XWPFVertAlign.CENTER);
			    paragraph = dataRow.getCell(5).addParagraph();
			    setCellParagraph(paragraph);
			    createRunTimesNR(paragraph, employees.get(i-1).getTheoryResult(), false, 10);
			    dataRow.getCell(6).removeParagraph(0);
			    dataRow.getCell(6).setVerticalAlignment(XWPFVertAlign.CENTER);
			    paragraph = dataRow.getCell(6).addParagraph();
			    setCellParagraph(paragraph);
			    createRunTimesNR(paragraph, employees.get(i-1).getPractiseResult(), false, 10);
			}
			
			for(int i = employees.size() + 1; i <= tableMaxLength; i++) {
				dataRow = table.createRow();
				dataRow.getCell(0).removeParagraph(0);
				dataRow.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);
				paragraph = dataRow.getCell(0).addParagraph();
				setCellParagraph(paragraph);
				createRunTimesNR(paragraph, i + "", false, 10);
				dataRow.getCell(3).removeParagraph(0);
				dataRow.getCell(3).setVerticalAlignment(XWPFVertAlign.CENTER);
				paragraph = dataRow.getCell(3).addParagraph();
				setCellParagraph(paragraph);
				createRunTimesNR(paragraph, i + "", false, 10);
			}
			
			XWPFTableCell cell = table.getRow(2).getCell(4);
			cell.removeParagraph(0);
			cell.setVerticalAlignment(XWPFVertAlign.CENTER);
			paragraph = cell.addParagraph();
			setCellParagraph(paragraph);
			int cnt = 0;
			if(choice1.selectedProperty().get()) {
				createRunTimesNR(paragraph, choice1.getText().toUpperCase(), false, 10);
				cnt++;
			}
			if(choice2.selectedProperty().get()) {
				if(cnt == 1) {
					paragraph = cell.addParagraph();
					setCellParagraph(paragraph);
					createRunTimesNR(paragraph, " + ", false, 10);
					paragraph = cell.addParagraph();
					setCellParagraph(paragraph);
				}
				createRunTimesNR(paragraph, choice2.getText().toUpperCase(), false, 10);
				cnt++;
			}
			if(choice3.selectedProperty().get()) {
				if(cnt >= 1) {
					paragraph = cell.addParagraph();
					setCellParagraph(paragraph);
					createRunTimesNR(paragraph, " + ", false, 10);
					paragraph = cell.addParagraph();
					setCellParagraph(paragraph);
				}
				if(choice3.getText().indexOf("(") > 0) {
					createRunTimesNR(paragraph, choice3.getText().substring(0, choice3.getText().indexOf("(") - 1).toUpperCase(), false, 10);
					paragraph = cell.addParagraph();
					setCellParagraph(paragraph);
					createRunTimesNR(paragraph, choice3.getText().substring(choice3.getText().indexOf("("), choice3.getText().indexOf("Q") + 1), false, 10);
					run = createRunTimesNR(paragraph, choice3.getText().substring(choice3.getText().indexOf("Q") + 1, choice3.getText().indexOf("x") + 1), false, 10);
					run.setSubscript(VerticalAlign.SUBSCRIPT);
					createRunTimesNR(paragraph, choice3.getText().substring(choice3.getText().indexOf("x") + 1, choice3.getText().length()), false, 10);
				} else {
					createRunTimesNR(paragraph, choice3.getText().toUpperCase(), false, 10);
				}
				cnt++;
			}
			if(choice4.isVisible() && choice4.selectedProperty().get()) {
				if(cnt >= 1) {
					paragraph = cell.addParagraph();
					setCellParagraph(paragraph);
					createRunTimesNR(paragraph, " + ", false, 10);
					paragraph = cell.addParagraph();
					setCellParagraph(paragraph);
				}
				createRunTimesNR(paragraph, choice4.getText().substring(0, choice4.getText().indexOf("(") - 1).toUpperCase(), false, 10);
				paragraph = cell.addParagraph();
				setCellParagraph(paragraph);
				createRunTimesNR(paragraph, choice4.getText().substring(choice4.getText().indexOf("("), choice4.getText().indexOf("Q") + 1), false, 10);
				run = createRunTimesNR(paragraph, choice4.getText().substring(choice4.getText().indexOf("Q") + 1, choice4.getText().indexOf("x") + 1), false, 10);
				run.setSubscript(VerticalAlign.SUBSCRIPT);
				createRunTimesNR(paragraph, choice4.getText().substring(choice4.getText().indexOf("x") + 1, choice4.getText().length()), false, 10);
			}
			
			emptyLines(document, 3);
			paragraph = createParagraphForCertificate(document, ParagraphAlignment.LEFT);
			run = createRunSize14TimesNR(paragraph, "", false);
			createRunSize14TimesNR(paragraph, getStringOfSpaces(16) + "DIRECTOR" + getStringOfSpaces(17), true);
			run = createRunSize14TimesNR(paragraph, "", false);
			createRunSize14TimesNR(paragraph, getStringOfSpaces(18) + "RSVTI", true);
			paragraph = createParagraphForCertificate(document, ParagraphAlignment.LEFT);
			run = createRunSize14TimesNR(paragraph, "", false);
			createRunSize14TimesNR(paragraph, getStringOfSpaces((82/2-executiveName.length())/2) + executiveName + getStringOfSpaces((82/2-executiveName.length())/2), false);
			run = createRunSize14TimesNR(paragraph, "", false);
			createRunSize14TimesNR(paragraph, getStringOfSpaces((82/2-rsvti.length())/2) + rsvti + getStringOfSpaces((82/2-rsvti.length())/2), false);
			
			mergeCellsHorizontally(table, 0, 5, 6);
			mergeCellsVertically(table, 4, 2, tableMaxLength + 1);
			mergeCellsVertically(table, 0, 0, 1);
			mergeCellsVertically(table, 1, 0, 1);
			mergeCellsVertically(table, 2, 0, 1);
			mergeCellsVertically(table, 3, 0, 1);
			mergeCellsVertically(table, 4, 0, 1);
			widenTableCells(table, 0, 1225);
		    widenTableCells(table, 1, 5125);
		    widenTableCells(table, 2, 3675);
		    widenTableCells(table, 3, 1575);
		    widenTableCells(table, 4, 3750);
		    widenTableCells(table, 5, 1300);
		    widenTableCells(table, 6, 1300);
		    table.getRow(0).setHeight(550);
		    for(int i = 2; i < tableMaxLength + 2; i++) {
		    	table.getRow(i).setHeight(400);
		    }
			
			document.write(output);
			output.close();
			
			if(!DBServices.getBackupPath().isEmpty()) {
				File backupFile = new File(DBServices.getBackupPath() + "\\procese verbale");
				backupFile.mkdir();
				backupFile = new File(DBServices.getBackupPath() + "\\procese verbale\\rezultate examinare");
				backupFile.mkdir();
				backupFile = new File(DBServices.getBackupPath() + "\\procese verbale\\rezultate examinare\\" + currentDate);
				backupFile.mkdir();
				backupFile = new File(DBServices.getBackupPath() + "\\procese verbale\\rezultate examinare\\" + currentDate + "\\" + firm.getFirmName() + ".docx");
				FileOutputStream backupOutput = new FileOutputStream(backupFile);
				document.write(backupOutput);
			}
			
			document.close();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
		return file;
	}
	
	public static File generateTest(int nrOfQuestions, EmployeeWithDetails employeeDetails) {
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
			
			logGeneratedTest(document, employeeDetails);
			
			document.write(output);
			
			//Save to backup directory if there is one selected
			if(!DBServices.getBackupPath().equals("")) {
				File backupFile = new File(DBServices.getBackupPath() + "\\teste");
				backupFile.mkdir();
				backupFile = new File(DBServices.getBackupPath() + "\\teste\\" + employeeDetails.getFirmName());
				backupFile.mkdir();
				backupFile = new File(DBServices.getBackupPath() + "\\teste\\" + employeeDetails.getFirmName() + "\\" + "Examinare " + employeeDetails.getEmployee().getTitle() 
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
	
	private static void logGeneratedTest(XWPFDocument document, EmployeeWithDetails employeeDetails) {
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
			SimpleDateFormat extendedDateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT + " HH.mm.ss");
			String jarFilePath = Utils.getJarFilePath();
			File file = new File(jarFilePath + "docs\\teste\\logs\\" + dateFormat.format(Calendar.getInstance().getTime()) + "");
			file.mkdir();
			file = new File(jarFilePath + "docs\\teste\\logs\\" + dateFormat.format(Calendar.getInstance().getTime())  + "\\"
					+ employeeDetails.getEmployee().getLastName() + " " + employeeDetails.getEmployee().getFirstName() + " " + employeeDetails.getEmployee().getTitle() 
					+ " " + extendedDateFormat.format(Calendar.getInstance().getTime()) + ".docx");
			
			document.write(new FileOutputStream(file));
		} catch(Exception e) {
			e.printStackTrace();
		}
		DBServices.saveEntry(
				new LoggedTest(
					employeeDetails.getEmployee().getFirstName(), 
					employeeDetails.getEmployee().getLastName(),
					employeeDetails.getEmployee().getTitle(),
					employeeDetails.getFirmName(), 
					Calendar.getInstance().getTime()
				));
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
