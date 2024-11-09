package DepartmentSrore.datamodel.order;

//import DepartmentSrore.Category;

//import DepartmentSrore.datamodel.product.Product;

/*public class OrderItem{
    private Integer  qty;
    private Double price;
    private Product product;
    public OrderItem(Integer  qty, Double price, Product product){
        this.setQty(qty);
        this.setPrice(price);
        this.setProduct(product);
    }
    public OrderItem(Integer  qty , Product product){
        this.setQty(qty);
        this.setProduct(product);
    }
    public void setQty(Integer  qty) {
        if (qty>0){
            this.qty = qty;
        }else{
            this.qty = 1;
        }
    }
    public Integer  getQty() {
        return qty;
    }
    public void setPrice(Double price) {
        if (price>0){
            this.price = price;
        }
    }
    public Double getPrice() {
        return price;
    }
    public void setProduct(Product product) {
        if(product!=null){
            this.product = product;
            if (this.price==0){
                this.price=product.getUnitprice();
            }
        }
    }
    public Product getProduct() {
        return product;
    }
    @Override
    public boolean equals(Object item){
        OrderItem newOrderItem = (OrderItem)item;
        return this.getProduct().equals(newOrderItem.getProduct());
    }
    public String toString(){
        String r="";
        r+=String.format("%1$-10d %2$-30s  %3$10d  %4$10.3f %5$10.3f", 
                    product.getId(), product.getName(), qty,price, qty*price);
        return r;
    }
    public String getProductName(){
        return product.getName();
    }
    public Category getProductCatogry(){
        return product.getCategory();
    }
}*/