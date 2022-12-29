package menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import repository.dataManagement;
import menu.Options;

public class generalMenu {
    private final dataManagement data = new dataManagement();
    private final List<Options> options = new ArrayList();
    private final Scanner scanner;

    public generalMenu() {
        this.scanner = new Scanner(System.in);
    }

    public Scanner getScanner() {
        return this.scanner;
    }

    public dataManagement getData() {
        return this.data;
    }

    protected void addOption(Options option) {
        this.options.add(option);
    }

    protected void displayOptions() {
//        TableFormatterService table = new TableFormatterService(Option.getFields());
//        Iterator var2 = this.options.iterator();
//
//        while(var2.hasNext()) {
//            Option option = (Option)var2.next();
//            table.addRows(option.toStringArray());
//        }
//
//        table.display();
    }



    private void welcomeScreen() {
        this.printDelimiter();
        System.out.println("COSC2081 GROUP ASSIGNMENT \nSTORE ORDER MANAGEMENT SYSTEM \nInstructor: Mr. Tom Huynh \nGroup: HTML Team \n");
        this.printDelimiter();
    }

    private void printDelimiter() {
        System.out.println("---------------------------------------------------------------------------------------------");
    }

    public void run() {
        this.welcomeScreen();

        while(true) {
            String input;
            while(true) {
                this.displayOptions();
                System.out.print("Enter an option: ");

                try {
                    input = String.valueOf(this.scanner.nextInt());
                    break;
                } catch (Exception var4) {
                    this.scanner.nextLine();
                }
            }

            this.scanner.nextLine();
            Iterator var2 = this.options.iterator();

            while(var2.hasNext()) {
                Options option = (Options)var2.next();
                if (option.getInput().equals(input)) {
                    option.execute();
                }
            }
        }
    }
}
