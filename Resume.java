import java.io.Serializable;
import java.util.*;

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
        if (skills.isEmpty()) {
            return "No skills listed. Please add your skills to get job suggestions.";
        }

        HashMap<String, List<String>> jobCategories = new HashMap<>();
        jobCategories.put("Software Development", Arrays.asList("Java", "Python", "C++", "JavaScript", "SQL", "Git"));
        jobCategories.put("Data Science", Arrays.asList("Python", "R", "SQL", "Machine Learning", "Statistics", "Data Analysis"));
        jobCategories.put("Web Development", Arrays.asList("HTML", "CSS", "JavaScript", "React", "Node.js", "PHP"));
        jobCategories.put("Mobile Development", Arrays.asList("Java", "Kotlin", "Swift", "React Native", "Flutter"));
        jobCategories.put("DevOps", Arrays.asList("Linux", "Docker", "Kubernetes", "AWS", "CI/CD", "Python"));
        jobCategories.put("Cybersecurity", Arrays.asList("Network Security", "Python", "Linux", "Encryption", "Ethical Hacking"));

        StringBuilder suggestions = new StringBuilder();
        suggestions.append("Based on your skills, you might be suitable for:\n\n");

        for (Map.Entry<String, List<String>> entry : jobCategories.entrySet()) {
            String job = entry.getKey();
            List<String> requiredSkills = entry.getValue();
            
            long matchingSkills = skills.stream()
                .map(String::toLowerCase)
                .filter(skill -> requiredSkills.stream()
                    .anyMatch(req -> req.toLowerCase().contains(skill) || skill.contains(req.toLowerCase())))
                .count();

            if (matchingSkills >= 2) {
                double matchPercentage = (matchingSkills * 100.0) / requiredSkills.size();
                suggestions.append("• ").append(job)
                          .append(" (Match: ").append(String.format("%.1f", matchPercentage)).append("%)\n");
            }
        }

        if (suggestions.toString().equals("Based on your skills, you might be suitable for:\n\n")) {
            suggestions.append("No specific job matches found. Consider adding more diverse skills.");
        }

        return suggestions.toString();
    }
}