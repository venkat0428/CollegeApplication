package com.example.navigationbtm.ui.faculty;

public class FacultyData {
    String image,name,qualification,mailId,post,department,key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public FacultyData(String image, String name, String qualification, String mailId, String post, String department, String key) {
        this.image = image;
        this.name = name;
        this.qualification = qualification;
        this.mailId = mailId;
        this.post = post;
        this.department=department;
        this.key = key;
    }

    public FacultyData() {
    }
}
