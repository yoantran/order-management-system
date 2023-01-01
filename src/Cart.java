//https://www.quora.com/How-do-I-add-a-key-value-pair-in-ArrayList-in-Java


import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> products;

    public Cart() {
        products = new HashMap<>();
    }

    public void addProduct(Product product, int amount) {
        if (products.containsKey(product)) {
            amount += products.get(product);
        }
        products.put(product, amount);
    }

    public void addProduct(Product product) {
        addProduct(product, 1);
    }


    public void deleteProduct(Product product) {
        products.remove(product);
    }

    public void reset() {
        products.clear();
    }



//    Cart cart = new Cart();
//Product product1 = new Product("Product 1", 50, "Category 1");
//Product product2 = new Product("Product 2", 100, "Category 2");
//cart.addProduct(product1, 2);
//cart.addProduct(product2, 3);

//    cart.deleteProduct(product1);
//    cart.reset();

    public double getTotalAmount() {
        int totalAmount = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int amount = entry.getValue();
            totalAmount += product.getPrice() * amount;
        }
        return totalAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int amount = entry.getValue();
            sb.append(product.getId()).append(";").append(product.getPrice()).append(";").append(amount).append("|");
        }
        int length = sb.length();
        sb.deleteCharAt(length - 1);
        return sb.toString();
    }






}