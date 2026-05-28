package com.luan.exception;

import java.util.function.Supplier;

public class Exceptions {
		
	public static Supplier<RuntimeException> lanca(String mensagem) {
		return () -> new RuntimeException(mensagem);
	}
	
}
