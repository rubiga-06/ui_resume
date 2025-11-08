import java.io.Serializable;

class Education implements Serializable {
    private String degree;
    private String institution;
    private String year;
    private String gpa;

    public Education(String degree, String institution, String year, String gpa) {
        this.degree = degree;
        this.institution = institution;
        this.year = year;
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return degree + " | " + institution + " | " + year + " | GPA: " + gpa;
    }
}