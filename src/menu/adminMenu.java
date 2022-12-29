package menu;

import product.Product;
import member.Member;
import order.Order;
import repository.dataManagement;

import java.util.Objects;

public class adminMenu extends generalMenu {
    public adminMenu() {
//        Product productService = new Product(super.getRepo(), super.getScanner());
//        Member memberService = new Member(super.getRepo());
//        Order orderService = new Order(super.getRepo(), super.getScanner());
//        Objects.requireNonNull(productService);
//        this.addOption(new Options("1", "Add new product to the store", productService::addNewProduct));
//        Objects.requireNonNull(productService);
//        this.addOption(new Options("2", "Remove product from the store", productService::removeProduct));
//        Objects.requireNonNull(productService);
//        this.addOption(new Options("3", "Update product price", productService::updatePrice));
//        Objects.requireNonNull(productService);
//        this.addOption(new Options("4", "View all products", productService::viewAllProducts));
//        Objects.requireNonNull(orderService);
//        this.addOption(new Options("5", "View all orders", orderService::getAllOrders));
//        Objects.requireNonNull(memberService);
//        this.addOption(new Options("6", "View all members", memberService::viewAllMembers));
//        Objects.requireNonNull(orderService);
//        this.addOption(new Options("7", "Get all orders from customer", orderService::getOrderByCustomerID));
//        Objects.requireNonNull(orderService);
//        this.addOption(new Options("8", "Change the status of the Order", orderService::changePaidStatus));
//        Objects.requireNonNull(orderService);
//        this.addOption(new Options("9", "Calculate the daily revenue", orderService::showOrderListAndCalculateTotalRevenue));
        this.addOption(new Options("10", "Log out", () -> {
            generalMenu menu = new generalMenu();
            menu.run();
        }));
        this.addOption(new Options("11", "Exit the program", () -> {
            System.out.println("Good bye see you again.");
            System.exit(0);
        }));
    }
}
