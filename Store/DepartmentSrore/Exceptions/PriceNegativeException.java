package DepartmentSrore.Exceptions;

public class PriceNegativeException extends Exception {
    public PriceNegativeException(){
        super("Price most be positive value ");
    }
}
