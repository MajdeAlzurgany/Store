package DepartmentSrore.datamodel.paymentmethod;

public abstract class PaymentMethod {
    String name;
    abstract public Double calculateAmount(Double amount);
}