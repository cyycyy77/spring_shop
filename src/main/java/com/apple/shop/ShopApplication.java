package com.apple.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);

//		String lover = "computer";
//		int age = 23;
//		final int wifecount = 1;
//
////		var lover = "computer";
////		var age = 23;
//
//		System.out.println(wifecount);
//		System.out.println(lover);
//
////		var test = new Test();
////		test.hello();
//
//		var test = new Friend("lee");
//		System.out.println(test.name);
	}

}

class Test{
	String name = "kim";
	void hello(){
		System.out.println("안녕");
	}
}

class Friend{
	String name;
	int age = 20;
	Friend(String s){
		this.name = s;
	}
}