package org.just4fun.paymentsystem;

import java.util.Optional;

import org.just4fun.paymentsystem.reflection.RunTimeReflectionPaymentMethodFactory;

/**
 * https://dzone.com/articles/java-the-strategy-pattern
 * 
 * @author jason
 *
 */
public class Application {
	
	private static final int PAYMENT_TYPE_INDEX = 0;

	public static void main(String[] args) {
		
		Bill bill = new Bill();
		bill.addLineItem(new LineItem("roti", 200));
		bill.addLineItem(new LineItem("tomyam", 400));
		
		// can improve using human pay bill
		bill.pay(PaymentMethodFactory.getPaymentMethod(args[PAYMENT_TYPE_INDEX]));
		
		
		//------------------- using reflection
		
		Optional<PaymentMethod> runTimePaymentMethod = RunTimeReflectionPaymentMethodFactory.getPaymentMethod("org.just4fun.paymentsystem.Cash");
		runTimePaymentMethod.ifPresent(method -> bill.pay(method));
		
		Optional<PaymentMethod> staticPaymentMethod = StaticReflectionPaymentMethodFactory.getPaymentMethod();
		staticPaymentMethod.ifPresent(method -> bill.pay(method));
	}

}
