package com.loesoft.spring.aop.injection.bug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;

@Configuration
@EnableRetry
public class Proof {

	public static void main(String... args) {
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Proof.class);
		context.registerShutdownHook();
	}
	
	@Bean
	public BeanThing beanThing() {
		return new BeanThing();
	}
	
	@Bean
	@Autowired
	public BeanNeedy beanNeedy(BeanThing beanThing) {
		return new BeanNeedy(beanThing);
	}
	
	public static interface BeanInterface {
		public void doSomething();
	}

	public static class BeanThing implements BeanInterface {

		@Retryable
		public void doSomething() {
			System.out.println("BeanNeedingDependencies doing something");
		}
		
	}
	
	public static class BeanNeedy {
		
		private final BeanThing beanThing;
		
		public BeanNeedy(BeanThing beanThing) {
			this.beanThing = beanThing;
		}
	}

}
