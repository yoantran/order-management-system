//https://www.quora.com/How-do-I-add-a-key-value-pair-in-ArrayList-in-Java


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<String [], Integer> products;
    private double totalAmount;

    public Cart() {
        products = new HashMap<>();
        totalAmount = 0;
    }

    public void addProduct(String product, int amount) throws IOException {
        if (products.containsKey(product)) {
            amount += products.get(product);
        }
        Product newProduct = Product.findProductById(product);
        String [] productInfo = new String [] {newProduct.getId(), newProduct.getProductName(), String.valueOf(newProduct.getPrice())};
        products.put(productInfo, amount);
        totalAmount += newProduct.getPrice() * amount;
    }

    public void addProduct(String product, int amount, int price, String productName) {
        if (products.containsKey(product)) {
            amount += products.get(product);
        }
        String [] productInfo = new String [] {product, productName, String.valueOf(price)};
        products.put(productInfo, amount);
        totalAmount += price * amount;
    }

    public void deleteProduct(Product product) {
        products.remove(product);
    }

    public void reset() {
        products.clear();
    }




    public double getTotalAmount() {
        return totalAmount;
    }

    public Map<String[], Integer> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String[], Integer> entry : products.entrySet()) {
            String[] productInfo = entry.getKey();
            int amount = entry.getValue();
            sb.append(productInfo[0]).append(";").append(productInfo[1]).append(";")
                    .append(productInfo[2]).append(";").append(amount).append("|");
        }
        int length = sb.length();
        if (length > 0) {
            sb.deleteCharAt(length - 1);
        }
        return sb.toString();
    }

}