package listeners;
import java.io.ByteArrayOutputStream;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import cmis.CMIShelper;

public class InvoiceProcessingListener implements ExecutionListener{

	@Override
	public void notify(DelegateExecution execution) {
		// TODO Auto-generated method stub
        long quantity= (Long) execution.getVariable("laptopQuantity");
        double price=Double.parseDouble((String) execution.getVariable("productPrice"));
		com.itextpdf.text.Document pdf = new com.itextpdf.text.Document();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
		PdfWriter writer;
		try {
			writer = PdfWriter.getInstance(pdf, outputStream);
		
		writer.setBoxSize("art", new Rectangle(36, 54,559,788));
        HeaderFooter event = new HeaderFooter();
        writer.setPageEvent(event);
       pdf.addAuthor("Loan Sharks");
       pdf.addTitle("Subject: loan request");
       pdf.addSubject("Reference number 100898");
       pdf.open();
       //URL imageUrl = PDFLetterTask.class.getClassLoader().getResource("chapter13/cmis/tshark3.gif");
		//Image image = Image.getInstance(imageUrl);
		//pdf.add(image);
		pdf.add(new Paragraph(" "+quantity));
		pdf.add(new Paragraph(" "+price));
		pdf.add(new Paragraph(" "));
		//pdf.add(new Paragraph("Dear Mr/Mrs " + loanApplication.getApplicant().getName() + ","));
		pdf.add(new Paragraph(" "));
		
		/*if("approved".equalsIgnoreCase(loanApplication.getStatus()) ||
				"approved by manager".equalsIgnoreCase(loanApplication.getStatus())) {
			
			pdf.add(new Paragraph("After analysis regarding your loan request we are happy to inform you that your loan request for $"
					+ loanApplication.getApplicant().getLoanAmount() + " is approved. Enclosed, you'll find all the details regarding the next steps in the process of your loan request."));
		} else {
			pdf.add(new Paragraph("After analysis regarding your loan request we regret to inform you that your loan request for $"
					+ loanApplication.getApplicant().getLoanAmount() + " is denied."));
		}*/
		pdf.add(new Paragraph(" "));
		pdf.add(new Paragraph(" "));
		pdf.add(new Paragraph(" "));
		pdf.add(new Paragraph("With regards,"));
		pdf.add(new Paragraph(" "));
		pdf.add(new Paragraph(" "));
		pdf.add(new Paragraph("John Shark"));
		pdf.add(new Paragraph("Manager Loan Sharks"));
		pdf.close();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    CMIShelper helper = new CMIShelper();
		helper.createCmisSession();
        helper.saveDocumentToFolder(outputStream,"a84bd722-01bb-4b5b-b8af-4df86295756c",(String) execution.getVariable("clientName"), ".pdf", "application/pdf");
	}
	
	/** Inner class to add a header and a footer. */
  static class HeaderFooter extends PdfPageEventHelper {

    public void onEndPage (PdfWriter writer, Document document) {
      Rectangle rect = writer.getBoxSize("art");
      ColumnText.showTextAligned(writer.getDirectContent(),
              Element.ALIGN_RIGHT, new Phrase("Loan Sharks"),
              rect.getRight(), rect.getTop(), 0);
      
      ColumnText.showTextAligned(writer.getDirectContent(),
              Element.ALIGN_RIGHT, new Phrase("4543 1st Street"),
              rect.getRight(), rect.getTop() - 15, 0);
      
      ColumnText.showTextAligned(writer.getDirectContent(),
              Element.ALIGN_RIGHT, new Phrase("Bay City, 38989 "),
              rect.getRight(), rect.getTop() - 30, 0);
      
      ColumnText.showTextAligned(writer.getDirectContent(),
              Element.ALIGN_RIGHT, new Phrase("E-mail: entreprise@email.com"),
              rect.getRight(), rect.getTop() - 60, 0);
    }
  }

	
		
	}
	 
		  
	  
	  

	  