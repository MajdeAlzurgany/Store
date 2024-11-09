package DepartmentSrore.database;

import DepartmentSrore.datamodel.product.Product;

public interface ProductDB {
    public Product getProduct(Integer id);
    public void updateProduct(Product product); // Fixed the method name
    public void deleteProduct(Integer id);
}