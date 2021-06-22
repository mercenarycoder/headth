package androidi.developer.headthapp.Qr;

public class qrBase {
    String title,description,id;

    public qrBase(String title, String description, String id) {
        this.title = title;
        this.description = description;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }
}
