package DepartmentSrore.database;

import DepartmentSrore.datamodel.order.Order;

public interface OrderDB {
    public Order getOrder(Integer id);
    public void updateOrder(Order order);
    public void deleteOrder(Integer id);
}
