package com.rsvti.database.services;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
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
import org.w3c.dom.NodeList;

import com.rsvti.database.entities.Administrator;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.Rig;
import com.rsvti.main.Constants;

public class DBServices {
	
	private static long indexOfFirms;
	private static long indexOfRigs;
	
	public static void saveEntry(Document document, Firm firm) {
		Element root = document.getDocumentElement();
		
		root.appendChild(document.createTextNode("\t"));
		Element firma = document.createElement("firma");
		firma.setAttribute("id", "" + indexOfFirms);
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
		
		Element dueDate = document.createElement("data_scadentei");
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
		dueDate.appendChild(document.createTextNode(format.format(firm.getDueDate())));
		firma.appendChild(dueDate);
		firma.appendChild(document.createTextNode("\n\t\t"));
		
		//administrator
		Administrator administrator = firm.getAdministrator();
		Element adminElement = document.createElement("administrator");
		firma.appendChild(adminElement);
		adminElement.appendChild(document.createTextNode("\n\t\t\t"));
		
		Element adminName = document.createElement("nume");
		adminName.appendChild(document.createTextNode(administrator.getName()));
		adminElement.appendChild(adminName);
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
		//administrator - end
		
		for(Rig rigIndex : firm.getRigs()) {
			Element rig = document.createElement("instalatie");
			rig.setAttribute("id", indexOfRigs + "");
			rig.setAttribute("type", rigIndex.getType());
			
			Map<String,String> parameters = rigIndex.getParameters();
			
			for(Map.Entry<String,String> rigParameterIndex : parameters.entrySet()) {
				Element node = document.createElement(rigParameterIndex.getKey());
				node.appendChild(document.createTextNode(rigParameterIndex.getValue()));
				rig.appendChild(document.createTextNode("\n\t\t\t"));
				rig.appendChild(node);
			}
			rig.appendChild(document.createTextNode("\n\t\t"));
			firma.appendChild(document.createTextNode("\n\t\t"));
			firma.appendChild(rig);
			
			indexOfRigs++;
		}
		
		firma.appendChild(document.createTextNode("\n\t"));
		root.appendChild(firma);
		root.appendChild(document.createTextNode("\n"));
		
		indexOfFirms++;
		
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(Constants.XML_DB_FILE_NAME));
			Source input = new DOMSource(document);
			
			transformer.transform(input, output);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void saveEntry(Document document, Rig rig) {
		Element root = document.getDocumentElement();
		
		root.appendChild(document.createTextNode("\t"));
		Element instalatie = document.createElement("instalatie");
		instalatie.setAttribute("id", "" + indexOfRigs);
		instalatie.setAttribute("type", rig.getType());
		
		Map<String,String> parameters = rig.getParameters();
		
		for(Map.Entry<String,String> index : parameters.entrySet()) {
			Element node = document.createElement(index.getKey());
			node.appendChild(document.createTextNode(index.getValue()));
			instalatie.appendChild(document.createTextNode("\n\t\t"));
			instalatie.appendChild(node);
		}
		
		instalatie.appendChild(document.createTextNode("\n\t"));
		root.appendChild(instalatie);
		root.appendChild(document.createTextNode("\n"));
		
		indexOfRigs++;
		
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(Constants.XML_DB_FILE_NAME));
			Source input = new DOMSource(document);
			
			transformer.transform(input, output);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//TODO:may not be needed
	public static List<Firm> getAllFirms(Document document) {
		List<Firm> firms = new ArrayList<Firm>();
		NodeList firmNodes = (NodeList) executeXmlQuery(document, "//firma", XPathConstants.NODESET);
		for(int i = 0; i < firmNodes.getLength(); i++) {
			firms.add(EntityBuilder.buildFirmFromXml(firmNodes.item(i)));
		}
		return firms;
	}
	
	public static void deleteEntry(Document document, Firm firm) {
		Element root = document.getDocumentElement();
		NodeList firmNodes = (NodeList) executeXmlQuery(document, "//firma", XPathConstants.NODESET);
		for(int i = 0; i < firmNodes.getLength(); i++) {
			if(EntityBuilder.buildFirmFromXml(firmNodes.item(i)).equals(firm)) {
				root.removeChild(firmNodes.item(i).getPreviousSibling().getPreviousSibling());	//deletes the CR and LF that remain after node deletion
				root.removeChild(firmNodes.item(i));
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
	 * @param document the .xml document
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
	public static Object executeXmlQuery(Document document,String query, QName xpathConstant) {
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
	
	public static int getLastFirmIndex(Document document) {
		int maxIndex = 0;
		NodeList firms = (NodeList) executeXmlQuery(document, "//firma", XPathConstants.NODESET);
		for(int i = 0; i < firms.getLength(); i++) {
			if(Integer.parseInt(firms.item(i).getAttributes().getNamedItem("id").getTextContent()) > maxIndex) {
				maxIndex = Integer.parseInt(firms.item(i).getAttributes().getNamedItem("id").getTextContent());
			}
		}
		return maxIndex;
	}
	
	public static int getLastRigIndex(Document document) {
		int maxIndex = 0;
		NodeList rigs = (NodeList) executeXmlQuery(document, "//instalatie", XPathConstants.NODESET);
		for(int i = 0; i < rigs.getLength(); i++) {
			if(Integer.parseInt(rigs.item(i).getAttributes().getNamedItem("id").getTextContent()) > maxIndex) {
				maxIndex = Integer.parseInt(rigs.item(i).getAttributes().getNamedItem("id").getTextContent());
			}
		}
		return maxIndex;
	}
}
