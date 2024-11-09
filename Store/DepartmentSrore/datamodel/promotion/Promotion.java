package DepartmentSrore.datamodel.promotion;

import java.util.Date;

import DepartmentSrore.Constants;
import DepartmentSrore.Exceptions.DiscountInvalidException;
import DepartmentSrore.Exceptions.PriceNegativeException;
import DepartmentSrore.Exceptions.PromotionTypeMismatchException;
import DepartmentSrore.Exceptions.DuplictePromotionExceptins;
import DepartmentSrore.datamodel.order.Order;

public abstract class  Promotion {
    protected String name;
    protected Constants.PromotionType type;
    Validator v;
    
    public  Promotion(String name, Constants.PromotionType type) throws PromotionTypeMismatchException{
        this.name = name;
        setPromotionType(type);
    }

   public String getName() {
        return name;
    }

    public Constants.PromotionType getType() {
        return type;
    }

    public Validator getV() {
        return v;
    }

    public void setPromotionType(Constants.PromotionType type)throws PromotionTypeMismatchException {
        if (v == null || v.getType().equals(type.name())) {
            this.type = type;
        } else {
            throw new PromotionTypeMismatchException();
        }
    }
    
    
    
    public void addValidator(Validator v) throws PromotionTypeMismatchException {
        if (type.name().equals(v.getType())) {
            this.v=v;
        }else{
            throw new PromotionTypeMismatchException();
        }
    }
    


    protected abstract Boolean isValled(Date today);
    public abstract Order applyPromotion(Order o) throws PriceNegativeException, DiscountInvalidException ,DuplictePromotionExceptins;

}