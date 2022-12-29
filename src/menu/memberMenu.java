package menu;

import order.Order;

import java.util.Objects;

public class memberMenu extends generalMenu {
    public memberMenu(String mID) {
//        Order orderService = new Order(mID, super.getRepo(), super.getScanner());
//        Objects.requireNonNull(orderService);
//        this.addOption(new Options("1", "Create order", orderService::createOrder));
//        Objects.requireNonNull(orderService);
//        this.addOption(new Options("2", "Search order", orderService::getOrderByOrderID));
//        Objects.requireNonNull(orderService);
//        this.addOption(new Options("3", "View your orders", orderService::getAllOrderOfMember));
        this.addOption(new Options("4", "Log out", () -> {
            generalMenu menu = new generalMenu();
            menu.run();
        }));
        this.addOption(new Options("5", "Exit the program", () -> {
            System.out.println("Good bye see you again.");
            System.exit(0);
        }));
    }
}
