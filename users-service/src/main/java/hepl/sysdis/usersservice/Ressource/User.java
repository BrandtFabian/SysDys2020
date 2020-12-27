package hepl.sysdis.usersservice.Ressource;


import com.netflix.discovery.DiscoveryClient;
import hepl.sysdis.usersservice.Model.Users;
import hepl.sysdis.usersservice.Model.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/users")
public class User {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    UsersService usersService;

    private DiscoveryClient discoveryClient;

    @RequestMapping("/all")
    public List<Users> getAllUsers()
    {
        return usersService.getAllUsers();
    }

    @RequestMapping("/{name}/{pwd}")
    public int GetId(@PathVariable("name") String name, @PathVariable("pwd") String pwd){
        return usersService.GetIdByNameANDpwdUser(name,pwd);
    }
    // do you know this user
    @PostMapping("/exist")
    public int GetId(@RequestBody Users user){
         int r=usersService.Exist(user);
        return r;
    }
    @RequestMapping("/getactive")
    public int GetId(){
        int re= usersService.GetActive();
        return re;
    }

    @PostMapping("add/user")
    public boolean AddUser(@RequestBody Users user)
    {
        boolean ok= usersService.SaveOrUpdate(user);
        if(ok==false)
        {
            return true;
        }
        return ok;
    }


}
