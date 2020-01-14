package app.test.wyp;

public class User {

    String name;
    String genre;
    Number phone;

    public User(){

    }

    public User(String name){
        this.name = name;
    }

    public User(String name, String genre, Number phone){
        this.name = name;
        this.genre = genre;
        this.phone = phone;
    }

    public String getName(){
        return name;
    }

    public String getGenre(){
        return genre;
    }

    public Number getPhone(){
        return phone;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setGenre(String genre){
        this.genre= genre;
    }

    public void setPhone(Number phone){
        this.phone = phone;
    }

}
