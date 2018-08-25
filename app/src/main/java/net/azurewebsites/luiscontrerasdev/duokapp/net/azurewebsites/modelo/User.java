package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.modelo;

public class User {
    private String name;
    private String email;
    private String foto;

    public User(String name, String email, String foto){
        this.name = name;
        this.email = email;
        this.foto = foto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
