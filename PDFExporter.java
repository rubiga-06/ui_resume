import java.awt.*;
import java.awt.print.*;
import javax.swing.*;

public class PDFExporter {
    public static void exportResumeToPDF(Resume resume, String filePath) throws Exception {
        PrintableResume printableResume = new PrintableResume(resume);
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

class PrintableResume implements Printable {
    private Resume resume;

    public PrintableResume(Resume resume) {
        this.resume = resume;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) return NO_SUCH_PAGE;

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = (int) pageFormat.getImageableWidth();
        int y = 60;

        // === HEADER BACKGROUND ===
        g2d.setColor(new Color(33, 97, 140)); // Deep Blue
        g2d.fillRect(0, 0, width, 80);

        // === NAME ===
        g2d.setFont(new Font("SansSerif", Font.BOLD, 26));
        g2d.setColor(Color.WHITE);
        g2d.drawString(resume.getFullName().toUpperCase(), 50, 45);

        // === CONTACT INFO ===
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g2d.drawString(resume.getEmail() + " | " + resume.getPhone(), 50, 65);
        g2d.drawString(resume.getLinkedIn() != null ? resume.getLinkedIn() : "", 300, 65);

        y = 110;

      
        if (resume.getSummary() != null && !resume.getSummary().isEmpty()) {
            y = drawSectionTitle(g2d, "OBJECTIVE", y);
            y = drawMultilineText(g2d, resume.getSummary(), 50, y + 10, 70);
        }

       
        if (!resume.getEducation().isEmpty()) {
            y = drawSectionTitle(g2d, "EDUCATION", y + 15);
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2d.setColor(Color.DARK_GRAY); // ✅ Make text visible and consistent
            for (Education edu : resume.getEducation()) {
                 g2d.drawString("• " + edu.toString(), 60, y += 18);
    }
}

        // === EXPERIENCE ===
        if (!resume.getExperience().isEmpty()) {
            y = drawSectionTitle(g2d, "EXPERIENCE", y + 25);
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
            for (Experience exp : resume.getExperience()) {
                y = drawMultilineText(g2d, "• " + exp.toString(), 60, y + 10, 75);
            }
        }

        // === SKILLS ===
        // === SKILLS ===
        if (!resume.getSkills().isEmpty()) {
            y = drawSectionTitle(g2d, "SKILLS", y + 15);
    
            g2d.setColor(Color.DARK_GRAY); // ✅ Reset color to dark gray for visibility
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
    
        int x = 60;
        int count = 0;
        for (String skill : resume.getSkills()) {
            g2d.drawString("• " + skill, x, y += 18);
            count++;
            if (count % 2 == 0) {
                 x = 60;
             } else {
                x = 300;
                y -= 18;
        }
    }
}

        // === FOOTER LINE ===
        g2d.setColor(new Color(180, 180, 180));
        g2d.drawLine(50, (int) pageFormat.getImageableHeight() - 30, width - 50, (int) pageFormat.getImageableHeight() - 30);
        g2d.setFont(new Font("SansSerif", Font.ITALIC, 10));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("Generated using Java Resume Builder © 2025", 50, (int) pageFormat.getImageableHeight() - 15);

        return PAGE_EXISTS;
    }

    // Draw colored section title
    private int drawSectionTitle(Graphics2D g2d, String title, int y) {
        g2d.setColor(new Color(33, 97, 140)); // blue
        g2d.setFont(new Font("SansSerif", Font.BOLD, 15));
        g2d.drawString(title, 50, y);
        g2d.setColor(new Color(200, 200, 200));
        g2d.drawLine(50, y + 3, 550, y + 3);
        return y + 20;
    }

    // Draw long text properly wrapped
    private int drawMultilineText(Graphics2D g2d, String text, int x, int y, int maxCharsPerLine) {
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g2d.setColor(Color.DARK_GRAY);
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            if (line.length() + word.length() > maxCharsPerLine) {
                g2d.drawString(line.toString(), x, y);
                y += 15;
                line = new StringBuilder();
            }
            line.append(word).append(" ");
        }
        g2d.drawString(line.toString(), x, y);
        return y + 10;
    }
}