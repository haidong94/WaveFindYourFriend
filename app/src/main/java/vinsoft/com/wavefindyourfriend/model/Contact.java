package vinsoft.com.wavefindyourfriend.model;

/**
 * Created by DONG on 31-Mar-17.
 */

public class Contact {
    String name;
    String phone;

    public Contact() {
    }

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
