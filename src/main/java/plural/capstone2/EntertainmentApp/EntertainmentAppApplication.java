package plural.capstone2.EntertainmentApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class })
public class EntertainmentAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntertainmentAppApplication.class, args);
	}
}
