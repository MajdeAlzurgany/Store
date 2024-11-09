package DepartmentSrore.datamodel.promotion;
import java.util.Date;

import DepartmentSrore.Constants;
import DepartmentSrore.datamodel.order.Order;
import DepartmentSrore.datamodel.order.Order.OrderItem;
import DepartmentSrore.Exceptions.PriceNegativeException;
import DepartmentSrore.Exceptions.PromotionTypeMismatchException;
import DepartmentSrore.Exceptions.DiscountInvalidException;
import DepartmentSrore.Exceptions.DuplictePromotionExceptins;

public class FlatDiscount extends Promotion{
    Double discount;
    public FlatDiscount(String name, Constants.PromotionType type) throws PromotionTypeMismatchException {
        super(name, type);
    }
    @Override
    protected Boolean isValled(Date today){
        if(v==null) // if the v object from validator is empty return true to handle the null exception (that means is the promotion type is ALWAYS)
            return true;
        else
            return super.v.validate(today);
    } 

    @Override
    public Order applyPromotion(Order o) throws PriceNegativeException , DiscountInvalidException , DuplictePromotionExceptins {
        if (o.isDiscountDone()) { //check if the discount done on this order before or not !
            throw new DuplictePromotionExceptins();
        }
        if(super.type==Constants.PromotionType.ALWAYS || isValled(new Date())){ // i used this or because i dont want to let isalled clalled if the first condition is true
            Double orderTotal = o.getOrderTotal(); //get the total amount of the order

            //conditions for the promotion below

        if (orderTotal > 500 && orderTotal < 700) { 
            this.setDiscount(0.05);
            for(OrderItem item : o.getOrderItems()){
                item.setPrice(item.getPrice() - getDiscount()*item.getPrice());
            }

            o.setDiscountDone();
            return o;

        } else if (orderTotal >= 700 && orderTotal < 1000) {
            this.setDiscount(0.07);
            for(OrderItem item : o.getOrderItems()){
                item.setPrice(item.getPrice() - getDiscount()*item.getPrice());
            }

            o.setDiscountDone(); //set the checker in the order as true for done promotion
            return o;
        } else if (orderTotal >= 1000) {
            this.setDiscount(0.10);
            for(OrderItem item : o.getOrderItems()){
                item.setPrice(item.getPrice() - getDiscount()*item.getPrice());
            }
            o.setDiscountDone();  //set the checker in the order as true for done promotion
            return o;
        } else {
            System.out.println("No discounts on this bill "); 
            return o;
        }
    } 
    else {
        System.out.println("No promotions Avillble");
        return o;
    }
    }

    public void setDiscount(Double discount) throws DiscountInvalidException{
        if(discount>0 && discount < 1 ){
            this.discount = discount ;
        }else{
            throw new  DiscountInvalidException();
        }
    }
    public Double getDiscount() {
        return discount;
    }
    
    
}