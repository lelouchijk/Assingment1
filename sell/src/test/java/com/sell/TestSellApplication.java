package com.sell;

import org.springframework.boot.SpringApplication;

public class TestSellApplication {

	public static void main(String[] args) {
		SpringApplication.from(SellApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
