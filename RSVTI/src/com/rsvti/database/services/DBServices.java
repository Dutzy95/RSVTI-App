package com.rsvti.database.services;

import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.rsvti.database.entities.Administrator;
import com.rsvti.database.entities.Firm;
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
}
