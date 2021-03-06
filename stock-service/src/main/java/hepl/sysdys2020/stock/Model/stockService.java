package hepl.sysdys2020.stock.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class stockService {

    @Autowired
    stockRepository stockRepository;

    public List<stockItems> getAllStock(){
        List<stockItems> stockItems = new ArrayList<>();
        stockRepository.findAll().forEach(stockItems::add);
        return stockItems;
    }

    public stockItems getItemsbyId(int id){
        return stockRepository.findById(id).get();
    }

    public void SaveOrUpdate(stockItems stockItems){
        stockRepository.save(stockItems);
    }

    public void delete(int id){
        stockRepository.deleteById(id);
    }

    public void ReturnItem(int id){



    }

    public int getLastId(){
        int i=0;
        boolean test = true;
        while(test == true){
            i++;
            test = stockRepository.existsById(i);
        }
        System.out.println("test id : " + i);
        return i;
    }


}
