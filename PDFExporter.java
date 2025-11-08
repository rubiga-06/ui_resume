import java.awt.print.*;
import javax.swing.*;

public class PDFExporter {
    public static void exportResumeToPDF(Resume resume, String filePath, String template) throws Exception {
        Printable printableResume;

        // ✅ Switch based on selected template
        switch (template) {
            case "Modern":
                printableResume = new PrintableResumeModern(resume);
                break;
            case "Creative":
                printableResume = new PrintableResumeCreative(resume);
                break;
            default:
                printableResume = new PrintableResumeClassic(resume);
                break;
        }

        // === Print Logic (same as yours)
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(printableResume);

        if (job.printDialog()) {
            JOptionPane.showMessageDialog(null,
                "Select 'Microsoft Print to PDF' or any PDF printer and save to:\n" + filePath,
                "Print to PDF", JOptionPane.INFORMATION_MESSAGE);
            job.print();
        }
    }
}

