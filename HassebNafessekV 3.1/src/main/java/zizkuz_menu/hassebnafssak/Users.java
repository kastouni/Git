package zizkuz_menu.hassebnafssak;

/**
 * Created by zouheir_kastouni on 18/02/2016.
 */
public class Users {

    private String Nom;
    private String email;
    private String Telephone;

    public Users (String name, String emails, String Tel){
        this.Nom=name;
        this.email=emails;
        this.Telephone=Tel;

    }
    public  String getEmail(Users user) {
        return user.email;
    }

    public  String getNom(Users user) {
        return user.Nom;
    }

    public  String  getTelephone(Users user) {
        return user.Telephone;
    }


    public  void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }
}
