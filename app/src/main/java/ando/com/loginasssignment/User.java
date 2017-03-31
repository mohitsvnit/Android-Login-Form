package ando.com.loginasssignment;

/**
 * Created by mohit on 28/3/17.
 */

public class User {
    private int id;
    private String fname;
    private String lname;
    private String email;
    private String pass;
    private String bDate;
    private String hobbies;
    private String gender;

    public User(){

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getbDate() {
        return bDate;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getHobbies() {
        return hobbies;
    }

    public String getLname() {
        return lname;
    }

    public String getPass() {
        return pass;
    }

}
