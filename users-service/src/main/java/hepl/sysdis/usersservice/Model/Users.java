package hepl.sysdis.usersservice.Model;

import javax.persistence.*;

@Entity
public class Users {


    @Id
    private int idusers;
    private String name;
    private String password;
    private boolean active;



    public Users(){}




    public Users(int id,String nom,String pwd,boolean a) {
        this.idusers=id;
        this.name = nom;
        this.password=pwd;
        this.active=a;

    }



    public int getIdusers() {
        return idusers;
    }

    public void setIdusers(int idusers) {
        this.idusers = idusers;
    }

    public String getNom() {
        return name;
    }

    public void setNom(String nom) {
        this.name = nom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }



    @Override
    public String toString() {
        return "Users{" +
                "idusers=" + getIdusers() +
                ", nom=" + getNom() +"}";
    }






}
