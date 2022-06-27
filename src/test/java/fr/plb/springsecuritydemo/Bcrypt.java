package fr.plb.springsecuritydemo;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Bcrypt {

    @Test
    public void testBcrypt() {
        String password = "M0nSuperP@asswordS3cured";
        for (int force = 10; force < 19; force++) {
            long startTime = System.currentTimeMillis();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(force);
            System.out.println(encoder.encode(password));
            System.out.println("Strength: "+force+" - ("+(System.currentTimeMillis() - startTime)+" ms)");
        }
    }
}
