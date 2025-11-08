import java.io.Serializable;

class Experience implements Serializable {
    private String company;
    private String position;
    private String duration;
    private String description;

    public Experience(String company, String position, String duration, String description) {
        this.company = company;
        this.position = position;
        this.duration = duration;
        this.description = description;
    }

    @Override
    public String toString() {
        return position + " at " + company + " (" + duration + ")\n" + description;
    }
}