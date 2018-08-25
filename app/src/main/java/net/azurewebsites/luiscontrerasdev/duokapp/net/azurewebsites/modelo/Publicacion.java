package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.modelo;

public class Publicacion {
    private String userId;
    private String userName;
    private String userEmail;
    private String userFotoPerfil;
    private String userUbicacion;
    private String urlFoto;
    private String descripcion;

    public Publicacion(String userId, String userName, String userEmail, String userFotoPerfil, String userUbicacion, String urlFoto, String descripcion) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userFotoPerfil = userFotoPerfil;
        this.userUbicacion = userUbicacion;
        this.urlFoto = urlFoto;
        this.descripcion = descripcion;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserUbicacion() {
        return userUbicacion;
    }

    public void setUserUbicacion(String userUbicacion) {
        this.userUbicacion = userUbicacion;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUserFotoPerfil() {
        return userFotoPerfil;
    }

    public void setUserFotoPerfil(String userFotoPerfil) {
        this.userFotoPerfil = userFotoPerfil;
    }
}
