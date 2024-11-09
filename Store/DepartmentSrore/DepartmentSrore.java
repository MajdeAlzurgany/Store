package DepartmentSrore;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.text.ParseException;


import DepartmentSrore.database.OrderHashMap;
import DepartmentSrore.database.ProductHashMap;
import DepartmentSrore.database.PromotionHashMap;
import DepartmentSrore.datamodel.order.Order;
import DepartmentSrore.datamodel.paymentmethod.Cash;
import DepartmentSrore.datamodel.paymentmethod.PaymentMethod;
import DepartmentSrore.datamodel.product.Product;
import DepartmentSrore.datamodel.promotion.CategoryDiscount;
import DepartmentSrore.datamodel.promotion.DayOfWeek;
import DepartmentSrore.datamodel.promotion.FlatDiscount;
import DepartmentSrore.datamodel.promotion.Match;
import DepartmentSrore.datamodel.promotion.Validator;
import DepartmentSrore.datamodel.paymentmethod.CreditCard;
import DepartmentSrore.datamodel.promotion.Period;
import DepartmentSrore.Exceptions.DiscountInvalidException;
import DepartmentSrore.Exceptions.DuplictePromotionExceptins;
import DepartmentSrore.Exceptions.PriceNegativeException;
import DepartmentSrore.Exceptions.ProductNullException;
import DepartmentSrore.Exceptions.PromotionTypeMismatchException;

