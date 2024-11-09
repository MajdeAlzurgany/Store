package DepartmentSrore.datamodel.paymentmethod;

import DepartmentSrore.Exceptions.DiscountInvalidException;

public class Cash extends PaymentMethod {
    private Double discountPercentage;

    public void setDiscountPercentage(Double discountPercentage) throws DiscountInvalidException {
        if(discountPercentage>0 && discountPercentage < 1 ){
            this.discountPercentage = discountPercentage ;
        }else{
            throw new  DiscountInvalidException();
        }
    }
    public Cash(Double discountPercentage) throws DiscountInvalidException{
        
        setDiscountPercentage(discountPercentage);
    }
    @Override
    public Double calculateAmount(Double amount) {
        amount=amount - (amount * discountPercentage);
        return amount;
    }
    
}