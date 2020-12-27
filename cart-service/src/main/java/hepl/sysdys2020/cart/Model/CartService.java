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
    public List<Integer> getAllCartTrue(){

        int objet1=0;
        int objet2=0;
        int objet3=0;
        List<Integer> cartItemsList = new ArrayList<>();

        List<CartItems> cart=cartRepository.findAll();

        for (CartItems x: cart) {
            if(x.isVirtual()==true) {
                if(x.getIdProduit()==1)
                {
                    objet1=objet1+x.getQuantite();
                }
                else
                {
                    if(x.getIdProduit()==2)
                    {
                        objet2=objet2+x.getQuantite();
                    }
                    else
                    {
                        objet3=objet3+x.getQuantite();
                    }
                }

                       };
        }
        cartItemsList.add(objet1);
        cartItemsList.add(objet2);
        cartItemsList.add(objet3);
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

    public int GetCurrentCart(int id) {

        List<CartItems> cartItemsList = new ArrayList<>();


        List<CartItems> cart=cartRepository.findAll();

        for (CartItems x: cart) {
            if(x.isVirtual()==true && x.getIdClient()==id) {

                     return x.getIdCart();
            };
        }
      return 0;
    }

    public boolean UpdateCart(int id,int idclient) {

        List<CartItems> cartItemsLista = new ArrayList<>();
        int lastid=0;
        CartItems cartchange = new CartItems();
        List<CartItems> cart=cartRepository.findAll();

        for (CartItems x: cart) {
            if(lastid<x.getIdCart())
            {
                lastid=x.getIdCart();
            }
            if(x.isVirtual()==true && x.getIdCart()==id) {
                cartItemsLista.add( new CartItems(x.getIdGeneral(),x.getIdCart(),x.getIdClient(),x.getIdProduit(),x.getQuantite(),x.isVirtual()));};
        }

        // liste (panier) ancien

        // idcart change + idclient change
        int cartcurrent=GetCurrentCart(idclient);
        if(cartcurrent==0)
        {
            cartcurrent=lastid;
        }

/*
        cartchange.setIdCart(cartcurrent);
        cartchange.setIdClient(idclient);

         */

        // recherche l'ancien cart
        List<CartItems> cartItemsList = new ArrayList<>();

        for (CartItems x: cart) {
            if(x.isVirtual()==true && x.getIdClient()==idclient) {
                cartItemsList.add(
                        new CartItems(x.getIdGeneral(),x.getIdCart(),x.getIdClient(),x.getIdProduit(),x.getQuantite(),x.isVirtual()));};
        }

        // ajouter les qt dans le nouv cart
        for( CartItems xi:cartItemsLista)
        {
            if(cartItemsList.size()!=0)
            {
                for(CartItems x :cartItemsList)
                {
                    if(x.getIdProduit()==xi.getIdProduit())
                    {
                        x.setQuantite(x.getQuantite()+xi.getQuantite());
                        cartRepository.save(x);
                        xi.setVirtual(false);

                    }
                    else
                    {
                        xi.setIdCart(cartcurrent);
                        xi.setIdClient(idclient);
                        cartRepository.save(xi);
                    }

                }
            }
            else
            {
                xi.setIdCart(cartcurrent);
                xi.setIdClient(idclient);
                cartRepository.save(xi);
            }
        }
        return true;
    }





            public void SaveOrUpdate(CartItems cartItems){
                cartRepository.save(cartItems);
            }

            public boolean delete(int id){



                List<CartItems> cart=cartRepository.findAll();

                for (CartItems x: cart) {
                    if(x.isVirtual()==true && x.getIdClient()==id) {
                       cartRepository.deleteById(x.getIdGeneral());
                    };
                }

                return true;
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


