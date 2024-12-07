package com.example.finaljava;

import com.example.finaljava.Model.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication


public class FinalJavaApplication implements CommandLineRunner {



	public static void main(String[] args) {
		SpringApplication.run(FinalJavaApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		Main.main(args);
	}
}
