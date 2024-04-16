package com.spring.battleship.battleshipServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class BattleshipServerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BattleshipServerApplication.class, args);
	}

}
