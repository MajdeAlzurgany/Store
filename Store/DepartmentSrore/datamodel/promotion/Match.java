package DepartmentSrore.datamodel.promotion;
import java.util.ArrayList;
import java.util.Date;

import DepartmentSrore.Constants;
import DepartmentSrore.datamodel.order.Order;
import DepartmentSrore.datamodel.product.Product;
import DepartmentSrore.Exceptions.PriceNegativeException;
import DepartmentSrore.Exceptions.ProductNullException;
import DepartmentSrore.Exceptions.PromotionTypeMismatchException;
import DepartmentSrore.Exceptions.DuplictePromotionExceptins;


public class Match  extends Promotion {
    private Integer qtyIteamsOrder;
    private Integer qtyIteamsPaid;
    private ArrayList<Product>products= new ArrayList<>();
    

    public Match(String name, Constants.PromotionType type, Integer qtyIteamsOrder, Integer qtyIteamsPaid) throws PromotionTypeMismatchException{
        super(name, type);
        this.qtyIteamsOrder = qtyIteamsOrder;
        this.qtyIteamsPaid = qtyIteamsPaid;
        
    }

    public void addProducts(ArrayList<Product> products) throws ProductNullException{
        
        if (!products.isEmpty()){  //check if the products arrayList is empty or not 
            this.products = products;
        }else{
            throw new ProductNullException();
        }
    }

    @Override
    protected Boolean isValled(Date today){
        if(v==null)  // if the v object from validator is empty return true to handle the null exception (that means is the promotion type is ALWAYS)
            return true;
        else
            return super.v.validate(today);
    } 


    @Override
    public Order applyPromotion(Order o) throws PriceNegativeException , DuplictePromotionExceptins{
    if (o.isDiscountDone()) {    //check if the discount done on this order before or not !
        throw new DuplictePromotionExceptins();
    }
    
    if (super.type == Constants.PromotionType.ALWAYS || isValled(new Date())) {
        for (Order.OrderItem item : o.getOrderItems()) { // items for
            for (Product product : products) { //prods for
                if (item.getProduct().equals(product)) {
                    Integer l = item.getQty();
                    if( l <qtyIteamsOrder) // check if the quantity of orderitem greater than the qtyIteamsOrder , if not continue
                        continue;

                    //calc the final price 
                    System.out.println(item.getProductName() + " IS IN THE PROMOTION !"); 
                    Double old_unit_price =  item.getPrice();
                    Double new_unit_price = (old_unit_price * (qtyIteamsOrder-qtyIteamsPaid))/qtyIteamsOrder;
                    item.setPrice(new_unit_price);

                    if(item.getQty()%qtyIteamsOrder!=0){
                        Double final_unit_price =  ((new_unit_price*(l-(l%qtyIteamsOrder))) + (old_unit_price * (l%qtyIteamsOrder)))/l;
                        item.setPrice(final_unit_price);
                    }
            }   }
        }
        o.setDiscountDone(); //set the checker in the order as true for done promotion
    }
    else{
        System.out.println("No promotion avallible");
    }
    
    return o;
}

}