package DepartmentSrore.Exceptions;

public class DiscountInvalidException extends Exception  {
    public DiscountInvalidException(){
        super("Percentage discount most be between 0 and 1 ");
    }   
}
