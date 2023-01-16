//https://www.quora.com/How-do-I-add-a-key-value-pair-in-ArrayList-in-Java


import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> products;
    private double totalAmount;

    public Cart() {
        products = new HashMap<>();
        totalAmount = 0;
    }

    public void addProduct(Product product, int amount) {
        int newAmount = amount;
        if (products.containsKey(product)) {
            newAmount += products.get(product);
        }
        products.put(product, newAmount);
        totalAmount += product.getPrice() * amount;
    }

    public void addProduct(String product, int amount, int price, String productName) {
        int newAmount = amount;
        Product productInfo = new Product(product, productName, price);
        if (products.containsKey(productInfo)) {
            newAmount += products.get(product);
        }
        products.put(productInfo, newAmount);
        totalAmount += price * amount;
    }

    public void deleteProductById(String productId) {
        Product productToRemove = null;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            if (product.getId().equals(productId)) {
                productToRemove = product;
                break;
            }
        }
        if (productToRemove != null) {
            int amount = products.get(productToRemove);
            totalAmount -= amount * productToRemove.getPrice();
            products.remove(productToRemove);
        }
    }

    public void deleteProductByName(String productName) {
        Product productToRemove = null;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            if (product.getProductName().equals(productName)) {
                productToRemove = product;
                break;
            }
        }
        if (productToRemove != null) {
            int amount = products.get(productToRemove);
            totalAmount -= amount * productToRemove.getPrice();
            products.remove(productToRemove);
        }
    }

    public void reset() {
        products.clear();
        totalAmount = 0;
    }


    public double getTotalAmount() {
        return totalAmount;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public boolean isCartEmpty() {
        return products.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int amount = entry.getValue();
            sb.append(product.getId()).append(";").append(product.getProductName()).append(";")
                    .append(product.getPrice()).append(";").append(amount).append("|");
        }
        int length = sb.length();
        if (length > 0) {
            sb.deleteCharAt(length - 1);
        }
        return sb.toString();
    }

}