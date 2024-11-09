package DepartmentSrore.database;

import java.util.HashMap;
import DepartmentSrore.datamodel.order.Order;

public class OrderHashMap implements OrderDB {
    private HashMap<Integer, Order> orders;

    public OrderHashMap() {
        this.orders = new HashMap<Integer, Order>();
    }

    public Order getOrder(Integer id) {
        return orders.get(id);
    }

    public void updateOrder(Order order) {
        if (orders.containsKey(order.getId())) {
            orders.replace(order.getId(), order);
        } else {
            orders.put(order.getId(), order);
        }
    }

    public void deleteOrder(Integer id) {
        if (orders.get(id) != null)
            orders.remove(id);
    }

    public Boolean isEmpty() {
        return orders.isEmpty();
    }
}
