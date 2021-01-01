package com.example.demo.Model;

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
    public void ChangeStock( String libelle){

        double prixfirst=0;
        stockItems items=null;
        List<stockItems> stockItems = stockRepository.findAll();
        for (stockItems x: stockItems) {
            if(x.getLibelle().equals(libelle)==true) {
                x.setQuantite(0);
                stockRepository.save(x);

            }
        }

    }
    public stockItems getBetterPriceDemande( String nameproduct){

        double prixfirst=0;
        stockItems items=null;
        List<stockItems> stockItems = stockRepository.findAll();
        for (stockItems x: stockItems) {
            if(x.getLibelle().equals(nameproduct)==true && x.getQuantite()>0) {


                items=x;
                prixfirst=x.getPrix();

                /*
                si le fournisseur a le meme article avec des prix differents
               if(prixfirst>x.getPrix())
               {
                   items=x;
                   prixfirst=x.getPrix();
               }

                 */

            }
        }


        int index=0;

        if("jeux".equals(nameproduct)==true)
        {
            index=2;
        }
        else
        {
            if("divers".equals(nameproduct)==true)
            {
                index=3;
            }
            else
            {
                index=1;
            }
        }
        if(items==null)
        {
            return null;
        }

        stockItems newitems = new stockItems(index,items.getFournisseur()
                ,items.getLibelle(),items.getQuantite(),items.getPrix());
        /*
        items.setQuantite(0);


         */
        stockRepository.save(items);

        return newitems;

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
