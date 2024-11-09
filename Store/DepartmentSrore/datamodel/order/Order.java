package DepartmentSrore.datamodel.order;
import java.util.LinkedList;
import java.time.LocalDate;

import DepartmentSrore.Category;
import DepartmentSrore.datamodel.paymentmethod.PaymentMethod;
import DepartmentSrore.datamodel.product.Product;
import DepartmentSrore.Exceptions.ProductNullException;
import DepartmentSrore.Exceptions.PriceNegativeException;


public class Order {
    private int id;
    private LocalDate date;
    boolean discountDone=false; // checker for availability
    private LinkedList<OrderItem> orderItems=  new LinkedList<OrderItem>();

    //+++++++OrderItem Class++++++++++++++++++++++++++++
    public class OrderItem{
        private int qty;
        private double price;
        private Product product;
        public OrderItem(int qty, double price, Product product) throws ProductNullException , PriceNegativeException {
            this.setQty(qty);
            this.setPrice(price);
            this.setProduct(product);
        }
        public OrderItem(int qty , Product product) throws ProductNullException{
            this.setQty(qty);
            this.setProduct(product);
        }
        public void setQty(int qty) {
            if (qty>0){
                this.qty = qty;
            }else{
                this.qty = 1;
            }
        }
        public int getQty() {
            return qty;
        }
        public void setPrice(double price) throws PriceNegativeException {
            if (price>0){
                this.price = price;
            }else{
                throw new PriceNegativeException();
            }
        }
        public double getPrice() {
            return price;
        }
        public void setProduct(Product product) throws ProductNullException{
            if(product!=null){
                this.product = product;
                if (this.price==0){
                    this.price=product.getUnitprice();
                }
            }else{
                throw new ProductNullException();
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
    }
    public Order(int id, LocalDate date){
        this.id=id;
        this.date=date;
    }
    public Order(int id, LocalDate date,LinkedList<OrderItem> items){
        this.id=id;
        this.date=date;
        if(items!=null){
            this.orderItems=items;
        }
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalDate getDate() {
        return date;
    }
    
    public boolean isDiscountDone() {
        return discountDone;
    }
    public void setDiscountDone(){
        discountDone=true;
    }
    public void addItem(OrderItem item){
        if(!orderItems.contains(item)){
            orderItems.add(item);
        }
    }
    public void addItem(int qty, double price, Product product) throws ProductNullException , PriceNegativeException {
        
        OrderItem orderItem= new OrderItem(qty , price , product);
        if(!orderItems.contains(orderItem))
            addItem(orderItem);
        else{
            int index;
            index = orderItems.indexOf(orderItem);
            OrderItem oldOrderItem = orderItems.get(index);
            oldOrderItem.setPrice(price);
            oldOrderItem.setQty(qty);
        }

        

        
        
    }
    public void removeItem(OrderItem item){
        if(orderItems.contains(item)){
            orderItems.remove(item);
        }
    }
    public void removeItem(Product product)throws ProductNullException{
        OrderItem toRemove = new OrderItem(0 , product);
        removeItem(toRemove);
        
    }
    public String toString(){
        String r="========= Order =============================================================\n";
        r+=String.format("Order number: %1$10d     Order Date: %2$te/%2$tm/%2$tY \n", id,date);
        for (OrderItem orderItem : orderItems) {
            r+=(orderItem.toString()+" \n");
        }
        r+=String.format("============== T O T A L =================: %1$15.3f",getOrderTotal());
        return r;
    }
    public double getOrderTotal() {
        double total=0.0;
        for (OrderItem orderItem : orderItems) {
            total+=(orderItem.getQty()*orderItem.getPrice());
        }
        return total;
    }
    public double getOrderTotal(PaymentMethod pay){
        return pay.calculateAmount(getOrderTotal());
    }
    public LinkedList<OrderItem> getOrderItems(){
        return orderItems;
    }
    public Order flatdiscount(double discount) throws PriceNegativeException{
        for (OrderItem orderItem : orderItems) 
            orderItem.setPrice(orderItem.getPrice() - discount*orderItem.getPrice());
        this.discountDone=true;
        return this;
    }
    
}