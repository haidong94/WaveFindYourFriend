package vinsoft.com.wavefindyourfriend.model;

/**
 * Created by DONG on 28-Mar-17.
 */

public class Person {
    String id;
    String name;
    String pass;
    String sex;
    String avatar;

    public Person() {
    }

    public Person(String id, String name){
        this.id=id;
        this.name=name;
    }

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



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
