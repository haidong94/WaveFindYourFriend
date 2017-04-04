package vinsoft.com.wavefindyourfriend.model;

/**
 * Created by DONG on 04-Apr-17.
 */

public class ZonePhone {
    private String countryName;
    private String countryPhone;

    public ZonePhone() {
    }

    public ZonePhone(String countryName, String countryPhone) {
        this.countryName = countryName;
        this.countryPhone = countryPhone;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryPhone() {
        return countryPhone;
    }

    public void setCountryPhone(String countryPhone) {
        this.countryPhone = countryPhone;
    }
}
