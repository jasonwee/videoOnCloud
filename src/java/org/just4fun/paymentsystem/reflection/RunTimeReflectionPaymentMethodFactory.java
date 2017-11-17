package org.just4fun.paymentsystem.reflection;

import java.util.Optional;

import org.just4fun.paymentsystem.PaymentMethod;

public class RunTimeReflectionPaymentMethodFactory {
	
	public static Optional<PaymentMethod> getPaymentMethod(String qualifiedName) {
		try {
			Class<?> clazz = Class.forName(qualifiedName);
			PaymentMethod method = (PaymentMethod) clazz.newInstance();
			return Optional.of(method);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException e) {
			return Optional.empty();
		}
	}

}
