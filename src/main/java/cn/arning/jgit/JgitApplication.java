package cn.arning.jgit;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author arning
 */
@SpringBootApplication
public class JgitApplication {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(JgitApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
