package Models;

/**
 * Created by Dost Muhammad on 5/8/2018.
 */
public class Person {
    private  String username, email, faceid, faceimage;

    public Person() {
    }

    public Person(String username, String email, String faceid, String faceimage) {
        this.username = username;
        this.email = email;
        this.faceid = faceid;
        this.faceimage = faceimage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFaceid() {
        return faceid;
    }

    public void setFaceid(String faceid) {
        this.faceid = faceid;
    }

    public String getFaceimage() {
        return faceimage;
    }

    public void setFaceimage(String faceimage) {
        this.faceimage = faceimage;
    }
}
