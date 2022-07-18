package Helper;

import java.io.File;

import javax.lang.model.element.Element;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import org.w3c.dom.*;

public class XmlHelper {
	public static void xmlConn(String customerNameValueT,String customerssnNumberValueT,Object[][] items,
			String totalAmountValueT,
			String discountValueT,String amountToPayValueT) {
		String path="sampleoutput.xml";
		File f=new File(path);
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder=dbf.newDocumentBuilder();
			Document doc=builder.newDocument();
			org.w3c.dom.Element uploadSystem= doc.createElement("uploadSystem");
			
			org.w3c.dom.Element customer=doc.createElement("customer");
			org.w3c.dom.Element customerName= doc.createElement("name");
			Text customerNameValue=doc.createTextNode(customerNameValueT);
			customerName.appendChild(customerNameValue);
			
			org.w3c.dom.Element customerssnNumber=doc.createElement("ssnNumber");
			Text customerssnNumberValue=doc.createTextNode(customerssnNumberValueT);
			customerssnNumber.appendChild(customerssnNumberValue);
			customer.appendChild(customerName);
			customer.appendChild(customerssnNumber);
			
			org.w3c.dom.Element invoiceData=doc.createElement("invoiceData");
			
			for(int i=0;i<items.length;i++) {
				
				org.w3c.dom.Element item= doc.createElement("item");
				
				org.w3c.dom.Element name= doc.createElement("name");
				Text nameValue=doc.createTextNode(items[i][0].toString());
				name.appendChild(nameValue);
				
				org.w3c.dom.Element quantity= doc.createElement("quantity");
				Text quantityValue=doc.createTextNode(items[i][1].toString());
				quantity.appendChild(quantityValue);
				
				org.w3c.dom.Element unitPrice= doc.createElement("unitPrice");
				Text unitPriceValue=doc.createTextNode(items[i][2].toString());
				unitPrice.appendChild(unitPriceValue);
				
				org.w3c.dom.Element amount= doc.createElement("Amount");
				Text amountValue=doc.createTextNode(items[i][3].toString());
				amount.appendChild(amountValue);
				
				item.appendChild(name);
				item.appendChild(quantity);
				item.appendChild(unitPrice);
				item.appendChild(amount);
				
				invoiceData.appendChild(item);
			}
			
			
			
			
			org.w3c.dom.Element totalAmount= doc.createElement("totalAmount");
			Text totalAmountValue=doc.createTextNode(totalAmountValueT);
			totalAmount.appendChild(totalAmountValue);
			
			org.w3c.dom.Element discount= doc.createElement("discount");
			Text discountValue=doc.createTextNode(discountValueT);
			discount.appendChild(discountValue);
			
			org.w3c.dom.Element amountToPay= doc.createElement("amountToPay");
			Text amountToPayValue=doc.createTextNode(amountToPayValueT);
			amountToPay.appendChild(amountToPayValue);
			
			invoiceData.appendChild(totalAmount);
			invoiceData.appendChild(discount);
			invoiceData.appendChild(amountToPay);
			
			uploadSystem.appendChild(customer);
			uploadSystem.appendChild(invoiceData);
			doc.appendChild(uploadSystem);
			DOMSource source=new DOMSource(doc);
			StreamResult rs=new StreamResult(f);
			TransformerFactory transformerFactory=TransformerFactory.newInstance();
			javax.xml.transform.Transformer transformer;
			try {
				transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.transform(source, rs);
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
				
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Inserted to Xml File", "Inserted", JOptionPane.INFORMATION_MESSAGE);
	}
	
	

}