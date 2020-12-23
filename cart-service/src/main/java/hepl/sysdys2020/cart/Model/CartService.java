package hepl.sysdys2020.cart.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
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

            List<CartItems> cartItemsList = new ArrayList<>();


            List<CartItems> cart=cartRepository.findAll();

            for (CartItems x: cart) {
                if(x.isVirtual()==true && x.getIdClient()==id) {
                    cartItemsList.add(
                            new CartItems(x.getIdGeneral(),x.getIdCart(),x.getIdClient(),x.getIdProduit(),x.getQuantite(),x.isVirtual()));};
                }
            ListCartItems returncartItemsList = new ListCartItems();
            returncartItemsList.setList(cartItemsList);
            return returncartItemsList;
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



        List<CartItems> cartItemsList = new ArrayList<>();
        CartItems returncart=null;

        List<CartItems> cart=cartRepository.findAll();

        for (CartItems x: cart) {
            if(x.getIdProduit()==idproduit && x.getIdClient()==idclient && x.getIdCart()==idcart) {
                returncart = new CartItems(x.getIdGeneral(),x.getIdCart(),x.getIdClient(),x.getIdProduit(),x.getQuantite(),x.isVirtual());};
        }

        return returncart;
    }



    public boolean deleteCartById(int idcart, int idclient, int idproduit) {


        CartItems returncart = null;
        boolean r = false;
        List<CartItems> cart = cartRepository.findAll();

        for (CartItems x : cart) {
            if (x.getIdProduit() == idproduit && x.getIdClient() == idclient && x.getIdCart() == idcart) {
                returncart = new CartItems(x.getIdGeneral(), x.getIdCart(), x.getIdClient(), x.getIdProduit(), x.getQuantite(), x.isVirtual());
                r=true;
            } ;
        }

        cartRepository.deleteById(returncart.getIdGeneral());

        return r;

    }


    public ListCartItems getCartbyIdClientAndIdCart(int idclient, int idcart) {




        List<CartItems> cartItemsList = new ArrayList<>();


        List<CartItems> cart=cartRepository.findAll();

        for (CartItems x: cart) {
            if(x.getIdCart()==idcart && x.getIdClient()==idclient) {
                cartItemsList.add(
                        new CartItems(x.getIdGeneral(),x.getIdCart(),x.getIdClient(),x.getIdProduit(),x.getQuantite(),x.isVirtual()));};
        }
        ListCartItems returncartItemsList = new ListCartItems();
        returncartItemsList.setList(cartItemsList);
        return returncartItemsList;
    }

    public int GetNewCart(int idclient) {

        int lastcart = 0;
        int gencart=0;

            List<CartItems> cartItemsList=cartRepository.findAll();

            cartItemsList.size();

            for (CartItems x : cartItemsList) {
                if(lastcart<x.getIdCart())
                {
                    lastcart=x.getIdCart();

                }
                gencart=x.getIdGeneral();
            }


            //CartItems cart = new CartItems(gencart+1,lastcart+1,idclient,0,0,true);
           // cartRepository.save(cart);

            return lastcart+ 1;





    }
}


