package vinsoft.com.wavefindyourfriend.model;

/**
 * Created by DONG on 28-Mar-17.
 */

public class Person {
    int id;
    String name;
    String pass;
    String sex;
    String avatar;

    public String getImage() {
        return avatar;
    }

    public void setImage(String image) {
        this.avatar = image;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }




    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
