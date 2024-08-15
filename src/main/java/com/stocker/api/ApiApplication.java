package com.stocker.api;

import com.stocker.api.domain.entity.Role;
import com.stocker.api.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class ApiApplication implements CommandLineRunner {
	private final RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createRoleIfNotFound(1L, "ADMIN", "Administrator role");
		createRoleIfNotFound(2L, "USER", "Default user role");
	}

	private void createRoleIfNotFound(Long id, String roleName, String description) {
		if (!roleRepository.findByName(roleName).isPresent()) {

			Role role = new Role();
			role.setId(id);
			role.setName(roleName);
			role.setDescription(description);
			roleRepository.save(role);
		}
	}
}
