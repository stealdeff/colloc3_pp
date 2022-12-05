package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class PayByPayPal implements PayStrategy {
    private static final Map<String, String> DATA_BASE = new HashMap<>();
    private final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private String email;
    private String password;
    private boolean signedIn;
    public void setEmail(String email) {
        this.email= email;
    }
    public void setPassword(String password) {
        this.password= password;
    }

    static {
        DATA_BASE.put("amanda1985", "amanda@ya.com");
        DATA_BASE.put("qwerty", "john@amazon.eu");
    }
    public static boolean isEmail(String email) {
        var regexp = new String("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");
        return email.matches(regexp);
    }
public static boolean isPassword(String password)
{
    var regexp=new String("^.*(?=.{8,})(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!#$%&? \"]).*$\n");
    return password.matches(regexp);
}
    @Override
    public void collectPaymentDetails() {
        try {
            var item=new PayByPayPal();
            while (!signedIn) {
                System.out.print("Enter the user's email: ");
                email = READER.readLine();
                if(isEmail(email)) {
                    {
                        item.setEmail(email);
                    }
                    System.out.print("Enter the password: ");
                }
                else System.out.println("No");
                password = READER.readLine();
                if(isPassword(password))
                {
                    {
                        item.setPassword(password);
                    }
                    if (verify()) {
                        System.out.println("Data verification has been successful.");
                    } else {
                        System.out.println("Wrong email or password!");
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean verify() {
        setSignedIn(email.equals(DATA_BASE.get(password)));
        return signedIn;
    }

    @Override
    public boolean pay(int paymentAmount) {
        if (signedIn) {
            System.out.println("Paying " + paymentAmount + " using PayPal.");
            return true;
        } else {
            return false;
        }
    }

    private void setSignedIn(boolean signedIn) {
        this.signedIn = signedIn;
    }
}