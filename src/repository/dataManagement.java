/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Author: Tran Ngoc Hong Doanh
  ID: s3927023
  Acknowledgement:
  - https://www.geeksforgeeks.org/java-io-bufferedreader-class-java/
  - https://www.w3schools.com/java/java_arraylist.asp
  - https://www.javatpoint.com/java-bufferedwriter-class
  - https://viettuts.vn/java-collection/su-dung-iterator-trong-java
*/
package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import order.Order;
import product.Product;
import member.Member;

public class dataManagement {
    public ArrayList<Order> readOrderList() {
        String orderItem = "";
        ArrayList<Order> orderList = new ArrayList();

        try {
            BufferedReader readOrder = new BufferedReader(new FileReader("repository/Order.csv"));

            while(orderItem != null) {
                if ((orderItem = readOrder.readLine()) != null) {
                    String[] item = orderItem.split(",");
                    Order newOrder = new Order(item[0], item[1], item[2], item[3], item[4], Double.parseDouble(item[5]));
                    orderList.add(newOrder);
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return orderList;
    }
    public ArrayList<Product> readProductList() {
        String productItem = "";
        ArrayList<Product> productList = new ArrayList();

        try {
            BufferedReader readProduct = new BufferedReader(new FileReader("repository/items.csv"));

            while(productItem != null) {
                if ((productItem = readProduct.readLine()) != null) {
                    if (productItem.equals("")) {
                        productList.add(new Product());
                    } else {
                        String[] item = productItem.split(",");
                        Product newProduct = new Product(item[0], item[1], item[2], item[3], Double.parseDouble(item[4]));
                        productList.add(newProduct);
                    }
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return productList;
    }

    public ArrayList<Member> readCustomerList() {
        String memberItem = "";
        ArrayList<Member> memberList = new ArrayList();

        try {
            BufferedReader readMember = new BufferedReader(new FileReader("repository/customers.csv"));

            while(memberItem != null) {
                if ((memberItem = readMember.readLine()) != null) {
                    String[] item = memberItem.split(",");
                    Member newMember = new Member(item[0], item[1], item[2], item[3], item[4], item[5], Double.parseDouble(item[6]));
                    memberList.add(newMember);
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return memberList;
    }

    public void writeProductFile(ArrayList<Product> ProductList, boolean append) {
        try {
            BufferedWriter DataWriter = new BufferedWriter(new FileWriter("repository/Products.csv", append));
            Iterator var4 = ProductList.iterator();

            while(var4.hasNext()) {
                Product product = (Product)var4.next();
                if (product.getpID().equals("UNKNOWN")) {
                    DataWriter.write("\n");
                } else {
                    DataWriter.write(product.toString());
                }
            }

            DataWriter.close();
        } catch (IOException var6) {
            System.out.println("An error occurred.");
            var6.printStackTrace();
        }

    }
//
    public void writeCustomerFile(ArrayList<Member> MemberList, boolean append) {
        try {
            BufferedWriter DataWriter = new BufferedWriter(new FileWriter("repository/members.csv", append));
            Iterator var4 = MemberList.iterator();

            while(var4.hasNext()) {
                Member member = (Member)var4.next();
                DataWriter.write(member.toString());
            }

            DataWriter.close();
        } catch (IOException var6) {
            System.out.println("An error occurred.");
            var6.printStackTrace();
        }

    }

    public void writeOrderFile(ArrayList<Order> OrderList, boolean append) {
        try {
            BufferedWriter DataWriter = new BufferedWriter(new FileWriter("repo/Orders.csv", append));
            Iterator var4 = OrderList.iterator();

            while(var4.hasNext()) {
                Order order = (Order)var4.next();
                DataWriter.write(order.toString());
            }

            DataWriter.close();
        } catch (IOException var6) {
            System.out.println("An error occurred.");
            var6.printStackTrace();
        }

    }

    public void writeFile(String fileName, String data, boolean append) {
        try {
            BufferedWriter DataWriter = new BufferedWriter(new FileWriter(fileName, append));
            DataWriter.write(data);
            DataWriter.close();
        } catch (IOException var5) {
            System.out.println("An error occurred.");
            var5.printStackTrace();
        }

    }

}
