package main.java.mx.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VideoCloudApplication {

	public static void main(String[] args) {
		VideoCloudApplication.run(VideoCloudApplication.class, args);
	}

}