package com.helloworld.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws IOException {
		ClassLoader classLoader = DemoApplication.class.getClassLoader();

		File file = new File(Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json")).getFile());

		System.out.println("THIS IS THE PATH: "+file.getAbsolutePath()+" GOOFY AHH");
		FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());

		@SuppressWarnings("deprecation")
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://springbootdemo-772ee-default-rtdb.firebaseio.com")
				.build();

		FirebaseApp.initializeApp(options);

		SpringApplication.run(DemoApplication.class, args);
	}
}
