package hepl.sysdys2020.order.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class OrderItems {

    @Id
    private int idOrder; //equivaut a id caddie pour plus de facilite
    private int idClient;
    private String status;
    private double prixTotal;

    public int getIdOrder() { return idOrder; }
    public void setIdOrder(int idOrder) { this.idOrder = idOrder; }

    public int getIdClient() { return idClient; }
    public void setIdClient(int idClient) { this.idClient = idClient; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getPrixTotal() { return prixTotal; }
    public void setPrixTotal(double prixTotal) { this.prixTotal = prixTotal; }

    public OrderItems() { }
    public OrderItems(int idOrder, int idClient, String status, double prixTotal) {
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.status = status;
        this.prixTotal = prixTotal;
    }
    public OrderItems(int idClient, String status, double prixTotal) {
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.status = status;
        this.prixTotal = prixTotal;
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "idOrder=" + idOrder +
                ", idClient=" + idClient +
                ", status='" + status + '\'' +
                ", prixTotal=" + prixTotal +
                '}';
    }
}
