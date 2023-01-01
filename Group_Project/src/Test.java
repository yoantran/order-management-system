import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Test {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        Customer cus1 = new Customer();
        cus1.verifyCustomerLogin();
        System.out.println(cus1.toString());
        Customer.updateInfo(cus1);
    }
}
