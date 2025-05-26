package id.ac.ui.cs.advprog.admin;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        if (System.getenv("ENV") == null || System.getenv("ENV").equals("LOCAL")) {
            Dotenv dotenv = Dotenv.load();
            System.setProperty("DB_URL", dotenv.get("DB_URL"));
            System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
            System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
            System.setProperty("EXTERNAL_TRANSACTION_API_URL", dotenv.get("EXTERNAL_TRANSACTION_API_URL"));
            System.setProperty("EXTERNAL_CAMPAIGN_API_URL", dotenv.get("EXTERNAL_CAMPAIGN_API_URL"));
            System.setProperty("USER_API_URL", dotenv.get("USER_API_URL"));
            System.setProperty("USER_API_LOGIN_URL", dotenv.get("USER_API_LOGIN_URL"));
            System.setProperty("USER_API_ADMIN_EMAIL", dotenv.get("USER_API_ADMIN_EMAIL"));
            System.setProperty("USER_API_ADMIN_PASSWORD", dotenv.get("USER_API_ADMIN_PASSWORD"));
            System.setProperty("USER_API_JWT_TOKEN", dotenv.get("USER_API_JWT_TOKEN"));
        }

        SpringApplication.run(AdminApplication.class, args);
    }

}