public class DepartmentSrore {
    public static final String csvFile = "C:\\Users\\ALZARQANI\\Desktop\\UNIVARCITY\\CS355\\my codes\\home works\\Home Work4\\DepartmentSrore\\MOCK_DATA.csv";
    public static final String delimiter = ",";
    public static void main(String[] args) throws ParseException {
        // Read input file and store products in the array list of products
        ProductHashMap products = new ProductHashMap();
        OrderHashMap orders = new OrderHashMap();
        ArrayList<Product> Promotion_products = new ArrayList<Product>();
        //ArrayList<Order> orders = new ArrayList<Order>();
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;
            while ((line = br.readLine()) != null) {
                tempArr = line.split(delimiter);
                Integer id = Integer.parseInt(tempArr[0]);
                String name = tempArr[1];
                Category category = Category.valueOf(tempArr[2]);
                Double cost = Double.parseDouble(tempArr[3]);
                Double price = cost * 1.45;
                Product p = new Product(id, name,category, cost, price);
                products.updateProduct(p);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error reading the file:\n" + e.toString());
        }

        // Create a random number of orders b
        try{
            Random r = new Random(10);
            Integer numberOrders = (int)(10*r.nextDouble());
            for (Integer i = 1; i <= numberOrders; ++i) {
                Order o = new Order(i + 100, LocalDate.now());
                Integer numberItems=(int)(10*r.nextDouble());
                for (Integer j = 1; j <= numberItems; ++j) {
                    Integer productIndex = (int) (1000 * r.nextDouble());
                    Product p = products.getProduct(productIndex);
                    Integer qty = (int) (10 * r.nextDouble());
                    Order.OrderItem item = o.new OrderItem(qty, p.getUnitprice(), p);
                    o.addItem(item);
                }
                orders.updateOrder(o);
            }
        }catch(PriceNegativeException e){
            System.out.println("Error -> " + e.getMessage());
        }catch(ProductNullException e){
            System.out.println("Error -> " + e.getMessage());
        } 



        try{
            System.out.println("------------------Test--------------------------");
            //getting orders with there ids by hashMap
            Order o1 = orders.getOrder(101);
            Order o2 = orders.getOrder(102);
            Order o3 = orders.getOrder(103);
        
            String startDateString = "1/1/2024"; 
            String endDateString = "1/5/2024";

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            Date startDate = dateFormat.parse(startDateString);
            Date endDate = dateFormat.parse(endDateString);
            //validator instances
            Validator V1 = new Period(startDate, endDate);
            Validator V2 = new DayOfWeek(new String[]{"Saturday", "Sunday" , "Monday" , "Tuesday" , "Wednesday" , "Thursday" , "Friday"});
 
            //Flat promotion instance
            FlatDiscount F1 = new FlatDiscount("FlatDiscount", Constants.PromotionType.ALWAYS);
            //No needs to add validator cause its always 
            //match promotion instance

            Promotion_products = new ArrayList<>(products.getAllProducts()); // Function in ProductHashMap to get all products , to store them in array list and pass them in validator
            Match M1 = new Match("Math discount Bay 3 get 1 free", Constants.PromotionType.DAYOFWEEK, 3, 1);
            M1.addProducts(Promotion_products);
            M1.addValidator(V2);  // TO APPLY THE V2 VALIDATOR ON THIS PROMOTION

            CategoryDiscount C = new CategoryDiscount("Catogry discount",   Constants.PromotionType.PERIODIC  , Category.MENFASHON);
            C.addValidator(V1);
            C.setDiscount(0.05);

            //Storing  promotions in the hashMap 
            PromotionHashMap promotions = new PromotionHashMap();
            promotions.updatePromotion(F1);
            promotions.updatePromotion(M1);
            promotions.updatePromotion(C);
            

            PaymentMethod pay1=new Cash(0.05);
            PaymentMethod pay2= new CreditCard(0.05);
            System.out.println(o1.toString());// THE ORDER 1
            o1 = promotions.getPromotion("Math discount Bay 3 get 1 free").applyPromotion(o1); // I fetch the promotion from hash by its key !
            System.out.println("TOTAL MATCH DISCOUNT :"+o1.getOrderTotal());
            System.out.println("CASH :" + o1.getOrderTotal(pay1));
            System.out.println("CARD :" + o1.getOrderTotal(pay2));

            System.out.println(o2.toString()); // THE ORDER 2
            o2 = promotions.getPromotion("FlatDiscount").applyPromotion(o2); //I fetch the promotion from hash by its key !
            System.out.println("Discount percentage : " + F1.getDiscount()); // Persentage OF DISCOUNT 
            System.out.println("TOTAL AFTER  FLAT  DISCOUNT :"+o2.getOrderTotal());
            System.out.println("CASH :" + o2.getOrderTotal(pay1));
            System.out.println("CARD :" + o2.getOrderTotal(pay2));
            System.out.println(" ====================================================");

            System.out.println(o3.toString()); // THE ORDER 3
            o3= promotions.getPromotion("Catogry discount").applyPromotion(o3); //I fetch the promotion from hash by its key !
            System.out.println("Discount percentage : " + C.getDiscount()); // Persentage OF DISCOUNT 
            System.out.println("TOTAL CATEGORY DISCOUNT :"+o3.getOrderTotal());
            System.out.println("CASH :" + o3.getOrderTotal(pay1));
            System.out.println("CARD :" + o3.getOrderTotal(pay2));

            /*
                "In the previous code, no errors will be shown, and the program will not stop.
                However, in the next script, we will intentionally test for errors and observe the output.
                Select one line and comment out the rest below for each run to see the output exception."
            */
           o3= promotions.getPromotion("Math discount Bay 3 get 1 free").applyPromotion(o3); // duplicate promotion on same order

           PaymentMethod pay=new Cash(1.2); // invalid percentage
           C.setDiscount(2.0);

           ArrayList<Product> Nullproducts = new ArrayList<Product>(); // null array list for check
           M1.addProducts(Nullproducts); //products null 
           Order.OrderItem item = o1.new OrderItem(3,300, null);

           C.addValidator(V2); // mismatching 
           C.setPromotionType(Constants.PromotionType.DAYOFWEEK);
           
    

        }catch(DiscountInvalidException e){
            System.out.println("Error->"+ e.getMessage());
        }catch(PriceNegativeException e){
            System.out.println("Error->"+ e.getMessage());
        }catch(PromotionTypeMismatchException e){
            System.out.println("Error->"+ e.getMessage());
        }catch(ProductNullException e){
            System.out.println("Error->"+ e.getMessage());
        }catch(DuplictePromotionExceptins e){
            System.out.println("Error->" + e.getMessage());
        }

    }
}
