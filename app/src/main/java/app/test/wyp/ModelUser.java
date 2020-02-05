package app.test.wyp;

public class ModelUser {
    private String name;
    private int status;
    private String phone;
    private String genre;

    public ModelUser(String phone, String name, String genre, int status) {
        this.phone = phone;
        this.name = name;
        this.genre = genre;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public String getGenre() { return genre; }

    public String getPhone() { return phone; }

}
