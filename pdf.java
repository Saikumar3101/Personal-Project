package jdbc_project;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdfparser.BaseParser;
import org.apache.fontbox.afm.AFMParser;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class pdf {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class.forName("org.apache.fontbox.afm.AFMParser");
			System.out.println("imported successfully");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
		
		try {
            // Load the PDF document using PDDocument
			String filePath = "C:\\project\\PDF_Project.pdf";
			File pdf = new File(filePath);
			System.out.println(pdf.exists());
			System.out.println(pdf.canRead());
            PDDocument document = PDDocument.load(pdf);
            System.out.println("true");

            // Create a PDFTextStripper object
            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            // Extract text from the PDF
            String pdfText = pdfTextStripper.getText(document);

            // Close the PDF document
            document.close();

            // Analyze the text to calculate credit and debit balances
            calculateBalances(pdfText);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void calculateBalances(String pdfText) {
        // Assuming a simple pattern for credit and debit transactions
        Pattern transactionPattern = Pattern.compile("Transaction: (Deposits|Withdrawals) Amount: (\\d+\\.\\d+)");
        Matcher matcher = transactionPattern.matcher(pdfText);

        double creditBalance = 0.0;
        double debitBalance = 0.0;

        while (matcher.find()) {
            String transactionType = matcher.group(1);
            double amount = Double.parseDouble(matcher.group(2));

            if ("Deposits".equals(transactionType)) {
                creditBalance += amount;
            } else if ("Withdrawals".equals(transactionType)) {
                debitBalance += amount;
            }
        }

        // Print the calculated balances
        System.out.println("Credit Balance: " + creditBalance);
        System.out.println("Debit Balance: " + debitBalance);
		
	}

}

//"C:\\project\\PDF_Project.pdf"
