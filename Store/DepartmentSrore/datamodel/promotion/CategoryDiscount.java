package DepartmentSrore.datamodel.promotion;
import java.util.Date;
import DepartmentSrore.Category;

import DepartmentSrore.Constants;
import DepartmentSrore.Exceptions.DiscountInvalidException;
import DepartmentSrore.Exceptions.PriceNegativeException;
import DepartmentSrore.Exceptions.PromotionTypeMismatchException;
import DepartmentSrore.datamodel.order.Order;
import DepartmentSrore.datamodel.order.Order.OrderItem;
import DepartmentSrore.Exceptions.DuplictePromotionExceptins;


public class CategoryDiscount  extends Promotion {
    private Category category;
    private Double discount ;

    public CategoryDiscount(String name, Constants.PromotionType type , Category category ) throws PromotionTypeMismatchException  {
        super(name, type);
        this.category=category;
    }

    @Override
    public Boolean isValled(Date today){
        if(v==null) // if the v object from validator is empty return true to handle the null exception (that means is the promotion type is ALWAYS)
            return true;
        else
            return super.v.validate(today);
    } 

    @Override
    public Order applyPromotion(Order o)throws PriceNegativeException , DiscountInvalidException , DuplictePromotionExceptins{
        if (o.isDiscountDone()) { //check if the discount done on this order before or not !
            throw new DuplictePromotionExceptins();
        }
        if(super.type==Constants.PromotionType.ALWAYS || isValled(new Date())){ // i used this or because i dont want to let isalled clalled if the first condition is true
            for(OrderItem item : o.getOrderItems()){  //items for 
                if(item.getProductCatogry()==category){ 
                    Double old_Price = item.getPrice();
                    Double new_price = old_Price - old_Price*getDiscount();
                    item.setPrice(new_price);
                    System.out.println(item.getProductName() + " IS IN THE PROMOTION !");
                }
            }
            o.setDiscountDone();    //set the checker in the order as true for done promotion
            return o;
        }
        else{
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
