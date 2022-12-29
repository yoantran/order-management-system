package product;

import repository.dataManagement;
import repository.Converter;

import java.util.Scanner;


public class Product {
    private static final String[] labelFields = new String[]{"Product ID", "Product Name", "Product Category", "Description", "Price"};
    private static dataManagement repo;
    private static Scanner scanner;
    private String pID;
    private String pName;
    private String pCategory;
    private String description;
    private double price;

    public Product() {
        this.pID = "UNKNOWN";
        this.pName = "UNKNOWN";
        this.pCategory = "UNKNOWN";
        this.description = "UNKNOWN";
        this.price = 0.0;
    }

    public Product(dataManagement repo, Scanner scanner) {
        this.pID = "UNKNOWN";
        this.pName = "UNKNOWN";
        this.pCategory = "UNKNOWN";
        this.description = "UNKNOWN";
        this.price = 0.0;
        if (Product.repo == null) {
            Product.repo = repo;
        }

        if (Product.scanner == null) {
            Product.scanner = scanner;
        }

    }

    public Product(String pID, String pName, String pCategory, String description, double price) {
        try {
            this.pID = pID;
            this.pName = pName;
            this.pCategory = pCategory;
            this.description = description;
            this.price = price;
        } catch (Exception var8) {
            System.out.println("" + var8 + "Invalid DataType");
        }
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static String[] getLabelFields() {
        return labelFields;
    }

}
