package com.sgu.domain.enuns;

public enum TokenType {

	AUTHENTICATION(1, "Autenticação"),
	
	RECOVER_PASSWORD(2, "Recuperar senha"),
	
	REGISTRATION(3, "Cadastro"), 
	
	NotificationInactiveAccount(4, "Notificação de conta inativa");

	private final int value;

	private final String description;

	TokenType(int value, String description) {
		this.value = value;
		this.description = description;
	}

	public int value() {
		return value;
	}
	
	public String description() {
		return description;
	}

	public static TokenType toEnum(Integer type) {
		if (type == null) {
			return null;
		}
		
		for (TokenType tokenType : TokenType.values()) {
			if (type.equals(tokenType.value())) {
				return tokenType;
			}
		}
		
		throw new IllegalArgumentException("ID inválido: " + type);
	}
}
