package hepl.sysdys2020.server.Model.Model;

import java.util.ArrayList;
import java.util.List;

public class PanierList {
    private List<Panier> list;
    private double prixtotal;

    public PanierList(List<Panier> list, double prixtotal) {
        this.list = list;
        this.prixtotal = prixtotal;
    }

    public double getPrixtotal() {
        return prixtotal;
    }

    public void setPrixtotal(double prixtotal) {
        this.prixtotal = prixtotal;
    }

    public PanierList() {
        list = new ArrayList<>();
    }

    public PanierList(List<Panier> list) {
        this.list = list;
    }

    public List<Panier> getList() {
        return list;
    }

    public void setList(List<Panier> list) {
        this.list = list;
    }
}
