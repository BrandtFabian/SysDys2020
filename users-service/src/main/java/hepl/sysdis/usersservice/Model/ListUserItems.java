package hepl.sysdis.usersservice.Model;

import java.util.ArrayList;
import java.util.List;

public class ListUserItems {
    private List<Users> list;

    public ListUserItems() {
        this.list = new ArrayList<Users>();
    }

    public List<Users> getList() {
        return list;
    }

    public void setList(List<Users> list) {
        this.list = list;
    }

    public ListUserItems(List<Users> list) {
        this.list = list;
    }
}
