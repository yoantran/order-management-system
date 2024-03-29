/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Authors:
  Nguyen Nhat Minh
  Luu Quoc Nhat
  Tran Ngoc Hong Doanh
  To Gia Hy
  ID:
  S3932112
  S3924942
  s3927023
  S3927539
  Acknowledgement: None
*/

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Admin extends Account {
    public Admin(String id, String username, String password) throws NoSuchAlgorithmException {
        super(id, username, password);
    }


    public static double calculateTotalRevenue(List<Order> orders) {
        double totalRevenue = 0;
        for (Order order : orders) {
            totalRevenue += order.getCart().getTotalAmount();
        }
        return totalRevenue;
    }

    public static Map<Product, Integer> countTotalProducts(List<Order> orders) {
        Map<Product, Integer> productCounts = new HashMap<>();
        for (Order order : orders) {
            Cart cart = order.getCart();
            Map<Product, Integer> products = cart.getProducts();
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product product = entry.getKey();
                int count = entry.getValue();
                // Increment the count for this product
                int currentCount = productCounts.getOrDefault(product, 0) + count;
                productCounts.put(product, currentCount);
            }
        }
        return productCounts;
    }

    public static void findMostPopularProduct(List<Order> orders) {
        Map<Product, Integer> productCounts = countTotalProducts(orders);
        // Find the product with the highest count
        Product mostPopularProduct = null;
        int highestCount = 0;
        for (Map.Entry<Product, Integer> entry : productCounts.entrySet()) {
            if (entry.getValue() > highestCount) {
                mostPopularProduct = entry.getKey();
                highestCount = entry.getValue();
            }
        }
        assert mostPopularProduct != null;
        System.out.printf("The current most popular product is %s, with id %s, is sold %s time!\n", mostPopularProduct.getProductName(), mostPopularProduct.getId(), highestCount);
    }

    public static void findLeastPopularProduct(List<Order> orders) {
        Map<Product, Integer> productCounts = countTotalProducts(orders);
        // Find the product with the highest count
        Product leastPopularProduct = null;
        int lowestCount = Integer.MAX_VALUE;
        for (Map.Entry<Product, Integer> entry : productCounts.entrySet()) {
            if (entry.getValue() < lowestCount) {
                leastPopularProduct = entry.getKey();
                lowestCount = entry.getValue();
            }
        }

        assert leastPopularProduct != null;
        System.out.printf("The current least popular product is %s, with id %s, is sold %s time!\n", leastPopularProduct.getProductName(), leastPopularProduct.getId(), lowestCount);
    }

    public static void findBiggestSpender(List<Order> orders) throws IOException, NoSuchAlgorithmException {
        Map<String, Double> customerTotals = new HashMap<>();
        for (Order order : orders) {
            String customerId = order.getCustomer();
            double totalAmount = order.getCart().getTotalAmount();
            // Increment the total amount paid by this customer
            double currentTotal = customerTotals.getOrDefault(customerId, 0.0) + totalAmount;
            customerTotals.put(customerId, currentTotal);
        }

        // Find the customer who has paid the most
        Customer biggestSpender = null;
        double highestTotal = Double.MIN_VALUE;
        for (Map.Entry<String, Double> entry : customerTotals.entrySet()) {
            if (entry.getValue() > highestTotal) {
                biggestSpender = Customer.findCustomerById(entry.getKey());
                highestTotal = entry.getValue();
            }
        }

        assert biggestSpender != null;
        System.out.printf("The biggest spender of the shop is %s, id %s, with the total order of %.2f\n", biggestSpender.getUsername(), biggestSpender.getId(), highestTotal);

    }


    public static void countMembership(List<Customer> customers) {
        int regularCount = 0;
        int silverCount = 0;
        int goldCount = 0;
        for (Customer customer : customers) {
            String membership = customer.getMembership();
            switch (membership) {
                case "Regular":
                    regularCount++;
                    break;
                case "Silver":
                    silverCount++;
                    break;
                case "Gold":
                    goldCount++;
                    break;
            }
        }
        System.out.println("Number of Regular members: " + regularCount);
        System.out.println("Number of Silver members: " + silverCount);
        System.out.println("Number of Gold members: " + goldCount);
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
}