package com.rsvti.database.services;

import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.entities.RigParameter;
import com.rsvti.main.Constants;

public class DBServices {
	
	private static Document document;
	private static boolean indexesAreInitialized = false;
	private static long numberOfFirms;
	private static long indexOfUpdatedFirm;
	
	private static void openFile(String filepath) {
		try {
			File file = new File(filepath);
			
			if(file.createNewFile()) {
				PrintStream ps = new PrintStream(file);
				if(filepath.contains("RigParameters.xml")) {
					ps.println("<?xml version=\"1.0\"?>\n<parameters>"
							+ "\n\t<instalatie type=\"de ridicat\"></instalatie>"
							+ "\n\t<instalatie type=\"sub presiune\"></instalatie>"
							+ "\n</parameters>");
				} else {
					ps.println("<?xml version=\"1.0\"?>\n<app>\n</app>");
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
		
		root.appendChild(document.createTextNode("\t"));
		Element firma = document.createElement("firma");
		if(update) {
			firma.setAttribute("id", "" + indexOfUpdatedFirm);
		} else {
			firma.setAttribute("id", "" + numberOfFirms++);
		}
		firma.appendChild(document.createTextNode("\n\t\t"));
		
		Element nr_inreg = document.createElement("numar_inregistrare");
		nr_inreg.appendChild(document.createTextNode(firm.getRegistrationNumber()));
		firma.appendChild(nr_inreg);
		firma.appendChild(document.createTextNode("\n\t\t"));
		
		Element cod_fiscal = document.createElement("cod_fiscal");
		cod_fiscal.appendChild(document.createTextNode(firm.getFiscalCode()));
		firma.appendChild(cod_fiscal);
		firma.appendChild(document.createTextNode("\n\t\t"));
		
		Element address = document.createElement("adresa");
		address.appendChild(document.createTextNode(firm.getAddress()));
		firma.appendChild(address);
		firma.appendChild(document.createTextNode("\n\t\t"));
		
		Element phoneNumber = document.createElement("numar_telefon");
		phoneNumber.appendChild(document.createTextNode(firm.getPhoneNumber()));
		firma.appendChild(phoneNumber);
		firma.appendChild(document.createTextNode("\n\t\t"));
		
		Element faxNumber = document.createElement("numar_fax");
		faxNumber.appendChild(document.createTextNode(firm.getFaxNumber()));
		firma.appendChild(faxNumber);
		firma.appendChild(document.createTextNode("\n\t\t"));
		
		Element email = document.createElement("email");
		email.appendChild(document.createTextNode(firm.getEmail()));
		firma.appendChild(email);
		firma.appendChild(document.createTextNode("\n\t\t"));
		
		Element bankName = document.createElement("nume_banca");
		bankName.appendChild(document.createTextNode(firm.getBankName()));
		firma.appendChild(bankName);
		firma.appendChild(document.createTextNode("\n\t\t"));
		
		Element ibanCode = document.createElement("cod_IBAN");
		ibanCode.appendChild(document.createTextNode(firm.getIbanCode()));
		firma.appendChild(ibanCode);
		firma.appendChild(document.createTextNode("\n\t\t"));
		
		//administrator
		Administrator administrator = firm.getAdministrator();
		Element adminElement = document.createElement("administrator");
		adminElement.appendChild(document.createTextNode("\n\t\t\t"));
		
		Element adminFirstName = document.createElement("nume");
		adminFirstName.appendChild(document.createTextNode(administrator.getFirstName()));
		adminElement.appendChild(adminFirstName);
		adminElement.appendChild(document.createTextNode("\n\t\t\t"));
		
		Element adminLastName = document.createElement("prenume");
		adminLastName.appendChild(document.createTextNode(administrator.getLastName()));
		adminElement.appendChild(adminLastName);
		adminElement.appendChild(document.createTextNode("\n\t\t\t"));
		
		Element idCode = document.createElement("serie_buletin");
		idCode.appendChild(document.createTextNode(administrator.getIdCode()));
		adminElement.appendChild(idCode);
		adminElement.appendChild(document.createTextNode("\n\t\t\t"));
		
		Element idNumber = document.createElement("numar_buletin");
		idNumber.appendChild(document.createTextNode(administrator.getIdNumber()));
		adminElement.appendChild(idNumber);
		adminElement.appendChild(document.createTextNode("\n\t\t\t"));
		
		Element adminPhoneNumber = document.createElement("numar_telefon_admin");
		adminPhoneNumber.appendChild(document.createTextNode(administrator.getPhoneNumber()));
		adminElement.appendChild(adminPhoneNumber);
		adminElement.appendChild(document.createTextNode("\n\t\t"));
		
		firma.appendChild(adminElement);
		
		//administrator - end
		for(Rig rigIndex : firm.getRigs()) {
			Element rig = document.createElement("instalatie");
			
			rig.setAttribute("type", rigIndex.getType());
			
			Map<String,String> parameters = rigIndex.getParameters();
			
			Element dueDate = document.createElement("data_scadenta");
			dueDate.appendChild(document.createTextNode(format.format(rigIndex.getDueDate())));
			rig.appendChild(document.createTextNode("\n\t\t\t"));
			rig.appendChild(dueDate);
			
			for(Map.Entry<String,String> rigParameterIndex : parameters.entrySet()) {
				Element node = document.createElement(rigParameterIndex.getKey());
				node.appendChild(document.createTextNode(rigParameterIndex.getValue()));
				rig.appendChild(document.createTextNode("\n\t\t\t"));
				rig.appendChild(node);
			}
			rig.appendChild(document.createTextNode("\n\t\t\t"));
			for(Employee employeeIndex : rigIndex.getEmployees()) {
				
				Element employee = document.createElement("angajat");
				employee.setAttribute("title", employeeIndex.getTitle());
				employee.appendChild(document.createTextNode("\n\t\t\t\t"));
				
				Element employeeFirstName = document.createElement("nume");
				employeeFirstName.appendChild(document.createTextNode(employeeIndex.getFirstName()));
				employee.appendChild(employeeFirstName);
				employee.appendChild(document.createTextNode("\n\t\t\t\t"));
				
				Element employeeLastName = document.createElement("prenume");
				employeeLastName.appendChild(document.createTextNode(employeeIndex.getLastName()));
				employee.appendChild(employeeLastName);
				employee.appendChild(document.createTextNode("\n\t\t\t\t"));
				
				Element employeeIdCode = document.createElement("serie_buletin");
				employeeIdCode.appendChild(document.createTextNode(employeeIndex.getIdCode()));
				employee.appendChild(employeeIdCode);
				employee.appendChild(document.createTextNode("\n\t\t\t\t"));
				
				Element employeeIdNumber = document.createElement("numar_buletin");
				employeeIdNumber.appendChild(document.createTextNode(employeeIndex.getIdNumber()));
				employee.appendChild(employeeIdNumber);
				employee.appendChild(document.createTextNode("\n\t\t\t\t"));
				
				Element personalIdentificationNumber = document.createElement("CNP");
				personalIdentificationNumber.appendChild(document.createTextNode(employeeIndex.getPersonalIdentificationNumber()));
				employee.appendChild(personalIdentificationNumber);
				employee.appendChild(document.createTextNode("\n\t\t\t\t"));
				
				EmployeeAuthorization authorization = employeeIndex.getAuthorization();
				Element authorizationElement = document.createElement("autorizatie");
				authorizationElement.appendChild(document.createTextNode("\n\t\t\t\t\t"));
				
				Element authorizationNumber = document.createElement("numar_autorizatie");
				authorizationNumber.appendChild(document.createTextNode(authorization.getAuthorizationNumber()));
				authorizationElement.appendChild(authorizationNumber);
				authorizationElement.appendChild(document.createTextNode("\n\t\t\t\t\t"));
				
				Element obtainingDate = document.createElement("data_obtinerii");
				obtainingDate.appendChild(document.createTextNode(format.format(authorization.getObtainingDate())));
				authorizationElement.appendChild(obtainingDate);
				authorizationElement.appendChild(document.createTextNode("\n\t\t\t\t\t"));
				
				Element employeeDueDate = document.createElement("data_obtinerii");
				employeeDueDate.appendChild(document.createTextNode(format.format(authorization.getDueDate())));
				authorizationElement.appendChild(employeeDueDate);
				authorizationElement.appendChild(document.createTextNode("\n\t\t\t\t"));
				
				employee.appendChild(authorizationElement);
				employee.appendChild(document.createTextNode("\n\t\t\t"));
				
				rig.appendChild(employee);
				rig.appendChild(document.createTextNode("\n\t\t\t"));
			}
			firma.appendChild(document.createTextNode("\n\t\t"));
			firma.appendChild(rig);
		}
		
		firma.appendChild(document.createTextNode("\n\t"));
		root.appendChild(firma);
		root.appendChild(document.createTextNode("\n"));
		
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(Constants.XML_DB_FILE_NAME));
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
	
	//TODO:may not be needed
	public static List<Firm> getAllFirms() {
		List<Firm> firms = new ArrayList<Firm>();
		NodeList firmNodes = (NodeList) executeXmlQuery("//firma", XPathConstants.NODESET);
		for(int i = 0; i < firmNodes.getLength(); i++) {
			firms.add(EntityBuilder.buildFirmFromXml(firmNodes.item(i)));
		}
		return firms;
	}
	
	public static void deleteEntry(Firm firm) {
		openFile(Constants.XML_DB_FILE_NAME);
		
		Element root = document.getDocumentElement();
		NodeList firmNodes = (NodeList) executeXmlQuery("//firma", XPathConstants.NODESET);
		for(int i = 0; i < firmNodes.getLength(); i++) {
			if(EntityBuilder.buildFirmFromXml(firmNodes.item(i)).equals(firm)) {
				indexOfUpdatedFirm = Long.parseLong(firmNodes.item(i).getAttributes().getNamedItem("id").getTextContent());
				root.removeChild(firmNodes.item(i).getPreviousSibling());	//deletes the CR and LF that remain after node deletion
				root.removeChild(firmNodes.item(i - 1));
			}
		}
		
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(Constants.XML_DB_FILE_NAME));
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
		
		Node liftingRigNode = (Node) executeXmlQuery(Constants.XML_RIG_PARAMETERS, "//instalatie[@type = \"de ridicat\"]", XPathConstants.NODE);
		Node pressureRigNode = (Node) executeXmlQuery(Constants.XML_RIG_PARAMETERS, "//instalatie[@type = \"sub presiune\"]", XPathConstants.NODE);
		
		Element parameterElement = document.createElement(parameter.getName());
		
		if(parameter.getType().equals("de ridicat")) {
			liftingRigNode.appendChild(document.createTextNode("\n\t\t"));
			liftingRigNode.appendChild(parameterElement);
		} else {
			pressureRigNode.appendChild(document.createTextNode("\n\t\t"));
			pressureRigNode.appendChild(parameterElement);
		}
		
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(Constants.XML_RIG_PARAMETERS));
			Source input = new DOMSource(document);
			
			transformer.transform(input, output);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteEntry(RigParameter parameter) {
		Node liftingRigNode = (Node) executeXmlQuery(Constants.XML_RIG_PARAMETERS, "//instalatie[@type = \"de ridicat\"]", XPathConstants.NODE);
		Node pressureRigNode = (Node) executeXmlQuery(Constants.XML_RIG_PARAMETERS, "//instalatie[@type = \"sub presiune\"]", XPathConstants.NODE);
		NodeList parameters;
		
		if(parameter.getType().equals("de ridicat")) {
			parameters = liftingRigNode.getChildNodes();
		} else {
			parameters = pressureRigNode.getChildNodes();
		}
		
		for(int i = 0; i < parameters.getLength(); i++) {
			if(!parameters.item(i).getTextContent().contains("\t") ||
					!parameters.item(i).getTextContent().contains("\n")) {
				if(parameters.item(i).getNodeName().equals(parameter.getName())) {
					if(parameter.getType().equals("de ridicat")) {
						liftingRigNode.removeChild(parameters.item(i).getPreviousSibling());	//deletes the CR and LF that remain after node deletion
						liftingRigNode.removeChild(parameters.item(i-1));	//because it deleted previous sibling the item to delete is now shifted one position to the left
					} else {
						pressureRigNode.removeChild(parameters.item(i).getPreviousSibling());	//deletes the CR and LF that remain after node deletion
						pressureRigNode.removeChild(parameters.item(i-1));	//because it deleted previous sibling the item to delete is now shifted one position to the left
					}
				}
			}
		}
		
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(Constants.XML_RIG_PARAMETERS));
			Source input = new DOMSource(document);
			
			transformer.transform(input, output);
		} catch(Exception e) {
			e.printStackTrace();
		}
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
