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
            BufferedReader orderReader = new BufferedReader(new FileReader("repository/Order.csv"));

            while(orderItem != null) {
                if ((orderItem = orderReader.readLine()) != null) {
                    String[] singleItem = orderItem.split(",");
                    Order newOrder = new Order(singleItem[0], singleItem[1], singleItem[2], singleItem[3], singleItem[4], Double.parseDouble(singleItem[5]));
                    orderList.add(newOrder);
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return orderList;
    }
//    public ArrayList<Product> readProductList() {
//        String productItem = "";
//        ArrayList<Product> productList = new ArrayList();
//
//        try {
//            BufferedReader productReader = new BufferedReader(new FileReader("repository/items.csv"));
//
//            while(productItem != null) {
//                if ((productItem = productReader.readLine()) != null) {
//                    if (productItem.equals("")) {
//                        productList.add(new Product());
//                    } else {
//                        String[] singleItem = productItem.split(",");
//                        Product newProduct = new Product(singleItem[0], singleItem[1], singleItem[2], singleItem[3], Double.parseDouble(singleItem[4]));
//                        productList.add(newProduct);
//                    }
//                }
//            }
//        } catch (Exception var6) {
//            var6.printStackTrace();
//        }
//
//        return productList;
//    }

//    public ArrayList<Member> readCustomerList() {
//        String userItem = "";
//        ArrayList<Member> memberList = new ArrayList();
//
//        try {
//            BufferedReader userReader = new BufferedReader(new FileReader("repository/customers.csv"));
//
//            while(userItem != null) {
//                if ((userItem = userReader.readLine()) != null) {
//                    String[] singleItem = userItem.split(",");
//                    Member newUser = new Member(singleItem[0], singleItem[1], singleItem[2], singleItem[3], singleItem[4], singleItem[5], Double.parseDouble(singleItem[6]));
//                    memberList.add(newUser);
//                }
//            }
//        } catch (Exception var6) {
//            var6.printStackTrace();
//        }
//
//        return memberList;
//    }
//
//    public void writeProductFile(ArrayList<Product> ProductList, boolean append) {
//        try {
//            BufferedWriter DataWriter = new BufferedWriter(new FileWriter("repository/Products.csv", append));
//            Iterator var4 = ProductList.iterator();
//
//            while(var4.hasNext()) {
//                Product product = (Product)var4.next();
//                if (product.getProductID().equals("UNKNOWN")) {
//                    DataWriter.write("\n");
//                } else {
//                    DataWriter.write(product.toDataLine());
//                }
//            }
//
//            DataWriter.close();
//        } catch (IOException var6) {
//            System.out.println("An error occurred.");
//            var6.printStackTrace();
//        }
//
//    }
//
//    public void writeMemberFile(ArrayList<Member> MemberList, boolean append) {
//        try {
//            BufferedWriter DataWriter = new BufferedWriter(new FileWriter("repository/members.csv", append));
//            Iterator var4 = MemberList.iterator();
//
//            while(var4.hasNext()) {
//                Member member = (Member)var4.next();
//                DataWriter.write(member.toDataLine());
//            }
//
//            DataWriter.close();
//        } catch (IOException var6) {
//            System.out.println("An error occurred.");
//            var6.printStackTrace();
//        }
//
//    }

    public void writeOrderFile(ArrayList<Order> OrderList, boolean append) {
        try {
            BufferedWriter DataWriter = new BufferedWriter(new FileWriter("repo/Orders.csv", append));
            Iterator var4 = OrderList.iterator();

            while(var4.hasNext()) {
                Order order = (Order)var4.next();
                DataWriter.write(order.toDataLine());
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
