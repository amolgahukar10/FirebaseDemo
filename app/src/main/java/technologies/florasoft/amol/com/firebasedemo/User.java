package technologies.florasoft.amol.com.firebasedemo;

public class User {

    String name;
    String id ;
    String androidDeviceId;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAndroidDeviceId() {
        return androidDeviceId;
    }

    public User(){

    }

    public User(String name, String id, String androidDeviceId) {
        this.name = name;
        this.id = id;
        this.androidDeviceId = androidDeviceId;
    }



}
