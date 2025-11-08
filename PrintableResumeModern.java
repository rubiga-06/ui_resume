import java.awt.*;
import java.awt.print.*;

public class PrintableResumeModern implements Printable {
    private Resume resume;

    public PrintableResumeModern(Resume resume) {
        this.resume = resume;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) return NO_SUCH_PAGE;

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int y = 80;
        int width = (int) pageFormat.getImageableWidth();

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 24));
        g2d.drawString(resume.getFullName(), 50, y);

        g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
        y += 20;
        g2d.drawString(resume.getEmail() + " | " + resume.getPhone(), 50, y);
        y += 30;

        g2d.setColor(new Color(0, 102, 204));
        g2d.drawLine(50, y, width - 50, y);
        y += 30;

        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2d.drawString("Objective", 50, y);
        y += 20;
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
        y = drawWrappedText(g2d, resume.getSummary(), 50, y, 70);

        y = drawSection(g2d, "Education", resume.getEducation().toString(), y + 20);
        y = drawSection(g2d, "Experience", resume.getExperience().toString(), y + 20);
        y = drawSection(g2d, "Skills", String.join(", ", resume.getSkills()), y + 20);

        return PAGE_EXISTS;
    }

    private int drawWrappedText(Graphics2D g2d, String text, int x, int y, int maxChars) {
        if (text == null) return y;
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            if (line.length() + word.length() > maxChars) {
                g2d.drawString(line.toString(), x, y);
                y += 15;
                line = new StringBuilder();
            }
            line.append(word).append(" ");
        }
        g2d.drawString(line.toString(), x, y);
        return y + 10;
    }

    private int drawSection(Graphics2D g2d, String title, String content, int y) {
        g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2d.setColor(new Color(0, 102, 204));
        g2d.drawString(title, 50, y);
        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
        y = drawWrappedText(g2d, content, 60, y + 15, 80);
        return y;
    }
}
