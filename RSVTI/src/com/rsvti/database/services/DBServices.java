package com.rsvti.database.services;

import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rsvti.backup.GoogleDriveBackup;
import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.database.entities.Administrator;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.EmployeeAuthorization;
import com.rsvti.database.entities.EmployeeWithDetails;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.LoggedTest;
import com.rsvti.database.entities.ParameterDetails;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.entities.RigParameter;
import com.rsvti.database.entities.RigWithDetails;
import com.rsvti.database.entities.TestQuestion;
import com.rsvti.database.entities.Valve;

import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;


public class DBServices {
	
	private static Document document;
	private static boolean indexesAreInitialized = false;
	private static long numberOfFirms;
	private static long indexOfUpdatedFirm;
	private static String jarFilePath;
	
	private static void openFile(String filepath) {
		try {
			jarFilePath = Utils.getJarFilePath();
			File file;
			if(filepath.equals(Constants.XML_ERROR_LOG_FILE)) {
				file = new File(jarFilePath + filepath);
			} else {
				file = new File(jarFilePath + "database/" + filepath);
			}
			
			if(file.createNewFile()) {
				PrintStream ps = new PrintStream(file);
				if(filepath.contains("RigParameters.xml")) {
					ps.println("<?xml version=\"1.0\"?><parameters></parameters>");
				} else if(filepath.contains("TestData.xml")) {
					ps.println("<?xml version=\"1.0\"?><test></test>");
				} else if(filepath.contains("CustomSettings.xml")) {
					ps.println("<?xml version=\"1.0\"?><custom><variable_dates></variable_dates><backup></backup></custom>");
				} else if(filepath.contains("LoggedTests.xml")) {
					ps.println("<?xml version=\"1.0\"?><log></log>");
				} else if(filepath.contains("Errlog.xml")) {
					ps.println("<?xml version=\"1.0\"?><errlog></errlog>");
				} else {
					ps.println("<?xml version=\"1.0\"?><app></app>");
				}
				ps.close();
			}
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			if(document != null) {
				if(!document.getBaseURI().substring(document.getBaseURI().lastIndexOf('/') + 1).equals(dBuilder.parse(file).getBaseURI().substring(dBuilder.parse(file).getBaseURI().lastIndexOf('/') + 1))) {
					document = dBuilder.parse(file);
				}
			} else {
				document = dBuilder.parse(file);
			}
		} catch(Exception e) {
			saveErrorLogEntry(e);
		}
	}
	
