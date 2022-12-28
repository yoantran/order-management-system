/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Author: Tran Ngoc Hong Doanh
  ID: s3927023
  Acknowledgement:
  - https://docs.oracle.com/javase/7/docs/api/java/util/Scanner.html
*/

package order;

import repository.dataManagement;
import java.util.Scanner;
import Converter;


public class Order {

    private static final String[] labelFields = new String[]{"Order ID", "Customer ID", "Paid Status", "Product - Quantity", "Date", "Total Price"};
    private static dataManagement repo;
    private static Scanner scanner;
    private String oID;
    private String cID;
    private String paidStatus;
    private String productList = "";
    private String date;
    private double totalPrice;

    public Order(dataManagement repo, Scanner scanner){
        if (Order.repo == null) {
            Order.repo = repo;
        }

        if (Order.scanner == null) {
            Order.scanner = scanner;
        }
    }

    public Order(String memberID, dataManagement repo, Scanner scanner) {
        this.cID = memberID;
        if (Order.repo == null) {
            Order.repo = repo;
        }

        if (Order.scanner == null) {
            Order.scanner = scanner;
        }

    }

    public Order(String orderID, String cusID, String paidStatus, String productList, String date, double totalPrice) {
        String[] item = productList.split(" ");

        for(int i = 0; i < item.length - 1; ++i) {
            if (i % 2 == 0) {
                this.productList = this.productList + item[i] + "x" + item[i + 1];
            } else {
                this.productList = this.productList + ";";
            }
        }
        this.oID = orderID;
        this.cID = cusID;
        this.paidStatus = paidStatus;
        this.productList = productList;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public String getoID() {
        return oID;
    }

    public void setoID(String oID) {
        this.oID = oID;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(String paidStatus) {
        this.paidStatus = paidStatus;
    }

    public String getProductList() {
        return productList;
    }

    public void setProductList(String productList) {
        this.productList = productList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    private String convertProductList() {
        String res = this.productList;
        res = res.replace('x', ' ');
        res = res.replace(';', ' ');
        return res;
    }

    public String toDataLine() {
        String var10000 = this.oID;
        return var10000 + "," + this.cID + "," + this.paidStatus + "," + this.convertProductList() + "," + this.date + "," + Converter.toDecimal(this.totalPrice) + "\n";
    }


}
