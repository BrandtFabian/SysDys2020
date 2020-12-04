package hepl.sysdys2020.cart.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<CartItems> getCartbyId(int id){
        List<CartItems> cartItemsList = new ArrayList<>();
        cartRepository.findAllById(Collections.singleton(id)).forEach(cartItemsList::add);
        return cartItemsList;
    }

    public void SaveOrUpdate(CartItems cartItems){
        cartRepository.save(cartItems);
    }

    public void delete(Integer cartItems){
        cartRepository.delete(cartItems);
    }


}
