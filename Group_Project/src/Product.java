public class Product {
    public static void writeProductToDatabase(Product product, String filename) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            String s = String.valueOf(product.getPrice());
            bw.append(product.getId()).append(",").append(product.getProductName()).append(",").append(s).append(",").append(product.getCategory()).append("\n");

            bw.close(); // close the BufferedWriter object
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing to database file: " + e.getMessage());
        }
    }
    public static void removeProductById (String id) throws IOException {
        Method.removeById(id, fileName);
    }

    public static void removeProductByName (String name) throws IOException {
        Method.removeByName(name, fileName);
    }

    public static List<Product> listProducts() throws IOException {
        // Read the category data from the text file
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Parse the lines into a list of Category objects
        List<Product> products = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            products.add(new Product(fields[0], fields[1], parseInt(fields[2]), fields[3]));
        }

        return products;
    }

}
