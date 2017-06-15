package com.rsvti.database.services;

import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.rsvti.database.entities.Administrator;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.EmployeeAuthorization;
import com.rsvti.database.entities.EmployeeDueDateDetails;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.ParameterDetails;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.entities.RigDueDateDetails;
import com.rsvti.database.entities.RigParameter;
import com.rsvti.main.Constants;
import com.rsvti.main.Utils;

public class DBServices {
	
	private static Document document;
	private static boolean indexesAreInitialized = false;
	private static long numberOfFirms;
	private static long indexOfUpdatedFirm;
	private static String jarFilePath;
	
	private static void openFile(String filepath) {
		try {
			jarFilePath = Utils.getJarFilePath();
			File file = new File(jarFilePath + filepath);
			
			if(file.createNewFile()) {
				PrintStream ps = new PrintStream(file);
				if(filepath.contains("RigParameters.xml")) {
					ps.println("<?xml version=\"1.0\"?><parameters></parameters>");
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
			e.printStackTrace();
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
		openFile(Constants.XML_DB_FILE_NAME);
		setIndexes();
		
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
		
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
			revisionDate.appendChild(document.createTextNode(format.format(rigIndex.getRevisionDate())));
			rig.appendChild(revisionDate);
			
			Element authorizationExtension = document.createElement("extinderea_autorizatiei");
			authorizationExtension.appendChild(document.createTextNode("" + rigIndex.getAuthorizationExtension()));
			rig.appendChild(authorizationExtension);
			
			for(ParameterDetails rigParameterIndex : parameters) {
				Element node = document.createElement(rigParameterIndex.getName());
				node.appendChild(document.createTextNode(rigParameterIndex.getValue()));
				node.setAttribute("mUnit", rigParameterIndex.getMeasuringUnit());
				rig.appendChild(node);
			}
			for(Employee employeeIndex : rigIndex.getEmployees()) {
				
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
				
				EmployeeAuthorization authorization = employeeIndex.getAuthorization();
				Element authorizationElement = document.createElement("autorizatie");
				
				Element authorizationNumber = document.createElement("numar_autorizatie");
				authorizationNumber.appendChild(document.createTextNode(authorization.getAuthorizationNumber()));
				authorizationElement.appendChild(authorizationNumber);
				
				Element obtainingDate = document.createElement("data_obtinerii");
				obtainingDate.appendChild(document.createTextNode(format.format(authorization.getObtainingDate())));
				authorizationElement.appendChild(obtainingDate);
				
				Element employeeDueDate = document.createElement("data_scadenta");
				employeeDueDate.appendChild(document.createTextNode(format.format(authorization.getDueDate())));
				authorizationElement.appendChild(employeeDueDate);
				
				employee.appendChild(authorizationElement);
				
				rig.appendChild(employee);
			}
			firma.appendChild(rig);
		}
		
		root.appendChild(firma);
		
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(jarFilePath + Constants.XML_DB_FILE_NAME));
			Source input = new DOMSource(document);
			
			transformer.transform(input, output);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateEntry(Firm source, Firm replacement) {
		openFile(Constants.XML_DB_FILE_NAME);
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
		openFile(Constants.XML_DB_FILE_NAME);
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
	
	public static void updateEmployeeForRig(String firmAndRigName, Employee employeeToUpdate, Employee newEmployee) {
		openFile(Constants.XML_DB_FILE_NAME);
		setIndexes();
		String firmName = firmAndRigName.split("-")[0].trim();
		String rigName = firmAndRigName.split("-")[1].trim();
		Firm firm = EntityBuilder.buildFirmFromXml((Node) executeXmlQuery("//firma[nume_firma = \"" + firmName + "\"]", XPathConstants.NODE));
		deleteEntry(firm);
		for(int i = 0; i < firm.getRigs().size(); i++) {
			if(firm.getRigs().get(i).getRigName().equals(rigName)) {
				for(int j = 0; j < firm.getRigs().get(i).getEmployees().size(); j++) {
					if(firm.getRigs().get(i).getEmployees().get(j).equals(employeeToUpdate)) {
						firm.getRigs().get(i).getEmployees().set(j, newEmployee);
					}
				}
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
		List<Rig> rigs = new ArrayList<Rig>();
		NodeList rigNodes = (NodeList) executeXmlQuery("//instalatie", XPathConstants.NODESET);
		for(int i = 0; i < rigNodes.getLength(); i++) {
			rigs.add(EntityBuilder.buildRigFromXml(rigNodes.item(i)));
		}
		return rigs;
	}
	
	public static List<RigParameter> getAllRigParameters() {
		List<RigParameter> rigParameters = new ArrayList<RigParameter>();
		NodeList rigParameterNodes = (NodeList) executeXmlQuery(Constants.XML_RIG_PARAMETERS, "//parameter", XPathConstants.NODESET);
		for(int i = 0; i < rigParameterNodes.getLength(); i++) {
			rigParameters.add(new RigParameter(rigParameterNodes.item(i).getAttributes().getNamedItem("type").getTextContent(), rigParameterNodes.item(i).getTextContent(), rigParameterNodes.item(i).getAttributes().getNamedItem("mUnit").getTextContent()));
		}
		return rigParameters;
	}
	
	public static List<RigParameter> getRigParametersByType(String type) {
		List<RigParameter> rigParameters = new ArrayList<RigParameter>();
		NodeList rigParameterNodes = (NodeList) executeXmlQuery(Constants.XML_RIG_PARAMETERS, "//parameter[@type=\"" + type + "\"]", XPathConstants.NODESET);
		for(int i = 0; i < rigParameterNodes.getLength(); i++) {
			rigParameters.add(new RigParameter(type, rigParameterNodes.item(i).getTextContent(), rigParameterNodes.item(i).getAttributes().getNamedItem("mUnit").getTextContent()));
		}
		return rigParameters;
	}
	
	public static void deleteEntry(Firm firm) {
		openFile(Constants.XML_DB_FILE_NAME);
		
		Element root = document.getDocumentElement();
		NodeList firmNodes = (NodeList) executeXmlQuery("//firma", XPathConstants.NODESET);
		for(int i = 0; i < firmNodes.getLength(); i++) {
			if(EntityBuilder.buildFirmFromXml(firmNodes.item(i)).equals(firm)) {
				indexOfUpdatedFirm = Long.parseLong(firmNodes.item(i).getAttributes().getNamedItem("id").getTextContent());
				root.removeChild(firmNodes.item(i));
			}
		}
		
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(jarFilePath + Constants.XML_DB_FILE_NAME));
			Source input = new DOMSource(document);
			
			transformer.transform(input, output);
		} catch(Exception e) {
			e.printStackTrace();
		}
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
		openFile(Constants.XML_DB_FILE_NAME);
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
		openFile(Constants.XML_RIG_PARAMETERS);
		
		Element rootElement = document.getDocumentElement();
		
		Element parameterElement = document.createElement("parameter");
		parameterElement.appendChild(document.createTextNode(parameter.getName()));
		parameterElement.setAttribute("type", parameter.getType());
		parameterElement.setAttribute("mUnit", parameter.getMeasuringUnit());
		
		rootElement.appendChild(parameterElement);
		
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(jarFilePath + Constants.XML_RIG_PARAMETERS));
			Source input = new DOMSource(document);
			
			transformer.transform(input, output);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteEntry(RigParameter parameter) {
		openFile(Constants.XML_RIG_PARAMETERS);
		
		Element rootElement = document.getDocumentElement();
		
		for(int i = 0; i < rootElement.getChildNodes().getLength(); i++) {
			Node parameterNode = rootElement.getChildNodes().item(i);
			if(parameterNode.getTextContent().equals(parameter.getName()) &&
					parameterNode.getAttributes().getNamedItem("type").getTextContent().equals(parameter.getType()) &&
					parameterNode.getAttributes().getNamedItem("mUnit").getTextContent().equals(parameter.getMeasuringUnit())) {
				rootElement.removeChild(parameterNode);
			}
		}
		
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(jarFilePath + Constants.XML_RIG_PARAMETERS));
			Source input = new DOMSource(document);
			
			transformer.transform(input, output);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<EmployeeDueDateDetails> getEmployeesBetweenDateInterval(Date beginDate, Date endDate) {
		List<EmployeeDueDateDetails> selectedEmployees = new ArrayList<EmployeeDueDateDetails>();
		NodeList firmNodes = (NodeList) executeXmlQuery("//firma", XPathConstants.NODESET);
		for(int i = 0; i < firmNodes.getLength(); i++) {
			Firm firm = EntityBuilder.buildFirmFromXml(firmNodes.item(i));
			for(int j = 0; j < firm.getRigs().size(); j++) {
				Rig rig = firm.getRigs().get(j);
				for(int k = 0; k < rig.getEmployees().size(); k++) {
					Date dueDate = rig.getEmployees().get(k).getAuthorization().getDueDate();
					if(dueDate.equals(beginDate) || dueDate.equals(endDate) || (dueDate.after(beginDate) && dueDate.before(endDate))) {
						selectedEmployees.add(new EmployeeDueDateDetails(rig.getEmployees().get(k), rig, firm.getFirmName(), dueDate));
					}
				}
			}
		}
		return selectedEmployees;
	}
	
	public static List<RigDueDateDetails> getRigsBetweenDateInterval(Date beginDate, Date endDate) {
		List<RigDueDateDetails> selectedRigs = new ArrayList<RigDueDateDetails>();
		NodeList firmNodes = (NodeList) executeXmlQuery("//firma", XPathConstants.NODESET);
		for(int i = 0; i < firmNodes.getLength(); i++) {
			Firm firm = EntityBuilder.buildFirmFromXml(firmNodes.item(i));
			for(int j = 0; j < firm.getRigs().size(); j++) {
				Date dueDate = firm.getRigs().get(j).getDueDate(); 
				if(dueDate.equals(beginDate) || dueDate.equals(endDate) || (dueDate.after(beginDate) && dueDate.before(endDate))) {
					selectedRigs.add(new RigDueDateDetails(firm.getRigs().get(j), firm.getFirmName(), dueDate));
				}
			}
		}
		return selectedRigs;
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
}
