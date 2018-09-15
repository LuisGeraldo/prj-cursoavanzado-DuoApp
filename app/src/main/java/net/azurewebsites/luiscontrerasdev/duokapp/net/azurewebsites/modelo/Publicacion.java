package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.modelo;

public class Publicacion {
    private String userName;
    private String userEmail;
    private String userFotoPerfil;
    private String userUbicacion;
    private String urlFoto;
    private String descripcion;

    public Publicacion(){
    }

    public Publicacion(String userName, String userEmail, String userFotoPerfil, String userUbicacion, String urlFoto, String descripcion) {

        this.userName = userName;
        this.userEmail = userEmail;
        this.userFotoPerfil = userFotoPerfil;
        this.userUbicacion = userUbicacion;
        this.urlFoto = urlFoto;
        this.descripcion = descripcion;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public String getUserUbicacion() {
        return userUbicacion;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUserFotoPerfil() {
        return userFotoPerfil;
    }
}
