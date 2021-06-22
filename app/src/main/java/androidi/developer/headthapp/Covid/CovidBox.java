package androidi.developer.headthapp.Covid;

public class CovidBox {
    String name,blood,age,mobile,city,pin;

    public CovidBox(String name, String blood, String age, String mobile, String city, String pin) {
        this.name = name;
        this.blood = blood;
        this.age = age;
        this.mobile = mobile;
        this.city = city;
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public String getBlood() {
        return blood;
    }

    public String getAge() {
        return age;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCity() {
        return city;
    }

    public String getPin() {
        return pin;
    }
}
