import java.nio.file.Files;
import java.util.Scanner;

class Customer {
        public static Customer updateCustomerInformation(Customer customer) throws IOException {
            Scanner sc = new Scanner(System.in);
            System.out.printf("You are changing your personal information for %s!\n", customer.getUsername());
            System.out.printf("Your old name is '%s', please input your new name or type your old!\n", customer.getFullName());
            String newName = sc.nextLine();
            customer.setFullName(newName);
            System.out.printf("Your old phone number is %s, please input your new one or type your old!\n", customer.getPhoneNumber());
            String newPhoneNumber = sc.nextLine();
            customer.setPhoneNumber(newPhoneNumber);
            System.out.printf("Your old address is %s, please input your new one or type your old!\n", customer.getAddress());
            String newAddress = sc.nextLine();
            customer.setAddress(newAddress);
            System.out.printf("Your old email is %s, please input your new one or type your old!\n", customer.getEmail());
            String newEmail = sc.nextLine();
            customer.setEmail(newEmail);

            Path path = Paths.get(fileName);
            List<String> lines = Files.readAllLines(path);

            // Replace the customer information if it reaches the id
            for (int i = 0; i < lines.size(); i++) {
                String[] fields = lines.get(i).split(",");
                if (fields[0].equals(customer.getId())) {
                    fields[3] = customer.getUsername();
                    fields[4] = customer.getPhoneNumber();
                    fields[5] = customer.getEmail();
                    fields[6] = customer.getAddress();
                    lines.set(i, fields[0] + "," + fields[1] + "," + fields[2] + "," + fields[3] + "," + fields[4] + "," + fields[5] + "," + fields[6] + "," + fields[7]);
                    break;
                }
            }

            // Write the modified lines back to the text file
            Files.write(path, lines);
            return customer;

        }

        public static Customer updatePassword(Customer customer) throws IOException {
            Scanner sc = new Scanner(System.in);
            String username = customer.getUsername();
            System.out.printf("You are changing your passowrd for %s!\n", customer.getUsername());
            System.out.printf("Please input your old password\n", customer.getFullName());
            String oldPassword = sc.nextLine();

            Path path = Paths.get(fileName);
            List<String> lines = Files.readAllLines(path);

            // Replace the customer information if it reaches the id
            for (int i = 0; i < lines.size(); i++) {
                String[] fields = lines.get(i).split(",");
                if (fields[1].equals(username) && fields[2].equals(oldPassword)) {
                    do {
                        System.out.println("Your password is correct, please input the new password");
                        String newPassword = sc.nextLine();
                        System.out.println("Please re-input your new password");
                        String newRePassword = sc.nextLine();
                        if (newPassword.equals(newRePassword)) {
                            fields[2] = newPassword;
                            lines.set(i, fields[0] + "," + fields[1] + "," + fields[2] + "," + fields[3] + "," + fields[4] + "," + fields[5] + "," + fields[6] + "," + fields[7]);
                            break;
                        }

                    } while (true);

                    break;
                }
            }
            // Write the modified lines back to the text file
            Files.write(path, lines);
            System.out.println("Password changed successfully!");
            return customer;

        }
    }