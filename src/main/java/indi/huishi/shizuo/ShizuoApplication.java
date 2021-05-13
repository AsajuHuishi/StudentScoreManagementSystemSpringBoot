package indi.huishi.shizuo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = "classpath:kaptcha.xml")
@SpringBootApplication
public class ShizuoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShizuoApplication.class, args);
    }

}
