package DepartmentSrore.database;

import DepartmentSrore.datamodel.promotion.Promotion;
import java.util.HashMap;


public class PromotionHashMap implements PromotionDB  {

    private HashMap<String,Promotion> promotions ;
    
    public PromotionHashMap(){
        this.promotions = new HashMap<String , Promotion>();
    }

    public void updatePromotion(Promotion p){
        if(promotions.containsKey(p.getName())){
            promotions.replace(p.getName(), p);
        }else{
            promotions.put(p.getName(), p);
        }
    }
    public Promotion getPromotion(String name){
        return promotions.get(name);

    }
    public void deletePromotion(String name){
        promotions.remove(name);
    }
}
