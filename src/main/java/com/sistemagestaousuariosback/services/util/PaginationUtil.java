package com.sistemagestaousuariosback.services.util;

import com.sistemagestaousuariosback.services.exceptions.InvalidParameterException;

public class PaginationUtil {
	
	public static Integer normalizePage(Integer page) {
		if (page < 0) {
			throw new InvalidParameterException("linesPerPage", "O valor [" + page + "] é inválido. Aceitos: Valores maiores que 0.");
		}
		
		if (page != 0) {
			page -= 1;
		}
		
		return page;
	}
}
