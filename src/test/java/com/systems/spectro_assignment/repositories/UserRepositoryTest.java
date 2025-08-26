package com.systems.spectro_assignment.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.systems.spectro_assignment.models.Role;
import com.systems.spectro_assignment.models.User;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

 
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    
   
    @Autowired
    public UserRepositoryTest(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}



	/**
     * Test saving a User with a Role and retrieving it by username.
     * Verifies that the user exists and has the correct role assigned.
     */
    @Test
    void userRepository_save_getByUserName() {
        Role role = new Role();
        role.setName("USER");
        roleRepository.save(role);

        User user = new User();
        user.setUsername("ahmed");
        user.setPassword("123456");
        user.setRoles(Set.of(role));

        userRepository.save(user);

        var optionalUser = userRepository.findByUsername("ahmed");
        assertThat(optionalUser).isPresent();
        assertThat(optionalUser.get().getUsername()).isEqualTo("ahmed");
        assertThat(optionalUser.get().getRoles()).contains(role);
    }

}
