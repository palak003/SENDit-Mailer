package com.mainproject.useraccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UseraccountApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(UseraccountApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
