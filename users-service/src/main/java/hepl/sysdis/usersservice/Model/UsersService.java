package hepl.sysdis.usersservice.Model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    UserRepository ur;


    /// get all user of the db
    public List<Users> getAllUsers() {
        List<Users> userslist = new ArrayList<>();
        ur.findAll().forEach((userslist::add));
        return userslist;
    }

    /// get id for an user
    public int GetIdByNameANDpwdUser(String name, String pwd) {
        int currentid = 0;
        List<Users> cartItemsList = new ArrayList<>();
        List<Users> u = ur.findAll();

        for (Users x : u) {
            if (x.getPassword().equals(pwd) && x.getNom().equals(name)) {
                currentid = x.getIdusers();
            }

        }
        return currentid;
    }

    // do you knwo this user
    public int Exist(Users user) {
        int currentid = 0;
        List<Users> cartItemsList = new ArrayList<>();
        List<Users> u = ur.findAll();
        Users finduser= new Users();

        for (Users x : u) {
            if (x.getPassword().equals(user.getPassword()) && x.getNom().equals(user.getNom())) {
                finduser = x;

            }

        }

        int disbaleactive = this.GetActive();
        Optional<Users> useractive;
        useractive = ur.findById(disbaleactive);
        useractive.get().setActive(false);
        ur.save(useractive.get());

        finduser.setActive(true);
        ur.save(finduser);

            return finduser.getIdusers();
    }

    /// already exist return true if not save
    public boolean SaveOrUpdate(Users users) {
        List<Users> u = ur.findAll();
        boolean already = false;

        for (Users x : u) {
            if (x.getNom().equals(users.getNom())) {
                already = true;
            }
        }
        if (already == false) {
            ur.save(users);
            already = false;
        }
        return already;
    }

    public int GetActive() {
        List<Users> u = ur.findAll();
        int active = -1;
        Users users = new Users();

        for (Users x : u) {
            if (x.isActive() == true) {
                active = x.getIdusers();
                users = x;
            }
        }
        if (active != -1) {
            users.setActive(true);
            ur.save(users);

        } else {
            Optional<Users> user;
            user = ur.findById(-1);
            user.get().setActive(true);
            ur.save(user.get());
        }
        return active;
    }
}
