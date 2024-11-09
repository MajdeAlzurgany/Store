package DepartmentSrore.datamodel.product;

import DepartmentSrore.Category;
import DepartmentSrore.Exceptions.PriceNegativeException;

public class Product {
    private Integer id;
    private String name;
    private Category category;
    private Double cost;
    private Double unitprice;
    public Product(Integer id, String name, Category category, Double cost, Double price)throws PriceNegativeException{
        this.id=id;
        this.name=name;
        this.category=category;
        this.cost=cost;
        setUnitprice(price);
    } 
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public Category getCategory() {
        return category;
    }
    public void setCost(Double cost) {
        this.cost = cost;
    }
    public Double getCost() {
        return cost;
    }
    public void setUnitprice(Double unitprice) throws PriceNegativeException {
        if(unitprice >= 0 ){
            this.unitprice = unitprice;
        }else{
            throw new PriceNegativeException();
        }
        
    }
    public Double getUnitprice() {
        return unitprice;
    }
    public String toString(){
        return String.format("|%10d", id)+
                String.format("|%-40s",name)+
                String.format("|%-17s",category)+
                String.format("|%8.3f",cost)+
                String.format("|%8.3f|",unitprice);
    }
}