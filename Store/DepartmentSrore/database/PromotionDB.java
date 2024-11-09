package DepartmentSrore.database ;

import DepartmentSrore.datamodel.promotion.Promotion;

public interface PromotionDB {

    public void updatePromotion(Promotion p);
    public Promotion getPromotion(String name);
    public void deletePromotion(String name);
    
}