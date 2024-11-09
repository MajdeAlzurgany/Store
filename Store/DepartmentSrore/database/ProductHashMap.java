package DepartmentSrore.database;

import java.util.Collection;
import java.util.HashMap;

import DepartmentSrore.datamodel.product.Product;

public class ProductHashMap implements ProductDB {

    private HashMap<Integer, Product > products ;

    public ProductHashMap(){
        this.products= new HashMap<Integer, Product>();

    }

    public Product getProduct(Integer id){
        return products.get(id);

    }
    public void updateProduct(Product product){
        if(products.containsKey(product.getId())){
            products.replace(product.getId(), product);
        }else{
            products.putIfAbsent(product.getId(), product);
        }
    }
    public void deleteProduct(Integer id){
        if(products.get(id) != null)
            products.remove(id);
    }
    public Boolean isEmpty(){
        return products.isEmpty();
    }

    
    public Collection<Product> getAllProducts() {
        return products.values();
    }
    
}
