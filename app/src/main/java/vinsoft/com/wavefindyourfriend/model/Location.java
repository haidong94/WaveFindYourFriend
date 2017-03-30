package vinsoft.com.wavefindyourfriend.model;

/**
 * Created by DONG on 28-Mar-17.
 */

public class Location {
    int id;
    float latitude;
    float longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
