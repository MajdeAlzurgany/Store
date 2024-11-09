package DepartmentSrore.datamodel.paymentmethod;

import DepartmentSrore.Exceptions.DiscountInvalidException;

public class CreditCard extends PaymentMethod {
    private Double overhead;

    public CreditCard(Double overhead)throws DiscountInvalidException{
        setOverhead(overhead);
    } 
    @Override
    public Double calculateAmount(Double amount) {
        amount = amount + amount*overhead;
        return amount;
    }
    public void setOverhead(Double overhead)throws DiscountInvalidException {
        if(overhead>0 && overhead < 1 ){
            this.overhead = overhead ;
        }else{
            throw new  DiscountInvalidException();
        }
    }
    
}