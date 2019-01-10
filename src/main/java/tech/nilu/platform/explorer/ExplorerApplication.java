package tech.nilu.platform.explorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class ExplorerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExplorerApplication.class, args);
	}
}
