import java.awt.*;
import java.awt.print.*;

public class PrintableResumeCreative implements Printable {
    private Resume resume;

    public PrintableResumeCreative(Resume resume) {
        this.resume = resume;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) return NO_SUCH_PAGE;

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = (int) pageFormat.getImageableWidth();
        int height = (int) pageFormat.getImageableHeight();

        // Sidebar
        g2d.setColor(new Color(52, 73, 94));
        g2d.fillRect(0, 0, 180, height);

        // Name in sidebar
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 18));
        g2d.drawString(resume.getFullName(), 30, 80);

        g2d.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g2d.drawString(resume.getEmail(), 30, 110);
        g2d.drawString(resume.getPhone(), 30, 125);

        // Main content area
        int x = 200;
        int y = 80;
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2d.drawString("Objective", x, y);
        y += 15;
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 11));
        y = drawWrappedText(g2d, resume.getSummary(), x, y, 70);

        y = drawSection(g2d, "Education", resume.getEducation().toString(), x, y + 20);
        y = drawSection(g2d, "Experience", resume.getExperience().toString(), x, y + 20);
        y = drawSection(g2d, "Skills", String.join(", ", resume.getSkills()), x, y + 20);

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

    private int drawSection(Graphics2D g2d, String title, String content, int x, int y) {
        g2d.setFont(new Font("SansSerif", Font.BOLD, 13));
        g2d.setColor(new Color(52, 73, 94));
        g2d.drawString(title, x, y);
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 11));
        g2d.setColor(Color.DARK_GRAY);
        y = drawWrappedText(g2d, content, x + 10, y + 15, 70);
        return y;
    }
}

