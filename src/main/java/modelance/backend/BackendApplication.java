package modelance.backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import modelance.backend.config.security.RsaKeyProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class BackendApplication {

	public static void main(String[] args) {
		try {
			InputStream serviceAccount = new FileInputStream("./serviceAccount.json");
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setStorageBucket("modelance-84abf.appspot.com")
					.build();

			FirebaseApp.initializeApp(options);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		SpringApplication.run(BackendApplication.class, args);
	}

}