	private static void transformXmlFile(String fileName) {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output;
			if(fileName.equals(Constants.XML_ERROR_LOG_FILE)) {
				output = new StreamResult(new File(jarFilePath + fileName));
			} else {
				Thread thread = new Thread(() -> {
					GoogleDriveBackup.uploadFile(fileName);
				});
				thread.start();
				output = new StreamResult(new File(jarFilePath + "database/" + fileName));
			}
			Source input = new DOMSource(document);
			
			transformer.transform(input, output);
		} catch(Exception e) {
			saveErrorLogEntry(e);
		}
	}
	
	/**
	 * Method that sets the number of firms and rigs, depending on whether there is some data in the xml or not.
	 * If there isn't both indexes are 0. If there is some data in the xml it takes the last index of both firms and rigs to count
	 * the total number of firms and rigs that have been added at some point to the database. 
	 */
	private static void setIndexes() {
		if(!indexesAreInitialized) {
			numberOfFirms = getLastFirmIndex();
			indexesAreInitialized = true;
		}
	}
	
	public static void saveEntry(Firm firm, boolean update) {
		setIndexes();
		openFile(Constants.XML_FIRMS_FILE_NAME);
		
		Element root = document.getDocumentElement();
		
		Element firma = document.createElement("firma");
		if(update) {
			firma.setAttribute("id", "" + indexOfUpdatedFirm);
		} else {
			firma.setAttribute("id", "" + numberOfFirms++);
		}
		Element nume_firma = document.createElement("nume_firma");
		nume_firma.appendChild(document.createTextNode(firm.getFirmName()));
		firma.appendChild(nume_firma);
		
		Element nr_inreg = document.createElement("numar_inregistrare");
		nr_inreg.appendChild(document.createTextNode(firm.getRegistrationNumber()));
		firma.appendChild(nr_inreg);
		
		Element cod_fiscal = document.createElement("cod_fiscal");
		cod_fiscal.appendChild(document.createTextNode(firm.getFiscalCode()));
		firma.appendChild(cod_fiscal);
		
		Element address = document.createElement("adresa");
		address.appendChild(document.createTextNode(firm.getAddress()));
		firma.appendChild(address);
		
		Element phoneNumber = document.createElement("numar_telefon");
		phoneNumber.appendChild(document.createTextNode(firm.getPhoneNumber()));
		firma.appendChild(phoneNumber);
		
		Element faxNumber = document.createElement("numar_fax");
		faxNumber.appendChild(document.createTextNode(firm.getFaxNumber()));
		firma.appendChild(faxNumber);
		
		Element email = document.createElement("email");
		email.appendChild(document.createTextNode(firm.getEmail()));
		firma.appendChild(email);
		
		Element bankName = document.createElement("nume_banca");
		bankName.appendChild(document.createTextNode(firm.getBankName()));
		firma.appendChild(bankName);
		
		Element ibanCode = document.createElement("cod_IBAN");
		ibanCode.appendChild(document.createTextNode(firm.getIbanCode()));
		firma.appendChild(ibanCode);
		
		Element executiveName = document.createElement("director");
		executiveName.appendChild(document.createTextNode(firm.getExecutiveName()));
		firma.appendChild(executiveName);
		
		//administrator
		Administrator administrator = firm.getAdministrator();
		Element adminElement = document.createElement("administrator");
		
		Element adminFirstName = document.createElement("nume");
		adminFirstName.appendChild(document.createTextNode(administrator.getFirstName()));
		adminElement.appendChild(adminFirstName);
		
		Element adminLastName = document.createElement("prenume");
		adminLastName.appendChild(document.createTextNode(administrator.getLastName()));
		adminElement.appendChild(adminLastName);
		
		Element idCode = document.createElement("serie_buletin");
		idCode.appendChild(document.createTextNode(administrator.getIdCode()));
		adminElement.appendChild(idCode);
		
		Element idNumber = document.createElement("numar_buletin");
		idNumber.appendChild(document.createTextNode(administrator.getIdNumber()));
		adminElement.appendChild(idNumber);
		
		Element adminPhoneNumber = document.createElement("numar_telefon_admin");
		adminPhoneNumber.appendChild(document.createTextNode(administrator.getPhoneNumber()));
		adminElement.appendChild(adminPhoneNumber);
		
		firma.appendChild(adminElement);
		
		//administrator - end
		for(Rig rigIndex : firm.getRigs()) {
			Element rig = document.createElement("instalatie");
			
			rig.setAttribute("type", rigIndex.getType());
			
			List<ParameterDetails> parameters = rigIndex.getParameters();
			
			Element rigName = document.createElement("nume_instalatie");
			rigName.appendChild(document.createTextNode(rigIndex.getRigName()));
			rig.appendChild(rigName);
			
			Element revisionDate = document.createElement("data_reviziei");
			revisionDate.appendChild(document.createTextNode(rigIndex.getRevisionDate().getTime() + ""));
			rig.appendChild(revisionDate);
			
			Element authorizationExtension = document.createElement("extinderea_autorizatiei");
			authorizationExtension.appendChild(document.createTextNode("" + rigIndex.getAuthorizationExtension()));
			rig.appendChild(authorizationExtension);
			
			Element productionNumber = document.createElement("numar_fabricatie");
			productionNumber.appendChild(document.createTextNode(rigIndex.getProductionNumber()));
			rig.appendChild(productionNumber);
			
			Element productionYear = document.createElement("an_fabricatie");
			productionYear.appendChild(document.createTextNode(rigIndex.getProductionYear() + ""));
			rig.appendChild(productionYear);
			
			Element iscirRegistrationNumber = document.createElement("numar_inregistrare_iscir");
			iscirRegistrationNumber.appendChild(document.createTextNode(rigIndex.getIscirRegistrationNumber()));
			rig.appendChild(iscirRegistrationNumber);
			
			if(rigIndex.getType().equals(Constants.PRESSURE_RIG)) {
				Element valve = document.createElement("supapa");
				
				Element valveDueDate = document.createElement("data_scadentei");
				valveDueDate.appendChild(document.createTextNode(rigIndex.getValve().getDueDate().getTime() + ""));
				valve.appendChild(valveDueDate);
				
				Element valveRegistrationNumber = document.createElement("numar_inregistrare");
				valveRegistrationNumber.appendChild(document.createTextNode(rigIndex.getValve().getRegistrationNumber()));
				valve.appendChild(valveRegistrationNumber);
				
				rig.appendChild(valve);
			}
			
			for(ParameterDetails rigParameterIndex : parameters) {
				Element node = document.createElement(rigParameterIndex.getName().toLowerCase().replaceAll(" ", "_"));
				node.setAttribute("name", rigParameterIndex.getName());
				node.appendChild(document.createTextNode(rigParameterIndex.getValue()));
				node.setAttribute("mUnit", rigParameterIndex.getMeasuringUnit());
				rig.appendChild(node);
			}
			
			firma.appendChild(rig);
		}
		
		for(Employee employeeIndex : firm.getEmployees()) {
			
			Element employee = document.createElement("angajat");
			employee.setAttribute("title", employeeIndex.getTitle());
			
			Element employeeFirstName = document.createElement("nume");
			employeeFirstName.appendChild(document.createTextNode(employeeIndex.getFirstName()));
			employee.appendChild(employeeFirstName);
			
			Element employeeLastName = document.createElement("prenume");
			employeeLastName.appendChild(document.createTextNode(employeeIndex.getLastName()));
			employee.appendChild(employeeLastName);
			
			Element employeeIdCode = document.createElement("serie_buletin");
			employeeIdCode.appendChild(document.createTextNode(employeeIndex.getIdCode()));
			employee.appendChild(employeeIdCode);
			
			Element employeeIdNumber = document.createElement("numar_buletin");
			employeeIdNumber.appendChild(document.createTextNode(employeeIndex.getIdNumber()));
			employee.appendChild(employeeIdNumber);
			
			Element personalIdentificationNumber = document.createElement("CNP");
			personalIdentificationNumber.appendChild(document.createTextNode(employeeIndex.getPersonalIdentificationNumber()));
			employee.appendChild(personalIdentificationNumber);
			
			Element birthDate = document.createElement("data_nasterii");
			birthDate.appendChild(document.createTextNode(employeeIndex.getBirthDate().getTime() + ""));
			employee.appendChild(birthDate);
			
			Element birthCity = document.createElement("localitate_nastere");
			birthCity.appendChild(document.createTextNode(employeeIndex.getBirthCity()));
			employee.appendChild(birthCity);
			
			Element homeAddress = document.createElement("adresa_domiciliu");
			homeAddress.appendChild(document.createTextNode(employeeIndex.getHomeAddress()));
			employee.appendChild(homeAddress);
			
			Element homeRegion = document.createElement("judet_domiciliu");
			homeRegion.appendChild(document.createTextNode(employeeIndex.getHomeRegion()));
			employee.appendChild(homeRegion);
			
			Element isRsvti = document.createElement("este_rsvti");
			isRsvti.appendChild(document.createTextNode(employeeIndex.isRsvti() + ""));
			employee.appendChild(isRsvti);
			
			EmployeeAuthorization authorization = employeeIndex.getAuthorization();
			Element authorizationElement = document.createElement("autorizatie");
			
			Element authorizationNumber = document.createElement("numar_autorizatie");
			authorizationNumber.appendChild(document.createTextNode(authorization.getAuthorizationNumber()));
			authorizationElement.appendChild(authorizationNumber);
			
			Element obtainingDate = document.createElement("data_obtinerii");
			obtainingDate.appendChild(document.createTextNode(authorization.getObtainingDate().getTime() + ""));
			authorizationElement.appendChild(obtainingDate);
			
			Element employeeDueDate = document.createElement("data_scadenta");
			employeeDueDate.appendChild(document.createTextNode(authorization.getDueDate().getTime() + ""));
			authorizationElement.appendChild(employeeDueDate);
			
			employee.appendChild(authorizationElement);
			
			firma.appendChild(employee);
		}
		
		root.appendChild(firma);
		
		transformXmlFile(Constants.XML_FIRMS_FILE_NAME);
	}
	
	public static void updateEntry(Firm source, Firm replacement) {
		openFile(Constants.XML_FIRMS_FILE_NAME);
		setIndexes();
		List<Firm> firms = EntityBuilder.buildFirmListFormXml((NodeList) DBServices.executeXmlQuery("//firma", XPathConstants.NODESET));
		for(Firm index : firms) {
			if(index.equals(source)) {
				deleteEntry(source);
				saveEntry(replacement, true);
			}
		}
	}
	
	public static void updateRigForFirm(String firmName, Rig rigToUpdate, Rig newRig) {
		setIndexes();
		Firm firm = EntityBuilder.buildFirmFromXml((Node) executeXmlQuery("//firma[nume_firma = \"" + firmName + "\"]", XPathConstants.NODE));
		deleteEntry(firm);
		for(int i = 0; i < firm.getRigs().size(); i++) {
			if(firm.getRigs().get(i).equals(rigToUpdate)) {
				firm.getRigs().set(i, newRig);
			}
		}
		saveEntry(firm, true);
	}
	
	public static void updateEmployeeForFirm(String firmName, Employee employeeToUpdate, Employee newEmployee) {
		setIndexes();
		Firm firm = EntityBuilder.buildFirmFromXml((Node) executeXmlQuery("//firma[nume_firma = \"" + firmName + "\"]", XPathConstants.NODE));
		deleteEntry(firm);
		for(int i = 0; i < firm.getEmployees().size(); i++) {
				if(firm.getEmployees().get(i).equals(employeeToUpdate)) {
					firm.getEmployees().set(i, newEmployee);
				}
		}
		saveEntry(firm, true);
	}
	
	public static List<Firm> getAllFirms() {
		List<Firm> firms = new ArrayList<Firm>();
		NodeList firmNodes = (NodeList) executeXmlQuery("//firma", XPathConstants.NODESET);
		for(int i = 0; i < firmNodes.getLength(); i++) {
			firms.add(EntityBuilder.buildFirmFromXml(firmNodes.item(i)));
		}
		return firms;
	}
	
	public static List<Rig> getAllRigs() {
		NodeList rigNodes = (NodeList) executeXmlQuery("//instalatie", XPathConstants.NODESET);
		return EntityBuilder.buildRigListFromXml(rigNodes);
	}
	
	public static String getFirmForRig(Rig rig) {
		Node firmName = (Node) executeXmlQuery("//nume_firma[parent::firma[instalatie[nume_instalatie = '" + rig.getRigName() + "']]]", XPathConstants.NODE);
		return firmName.getTextContent();
	}
	
	public static List<Employee> getAllEmployees() {
		NodeList employeeNodes = (NodeList) executeXmlQuery("//angajat", XPathConstants.NODESET);
		return EntityBuilder.buildEmployeeListFromXml(employeeNodes);
	}
	
	public static String getFirmForEmployee(Employee employee) {
		Node firmName = (Node) executeXmlQuery("//nume_firma[parent::firma[angajat[nume = '" + employee.getLastName() + "', "
				+ "prenume ='" + employee.getFirstName() + "']]]", XPathConstants.NODE);
		return firmName.getTextContent();
	}
	
	public static List<RigParameter> getAllRigParameters() {
		List<RigParameter> rigParameters = new ArrayList<RigParameter>();
		NodeList rigParameterNodes = (NodeList) executeXmlQuery(Constants.XML_RIG_PARAMETERS_FILE_NAME, "//parameter", XPathConstants.NODESET);
		for(int i = 0; i < rigParameterNodes.getLength(); i++) {
			rigParameters.add(new RigParameter(rigParameterNodes.item(i).getAttributes().getNamedItem("type").getTextContent(), rigParameterNodes.item(i).getTextContent(), rigParameterNodes.item(i).getAttributes().getNamedItem("mUnit").getTextContent()));
		}
		return rigParameters;
	}
	
	public static List<RigParameter> getRigParametersByType(String type) {
		List<RigParameter> rigParameters = new ArrayList<RigParameter>();
		NodeList rigParameterNodes = (NodeList) executeXmlQuery(Constants.XML_RIG_PARAMETERS_FILE_NAME, "//parameter[@type=\"" + type + "\"]", XPathConstants.NODESET);
		for(int i = 0; i < rigParameterNodes.getLength(); i++) {
			rigParameters.add(new RigParameter(type, rigParameterNodes.item(i).getTextContent(), rigParameterNodes.item(i).getAttributes().getNamedItem("mUnit").getTextContent()));
		}
		return rigParameters;
	}
	
	public static void deleteEntry(Firm firm) {
		openFile(Constants.XML_FIRMS_FILE_NAME);
		
		Element root = document.getDocumentElement();
		NodeList firmNodes = (NodeList) executeXmlQuery("//firma", XPathConstants.NODESET);
		for(int i = 0; i < firmNodes.getLength(); i++) {
			if(EntityBuilder.buildFirmFromXml(firmNodes.item(i)).equals(firm)) {
				indexOfUpdatedFirm = Long.parseLong(firmNodes.item(i).getAttributes().getNamedItem("id").getTextContent());
				root.removeChild(firmNodes.item(i));
			}
		}
		
		transformXmlFile(Constants.XML_FIRMS_FILE_NAME);
	}
	
	/**
	 * Method that executes a given XPath query and returns the object/s provided. Method must be cast to obtain desired objects.
	 * @param doc the .xml document
	 * @param query the string of the query to be executed
	 * @param xpathConstant constant that shows the type of the returned object/s
	 * <br>
	 * This constant can have the following values:<br>
	 * &emsp;BOLEAN for boolean values<br>
	 * &emsp;NODE for Node values<br>
	 * &emsp;NODESET for multiple Node values<br>
	 * &emsp;STRING for String values<br>
	 * &emsp;NUMBER for Number values<br>
	 * @return object/s resulted from the query
	 */
	public static Object executeXmlQuery(String query, QName xpathConstant) {
		openFile(Constants.XML_FIRMS_FILE_NAME);
		Object returnValue = null;
		try {
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile(query);
			returnValue = expr.evaluate(document,xpathConstant);
		} catch(XPathExpressionException xee) {
			DBServices.saveErrorLogEntry(xee);
		}
		return returnValue;
	}
	
	public static Object executeXmlQuery(String filepath, String query, QName xpathConstant) {
		openFile(filepath);
		Object returnValue = null;
		try {
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile(query);
			returnValue = expr.evaluate(document,xpathConstant);
		} catch(XPathExpressionException xee) {
			xee.printStackTrace();
		}
		return returnValue;
	}
	
	public static void saveEntry(RigParameter parameter) {
		openFile(Constants.XML_RIG_PARAMETERS_FILE_NAME);
		
		Element rootElement = document.getDocumentElement();
		
		Element parameterElement = document.createElement("parameter");
		parameterElement.appendChild(document.createTextNode(parameter.getName()));
		parameterElement.setAttribute("type", parameter.getType());
		parameterElement.setAttribute("mUnit", parameter.getMeasuringUnit());
		
		rootElement.appendChild(parameterElement);
		
		transformXmlFile(Constants.XML_RIG_PARAMETERS_FILE_NAME);
	}
	
	public static void deleteEntry(RigParameter parameter) {
		openFile(Constants.XML_RIG_PARAMETERS_FILE_NAME);
		
		Element rootElement = document.getDocumentElement();
		
		for(int i = 0; i < rootElement.getChildNodes().getLength(); i++) {
			Node parameterNode = rootElement.getChildNodes().item(i);
			if(parameterNode.getTextContent().equals(parameter.getName()) &&
					parameterNode.getAttributes().getNamedItem("type").getTextContent().equals(parameter.getType()) &&
					parameterNode.getAttributes().getNamedItem("mUnit").getTextContent().equals(parameter.getMeasuringUnit())) {
				rootElement.removeChild(parameterNode);
			}
		}
		
		transformXmlFile(Constants.XML_RIG_PARAMETERS_FILE_NAME);
	}
	
	public static List<EmployeeWithDetails> getEmployeesBetweenDateInterval(Date beginDate, Date endDate) {
		List<EmployeeWithDetails> selectedEmployees = new ArrayList<EmployeeWithDetails>();
		NodeList firmNodes = (NodeList) executeXmlQuery("//firma", XPathConstants.NODESET);
		for(int i = 0; i < firmNodes.getLength(); i++) {
			Firm firm = EntityBuilder.buildFirmFromXml(firmNodes.item(i));
			for(int j = 0; j < firm.getEmployees().size(); j++) {
				Date dueDate = firm.getEmployees().get(j).getAuthorization().getDueDate();
				if(Utils.resetTimeForDate(dueDate).equals(Utils.resetTimeForDate(beginDate)) || Utils.resetTimeForDate(dueDate).equals(Utils.resetTimeForDate(endDate)) || 
						(Utils.resetTimeForDate(dueDate).after(Utils.resetTimeForDate(beginDate)) && Utils.resetTimeForDate(dueDate).before(Utils.resetTimeForDate(endDate)))) {
					selectedEmployees.add(new EmployeeWithDetails(firm.getEmployees().get(j), firm.getFirmName(), 
							firm.getAddress(), firm.getExecutiveName(), dueDate));
				}
			}
		}
		return selectedEmployees;
	}
	
	public static List<RigWithDetails> getRigsBetweenDateInterval(Date beginDate, Date endDate) {
		List<RigWithDetails> selectedRigs = new ArrayList<RigWithDetails>();
		NodeList firmNodes = (NodeList) executeXmlQuery("//firma", XPathConstants.NODESET);
		for(int i = 0; i < firmNodes.getLength(); i++) {
			Firm firm = EntityBuilder.buildFirmFromXml(firmNodes.item(i));
			for(int j = 0; j < firm.getRigs().size(); j++) {
				Date dueDate = firm.getRigs().get(j).getDueDate();
				if(Utils.resetTimeForDate(dueDate).equals(Utils.resetTimeForDate(beginDate)) || Utils.resetTimeForDate(dueDate).equals(Utils.resetTimeForDate(endDate)) || 
						(Utils.resetTimeForDate(dueDate).after(Utils.resetTimeForDate(beginDate)) && Utils.resetTimeForDate(dueDate).before(Utils.resetTimeForDate(endDate)))) {
					selectedRigs.add(new RigWithDetails(firm.getRigs().get(j), firm, dueDate));
				}
			}
		}
		return selectedRigs;
	}
	
	public static List<Valve> getValvesBetweenDateInterval(Date beginDate, Date endDate) {
		NodeList valveNodes = (NodeList) executeXmlQuery("//supapa", XPathConstants.NODESET);
		List<Valve> valves = EntityBuilder.buildValveListFromXml(valveNodes);
		List<Valve> tmp = new ArrayList<>();
		for(Valve index : valves) {
			Date dueDate = index.getDueDate();
			if(Utils.resetTimeForDate(dueDate).equals(Utils.resetTimeForDate(beginDate)) || Utils.resetTimeForDate(dueDate).equals(Utils.resetTimeForDate(endDate)) || 
					(Utils.resetTimeForDate(dueDate).after(Utils.resetTimeForDate(beginDate)) && Utils.resetTimeForDate(dueDate).before(Utils.resetTimeForDate(endDate)))) {
				tmp.add(index);
			}
		}
		return tmp;
	}
	
	public static int getLastFirmIndex() {
		int maxIndex = 0;
		NodeList firms = (NodeList) executeXmlQuery("//firma", XPathConstants.NODESET);
		for(int i = 0; i < firms.getLength(); i++) {
			if(Integer.parseInt(firms.item(i).getAttributes().getNamedItem("id").getTextContent()) > maxIndex) {
				maxIndex = Integer.parseInt(firms.item(i).getAttributes().getNamedItem("id").getTextContent());
			}
		}
		return maxIndex;
	}
	
	public static int getLastRigIndex() {
		int maxIndex = 0;
		NodeList rigs = (NodeList) executeXmlQuery("//instalatie", XPathConstants.NODESET);
		for(int i = 0; i < rigs.getLength(); i++) {
			if(Integer.parseInt(rigs.item(i).getAttributes().getNamedItem("id").getTextContent()) > maxIndex) {
				maxIndex = Integer.parseInt(rigs.item(i).getAttributes().getNamedItem("id").getTextContent());
			}
		}
		return maxIndex;
	}
	
	public static void saveEntry(TestQuestion question) {
		openFile(Constants.XML_TEST_DATA_FILE_NAME);
		
		Element rootElement = document.getDocumentElement();
		
		Element questionElement = document.createElement("intrebare");
		questionElement.setAttribute("type", question.getType());
		
		Element questionText = document.createElement("enunt");
		questionText.appendChild(document.createTextNode(question.getQuestion()));
		questionElement.appendChild(questionText);
		
		for(String index : question.getAnswers()) {
			Element answerElement = document.createElement("varianta");
			answerElement.appendChild(document.createTextNode(index));
			questionElement.appendChild(answerElement);
		}
		
		rootElement.appendChild(questionElement);
		
		transformXmlFile(Constants.XML_TEST_DATA_FILE_NAME);
	}
	
	public static List<TestQuestion> getAllTestQuestions() {
		List<TestQuestion> questions = new ArrayList<TestQuestion>();
		
		NodeList questionNodes = (NodeList) executeXmlQuery(Constants.XML_TEST_DATA_FILE_NAME, "//intrebare", XPathConstants.NODESET);
		for(int i = 0; i < questionNodes.getLength(); i++) {
			questions.add(EntityBuilder.buildTestQuestionFromXml(questionNodes.item(i)));
		}
		
		return questions;
	}
	
	public static List<TestQuestion> getTestQuestionsOfType(String type) {
		List<TestQuestion> questions = new ArrayList<TestQuestion>();
		
		NodeList questionNodes = (NodeList) executeXmlQuery(Constants.XML_TEST_DATA_FILE_NAME, "//intrebare[@type = \"" + type + "\"]", XPathConstants.NODESET);
		for(int i = 0; i < questionNodes.getLength(); i++) {
			questions.add(EntityBuilder.buildTestQuestionFromXml(questionNodes.item(i)));
		}
		
		return questions;
	}
	
	public static void deleteEntry(TestQuestion question) {
		openFile(Constants.XML_TEST_DATA_FILE_NAME);
		
		Element rootElement = document.getDocumentElement();
		
		for(int i = 0; i < rootElement.getChildNodes().getLength(); i++) {
			Node questionNode = rootElement.getChildNodes().item(i);
			if(questionNode.getChildNodes().item(0).getTextContent().equals(question.getQuestion()) &&
					questionNode.getChildNodes().item(1).getTextContent().equals(question.getAnswers().get(0)) &&
					questionNode.getChildNodes().item(2).getTextContent().equals(question.getAnswers().get(1)) &&
					questionNode.getChildNodes().item(3).getTextContent().equals(question.getAnswers().get(2))) {
				rootElement.removeChild(questionNode);
			}
		}
		
		transformXmlFile(Constants.XML_TEST_DATA_FILE_NAME);
	}
	
	public static void updateEntry(TestQuestion questionToUpdate, TestQuestion newQuestion) {
		NodeList questionNodes = (NodeList) executeXmlQuery(Constants.XML_TEST_DATA_FILE_NAME, "//intrebare", XPathConstants.NODESET);
		
		for(int i = 0; i < questionNodes.getLength(); i++) {
			if(EntityBuilder.buildTestQuestionFromXml(questionNodes.item(i)).equals(questionToUpdate)) {
				deleteEntry(questionToUpdate);
				saveEntry(newQuestion);
			}
		}
	}
	
	public static void saveEntry(Date variableDate) {
		Element variableDates = (Element) executeXmlQuery(Constants.XML_CUSTOM_SETTINGS_FILE_NAME, "//variable_dates", XPathConstants.NODE);
		
		Element date = document.createElement("date");
		date.appendChild(document.createTextNode(variableDate.getTime() + ""));
		variableDates.appendChild(date);
		
		transformXmlFile(Constants.XML_CUSTOM_SETTINGS_FILE_NAME);
	}
	
	public static void deleteEntry(Date variableDate) {
		Element variableDatesElement = (Element) executeXmlQuery(Constants.XML_CUSTOM_SETTINGS_FILE_NAME, "//variable_dates", XPathConstants.NODE);
		NodeList dates = (NodeList) executeXmlQuery(Constants.XML_CUSTOM_SETTINGS_FILE_NAME, "//date", XPathConstants.NODESET);
		
		for(int i = 0; i < dates.getLength(); i++) {
			if(dates.item(i).getTextContent().equals(variableDate.getTime() + "")) {
				variableDatesElement.removeChild(dates.item(i));
			}
		}
		
		transformXmlFile(Constants.XML_CUSTOM_SETTINGS_FILE_NAME);
	}
	
	public static List<Date> getVariableVacationDates() {
		List<Date> dates = new ArrayList<Date>();
		
		NodeList dateNodes = (NodeList) executeXmlQuery(Constants.XML_CUSTOM_SETTINGS_FILE_NAME, "//date", XPathConstants.NODESET);
		
		for(int i = 0; i < dateNodes.getLength(); i++) {
			try {
				dates.add(new Date(Long.parseLong(dateNodes.item(i).getTextContent())));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dates;
	}
	
	public static void saveBackupPath(String backupPath) {
		Node backupPathNode = (Node) executeXmlQuery(Constants.XML_CUSTOM_SETTINGS_FILE_NAME, "//backupPath", XPathConstants.NODE);
		Element backupPathElement = document.createElement("backupPath");
		backupPathElement.appendChild(document.createTextNode(backupPath));
		if(backupPathNode == null) {
			document.getDocumentElement().appendChild(backupPathElement);
		} else {
			document.getDocumentElement().replaceChild(backupPathElement, backupPathNode);
		}
		
		transformXmlFile(Constants.XML_CUSTOM_SETTINGS_FILE_NAME);
	}
	
	public static String getBackupPath() {
		Node backupPathNode = (Node) executeXmlQuery(Constants.XML_CUSTOM_SETTINGS_FILE_NAME, "//backupPath", XPathConstants.NODE);
		if(backupPathNode == null) {
			return "";
		} else {
			return backupPathNode.getFirstChild().getTextContent();
		}
	}
	
	public static void saveHomeDateDisplayInterval(int numberOf, int unit) {
		Node homeDateDisplayIntervalNode = (Node) executeXmlQuery(Constants.XML_CUSTOM_SETTINGS_FILE_NAME, "//dateInterval", XPathConstants.NODE);
		Element homeDateDisplayIntervalElement = document.createElement("dateInterval");
		Element numberOfUnitsElement = document.createElement("numberOfUnits");
		numberOfUnitsElement.appendChild(document.createTextNode(numberOf + ""));
		Element unitElement = document.createElement("unit");
		unitElement.appendChild(document.createTextNode(unit + ""));
		homeDateDisplayIntervalElement.appendChild(numberOfUnitsElement);
		homeDateDisplayIntervalElement.appendChild(unitElement);
		if(homeDateDisplayIntervalNode == null) {
			document.getDocumentElement().appendChild(homeDateDisplayIntervalElement);
		} else {
			document.getDocumentElement().replaceChild(homeDateDisplayIntervalElement, homeDateDisplayIntervalNode);
		}
		
		transformXmlFile(Constants.XML_CUSTOM_SETTINGS_FILE_NAME);
	}
	
	public static String getHomeDateDisplayInterval() {
		Node homeDateDisplayIntervalNode = (Node) executeXmlQuery(Constants.XML_CUSTOM_SETTINGS_FILE_NAME, "//dateInterval", XPathConstants.NODE);
		if(homeDateDisplayIntervalNode == null) {
			return "";
		} else {
			return homeDateDisplayIntervalNode.getFirstChild().getTextContent() + " " + homeDateDisplayIntervalNode.getChildNodes().item(1).getTextContent();
		}
	}
	
	public static void saveDatePattern(String datePattern) {
		Node datePatternNode = (Node) executeXmlQuery(Constants.XML_CUSTOM_SETTINGS_FILE_NAME, "//datePattern", XPathConstants.NODE);
		Element datePatternElement = document.createElement("datePattern");
		datePatternElement.appendChild(document.createTextNode(datePattern));
		if(datePatternNode == null) {
			document.getDocumentElement().appendChild(datePatternElement);
		} else {
			document.getDocumentElement().replaceChild(datePatternElement, datePatternNode);
		}
		
		transformXmlFile(Constants.XML_CUSTOM_SETTINGS_FILE_NAME);
	}
	
	public static String getDatePattern() {
		Node datePatternNode = (Node) executeXmlQuery(Constants.XML_CUSTOM_SETTINGS_FILE_NAME, "//datePattern", XPathConstants.NODE);
		if(datePatternNode == null) {
			return Constants.DEFAULT_DATE_FORMAT;
		} else {
			return datePatternNode.getFirstChild().getTextContent();
		}
	}
	
	public static String getRomanianDatePattern() {
		Node datePatternNode = (Node) executeXmlQuery(Constants.XML_CUSTOM_SETTINGS_FILE_NAME, "//datePattern", XPathConstants.NODE);
		if(datePatternNode == null) {
			return "zz-ll-aaaa";
		} else {
			return datePatternNode.getFirstChild().getTextContent().replaceAll("d", "z").replaceAll("M", "l").replaceAll("y", "a");
		}
	}
	
	public static void saveEntry(LoggedTest loggedTest) {
		openFile(Constants.XML_LOGGED_TESTS_FILE_NAME);
		
		Element rootElement = document.getDocumentElement();
		
		Element loggedTestElement = document.createElement("test");
		
		loggedTestElement.setAttribute("nume_angajat", loggedTest.getEmployeeLastName());
		loggedTestElement.setAttribute("prenume_angajat", loggedTest.getEmployeeFirstName());
		loggedTestElement.setAttribute("titlu_angajat", loggedTest.getEmployeeTitle());
		loggedTestElement.setAttribute("nume_firma", loggedTest.getFirmName());
		loggedTestElement.setAttribute("data_si_ora_generarii", loggedTest.getGenerationDateAndTime().getTime() + "");
		
		rootElement.appendChild(loggedTestElement);
		
		transformXmlFile(Constants.XML_LOGGED_TESTS_FILE_NAME);
	}
	
	public static void deleteEntry(LoggedTest loggedTest) {
		openFile(Constants.XML_LOGGED_TESTS_FILE_NAME);
		
		Element rootElement = document.getDocumentElement();
		
		for(int i = 0; i < rootElement.getChildNodes().getLength(); i++) {
			if(EntityBuilder.buildLoggedTestFormXml(rootElement.getChildNodes().item(i)).equals(loggedTest)) {
				rootElement.removeChild(rootElement.getChildNodes().item(i));
			}
		}
		
		transformXmlFile(Constants.XML_LOGGED_TESTS_FILE_NAME);
	}
	
	public static List<LoggedTest> getAllLoggedTests() {
		NodeList loggedTestNodes = (NodeList) executeXmlQuery(Constants.XML_LOGGED_TESTS_FILE_NAME, "//test", XPathConstants.NODESET);
		List<LoggedTest> loggedTests = new ArrayList<LoggedTest>();
		for(int i = 0; i < loggedTestNodes.getLength(); i++) {
			loggedTests.add(EntityBuilder.buildLoggedTestFormXml(loggedTestNodes.item(i)));
		}
		return loggedTests;
	}
	
	public static void saveMaximumLogSize(int maximumLogSize) {
		Node maximumLogSizeNode = (Node) executeXmlQuery(Constants.XML_CUSTOM_SETTINGS_FILE_NAME, "//maxLogSize", XPathConstants.NODE);
		Element maximumLogSizeElement = document.createElement("maxLogSize");
		maximumLogSizeElement.appendChild(document.createTextNode(maximumLogSize + ""));
		if(maximumLogSizeNode == null) {
			document.getDocumentElement().appendChild(maximumLogSizeElement);
		} else {
			document.getDocumentElement().replaceChild(maximumLogSizeElement, maximumLogSizeNode);
		}
		
		transformXmlFile(Constants.XML_CUSTOM_SETTINGS_FILE_NAME);
	}
	
	public static int getMaximumLogSize() {
		Node maximumLogSizeNode = (Node) executeXmlQuery(Constants.XML_CUSTOM_SETTINGS_FILE_NAME, "//maxLogSize", XPathConstants.NODE);
		if(maximumLogSizeNode == null) {
			return 999;
		} else {
			return Integer.parseInt(maximumLogSizeNode.getFirstChild().getTextContent());
		}
	}
	
	public static void saveErrorLogEntry(Exception e) {
		Calendar calendar = Calendar.getInstance();
		String currentDate = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT).format(calendar.getTime());
		String currentTimeStamp = new SimpleDateFormat(Constants.ERRLOG_TIMESTAMP_FORMAT).format(calendar.getTime());
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		String exceptionName = e.getClass().getName().substring(e.getClass().getName().lastIndexOf(".") + 1);
		
		openFile(Constants.XML_ERROR_LOG_FILE);
		Element root = document.getDocumentElement();
		Node currentDateNode = (Node) executeXmlQuery(Constants.XML_ERROR_LOG_FILE, "//_" + currentDate, XPathConstants.NODE);
		
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		String callerClassName = stackTraceElement.getClassName();
		String callerMethodName = stackTraceElement.getMethodName();
		
		if(currentDateNode == null) {
			Element currentDateElement = document.createElement("_" + currentDate);
			Element logEntryElement = document.createElement("_" + currentTimeStamp);
			logEntryElement.setAttribute("exception", exceptionName);
			logEntryElement.setAttribute("class",callerClassName.substring(callerClassName.lastIndexOf(".") + 1));
			logEntryElement.setAttribute("method", callerMethodName);
			logEntryElement.setTextContent("\n" + errors.toString());
			currentDateElement.appendChild(logEntryElement);
			root.appendChild(currentDateElement);
		} else {
			Element logEntryElement = document.createElement("_" + currentTimeStamp);
			logEntryElement.setAttribute("exception", exceptionName);
			logEntryElement.setAttribute("class",callerClassName.substring(callerClassName.lastIndexOf(".") + 1));
			logEntryElement.setAttribute("method", callerMethodName);
			logEntryElement.setTextContent("\n" + errors.toString());
			currentDateNode.appendChild(logEntryElement);
		}
		
		transformXmlFile(Constants.XML_ERROR_LOG_FILE);
		
		e.printStackTrace();
		Utils.alert(AlertType.ERROR, "Eroare", "A apărut o eroare!", 
				"Eroarea s-a inregistrat la data de: " + currentDate + ", ora: " + new SimpleDateFormat(Constants.DEFAULT_TIMESTAMP_FORMAT).format(calendar.getTime()), false);
	}
	
	public static List<Employee> getRsvtiFromFirm(String firmName) {
		NodeList employees = (NodeList) executeXmlQuery("//firma[nume_firma = '" + firmName + "']/angajat[este_rsvti = 'true']", XPathConstants.NODESET);
		return EntityBuilder.buildEmployeeListFromXml(employees);
	}
	
	public static String getPersonalIdentificationNumberForEmployee(String lastName, String firstName) {
		Node personalIdentificationNumberNode = (Node) executeXmlQuery("//angajat[nume = '" + firstName + "'][prenume = '" + lastName + "']/CNP", XPathConstants.NODE);
		return personalIdentificationNumberNode.getTextContent();
	}
}