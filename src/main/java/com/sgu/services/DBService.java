package com.sgu.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sgu.domain.Policy;
import com.sgu.domain.User;
import com.sgu.domain.UserPolicies;
import com.sgu.repositories.PolicyRepository;
import com.sgu.repositories.UserPoliciesRepository;
import com.sgu.repositories.UserRepository;
import com.sgu.services.util.DateTimeUtil;

@Service
public class DBService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCrypt;

	@Autowired
	private PolicyRepository policyRepository;

	@Autowired
	private UserPoliciesRepository userPoliciesRepository;
	
	public void instantiateTestDatabase() {
		generateUsers();
		generatePolicies();
	}

	private void generateUsers() {
		String password = bCrypt.encode("123");
		Date passwordExpiresAt = DateTimeUtil.getDateWithAddMonth(3);

		userRepository.save(
			new User("Vinicius Pugliesi", "vinicius_pugliesi@outlook.com", password, passwordExpiresAt)
		);
		
		userRepository.save(new User("Thales Erick Márcio Figueiredo", "thaleserickmarciofigueiredo@outlook.com",
				password, passwordExpiresAt));

		userRepository.save(
				new User("Gabriel Caio Freitas", "gabrielcaiofreitas-74@outlook.com", password, passwordExpiresAt));

		userRepository.save(new User("Anderson Sebastião Ramos", "andersonsebastiaoramos-72@outlook.com", password,
				passwordExpiresAt));

		userRepository.save(new User("Miguel Fernando Marcos Vinicius Lima",
				"mmiguelfernandomarcosviniciuslima@outlook.com", password, passwordExpiresAt));

		userRepository.save(new User("Gabriel Marcos Vinicius Leandro Lopes",
				"gabrielmarcosviniciusleandrolopes-97@outlook.com", password, passwordExpiresAt));

		userRepository
				.save(new User("Cauê Calebe Duarte", "cauecalebeduarte-81@outlook.com", password, passwordExpiresAt));
	}
	
	private void generatePolicies() {
		User user = userRepository.findByEmail("vinicius_pugliesi@outlook.com");
		Policy policy = null;
		
		policy = policyRepository.save(new Policy("Access.Control.User.All", "Acesso total a funções do usuário"));
		policyRepository.save(new Policy("Access.Control.User.View", "Acesso a visualização de usuário"));
		policyRepository.save(new Policy("Access.Control.User.Update", "Acesso a editar de usuário"));
		policyRepository.save(new Policy("Access.Control.User.Delete", "Acesso a deleção de usuário"));

		userPoliciesRepository.save(new UserPolicies(null, policy, user));
		
		policy = policyRepository.save(new Policy("Access.Control.Log.All", "Acesso total a funções de log"));
		policyRepository.save(new Policy("Access.Control.Log.View", "Acesso visualização de log"));
		policyRepository.save(new Policy("Access.Control.Log.Delete", "Acesso a deleção de log"));

		userPoliciesRepository.save(new UserPolicies(null, policy, user));
	}
}
