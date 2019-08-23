package com.sgu.dto.annotations.validations.utils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

@Component
public class NativeQueryUtil {

    @PersistenceContext
    private EntityManager entityManager;

	public NativeQueryUtil() {
	}

	public List<?> queryBuilder(String table, String collumn, String value, String conditions) {
		String sql = "SELECT " + collumn + " FROM " + table + " WHERE " + collumn + " = '" + value + "'";
		
		sql = conditions(sql, conditions);
		
		return entityManager.createNativeQuery(sql).getResultList();
	}
	
	private String conditions(String sql, String conditions) {
		if (conditions == null || conditions.isEmpty()) {
			return sql;
		}
		
		if (! conditions.matches(".*\\$\\{[a-zA-Z]*\\}.*")) {
			sql += " AND " + conditions;
		}
		
		return sql;
		

//		ResetPasswordDTO resetPasswordDTO = (ResetPasswordDTO) InputReaderUtil.getInput();
//		
//		System.out.println(resetPasswordDTO);
		
//		try {
//			ResetPasswordDTO resetPasswordDTO = new ObjectMapper().readValue(request.getReader(), ResetPasswordDTO.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
//        Pattern pattern = Pattern.compile("\\$\\{[a-zA-Z]*\\}");
//        Matcher matcher = pattern.matcher(conditions);
//
//        while (matcher.find()) {
//			String variable = matcher.group().replace("${", "").replace("}", "");
//			String value = request.getParameter(variable);
//			conditions.replace(variable, value);
//        }
	}
}
