package hepl.sysdys2020.cart.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    public List<CartItems> getAllCart(){
        List<CartItems> cartItemsList = new ArrayList<>();
        cartRepository.findAll().forEach((cartItemsList::add));
        return cartItemsList;
    }

    public ListCartItems getCartbyId(int id) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:testdbcart", "root", "root");
            PreparedStatement statement = connection.prepareStatement(
                    "Select * from Cart_Items where id_Client = ? and is_Virtual = true");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            List<CartItems> cartItemsList = new ArrayList<>();
            while (rs.next()) {
                cartItemsList.add(
                        new CartItems(
                                rs.getInt("id_General"),
                                rs.getInt("id_Cart"),
                                rs.getInt("id_Client"),
                                rs.getInt("id_Produit"),
                                rs.getInt("quantite"),
                                rs.getBoolean("is_Virtual")));
            }
            ListCartItems listCartItems = new ListCartItems();
            listCartItems.setList(cartItemsList);
            return listCartItems;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public void SaveOrUpdate(CartItems cartItems){
        cartRepository.save(cartItems);
    }

    public void delete(int id){
        cartRepository.deleteById(id);
    }

    public int getLastId(){
        int i=0;
        boolean test = true;
        while(test == true){
            i++;
            test = cartRepository.existsById(i);
        }
        return i;
    }

    public CartItems getCart(int idcart, int idclient, int idproduit){
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection("jdbc:h2:mem:testdbcart", "root", "root");
            PreparedStatement statement = connection.prepareStatement(
                    "Select * from Cart_Items where id_Client = ? " +
                            "and id_Cart = ? " +
                            "and id_Produit = ? " +
                            "and is_Virtual = true");
            statement.setInt(1, idclient);
            statement.setInt(2, idcart);
            statement.setInt(3, idproduit);
            ResultSet rs = statement.executeQuery();
            CartItems cart = null;
            while (rs.next())
            {
                cart = new CartItems(
                        rs.getInt("id_General"),
                        rs.getInt("id_Cart"),
                        rs.getInt("id_Client"),
                        rs.getInt("id_Produit"),
                        rs.getInt("quantite"),
                        rs.getBoolean("is_Virtual"));
            }
            return cart;
        } catch (SQLException throwables) {
        throwables.printStackTrace();
        return null;
        }
    }

    public boolean deleteCartById(int idcart, int idclient, int idproduit){
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection("jdbc:h2:mem:testdbcart", "root", "root");
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE from Cart_Items where id_Client = ? " +
                            "and id_Cart = ? " +
                            "and id_Produit = ? " +
                            "and is_Virtual = true");
            statement.setInt(1, idclient);
            statement.setInt(2, idcart);
            statement.setInt(3, idproduit);
            statement.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    public ListCartItems getCartbyIdClientAndIdCart(int idclient, int idcart) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:testdbcart", "root", "root");
            PreparedStatement statement = connection.prepareStatement(
                    "Select * from Cart_Items where id_Client = ? and id_cart = ?");
            statement.setInt(1, idclient);
            statement.setInt(2, idcart);
            ResultSet rs = statement.executeQuery();
            List<CartItems> cartItemsList = new ArrayList<>();
            while (rs.next()) {
                cartItemsList.add(
                        new CartItems(
                                rs.getInt("id_General"),
                                rs.getInt("id_Cart"),
                                rs.getInt("id_Client"),
                                rs.getInt("id_Produit"),
                                rs.getInt("quantite"),
                                rs.getBoolean("is_Virtual")));
            }
            ListCartItems listCartItems = new ListCartItems();
            listCartItems.setList(cartItemsList);
            return listCartItems;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

}
