package ru.maltsev.propvuedemo;

import org.springframework.boot.SpringApplication;

public class TestPropvueDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(PropvueDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
