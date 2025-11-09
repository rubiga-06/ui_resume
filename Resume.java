// your existing imports
import java.io.FileReader;
import java.io.Serializable;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

class Resume implements Serializable {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String linkedIn;
    private String summary;
    private List<Education> education;
    private List<Experience> experience;
    private List<String> skills;

    public Resume() {
        this.education = new ArrayList<>();
        this.experience = new ArrayList<>();
        this.skills = new ArrayList<>();
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getLinkedIn() { return linkedIn; }
    public void setLinkedIn(String linkedIn) { this.linkedIn = linkedIn; }
    
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    
    public List<Education> getEducation() { return education; }
    public void addEducation(Education edu) { education.add(edu); }
    
    public List<Experience> getExperience() { return experience; }
    public void addExperience(Experience exp) { experience.add(exp); }
    
    public List<String> getSkills() { return skills; }
    public void addSkill(String skill) { skills.add(skill); }

    public String generateResumeText() {
        StringBuilder sb = new StringBuilder();
        sb.append("RESUME\n");
        sb.append("======\n\n");
        
        sb.append("Personal Information:\n");
        sb.append("---------------------\n");
        sb.append("Name: ").append(fullName != null ? fullName : "").append("\n");
        sb.append("Email: ").append(email != null ? email : "").append("\n");
        sb.append("Phone: ").append(phone != null ? phone : "").append("\n");
        sb.append("Address: ").append(address != null ? address : "").append("\n");
        sb.append("LinkedIn: ").append(linkedIn != null ? linkedIn : "").append("\n");
        sb.append("Summary: ").append(summary != null ? summary : "").append("\n\n");
        
        sb.append("Education:\n");
        sb.append("----------\n");
        for (Education edu : education) {
            sb.append(edu).append("\n");
        }
        sb.append("\n");
        
        sb.append("Experience:\n");
        sb.append("-----------\n");
        for (Experience exp : experience) {
            sb.append(exp).append("\n");
        }
        sb.append("\n");
        
        sb.append("Skills:\n");
        sb.append("-------\n");
        for (String skill : skills) {
            sb.append("• ").append(skill).append("\n");
        }
        
        return sb.toString();
    }

    public String suggestJobs() {
        if (skills == null || skills.isEmpty()) {
            return "No skills listed. Please add your skills to get job suggestions.";
        }

        StringBuilder suggestions = new StringBuilder();
        suggestions.append("🔍 Based on your skills, you might be suitable for:\n\n");

        try {
            JSONParser parser = new JSONParser();
            JSONArray jobsArray = (JSONArray) parser.parse(new FileReader("data/jobs.json"));

            for (Object obj : jobsArray) {
                JSONObject job = (JSONObject) obj;
                String title = (String) job.get("title");
                String company = (String) job.get("company");
                String location = (String) job.get("location");
                JSONArray jobSkills = (JSONArray) job.get("skills");

                long matchingSkills = skills.stream()
                        .map(String::toLowerCase)
                        .filter(skill -> jobSkills.stream()
                                .anyMatch(js -> js.toString().toLowerCase().contains(skill)
                                        || skill.contains(js.toString().toLowerCase())))
                        .count();

                if (matchingSkills >= 2) {
                    double matchPercentage = (matchingSkills * 100.0) / jobSkills.size();
                    suggestions.append("• ")
                               .append(title)
                               .append(" at ")
                               .append(company)
                               .append(" (")
                               .append(location)
                               .append(") — Match: ")
                               .append(String.format("%.1f", matchPercentage))
                               .append("%\n");
                }
            }
        } catch (Exception e) {
            return "⚠️ Error loading job suggestions: " + e.getMessage();
        }

        if (suggestions.toString().equals("🔍 Based on your skills, you might be suitable for:\n\n")) {
            suggestions.append("No specific job matches found. Try adding more diverse skills.");
        }

        return suggestions.toString();
    }

    // ✅ Added main method without changing any of your existing code
    public static void main(String[] args) {
        Resume resume = new Resume();
        resume.setFullName("John Doe");
        resume.setEmail("john@example.com");
        resume.setPhone("1234567890");
        resume.setAddress("123 Main St, City");
        resume.setLinkedIn("linkedin.com/in/johndoe");
        resume.setSummary("Motivated software developer seeking challenging roles.");

        // Example Education & Experience
        resume.addEducation(new Education("B.Tech", "Computer Science", "XYZ University", "2020"));
        resume.addExperience(new Experience("Intern", "ABC Company", "Worked on Java projects", "2022"));

        // Example skills
        resume.addSkill("Java");
        resume.addSkill("Python");
        resume.addSkill("SQL");

        // Print resume
        System.out.println(resume.generateResumeText());

        // Print job suggestions
        System.out.println(resume.suggestJobs());
    }
}
