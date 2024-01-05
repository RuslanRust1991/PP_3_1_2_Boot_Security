package ru.kata.spring.boot_security.demo.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;
import java.util.List;

@Component
public class Initialization implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleService roleService;
    private final UserService userService;

    public Initialization(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Role userRole = new Role("ROLE_USER");
        if (roleService.getByName("ROLE_USER") == null) {
            roleService.save(userRole);
        }
        Role adminRole = new Role("ROLE_ADMIN");
        if (roleService.getByName("ROLE_ADMIN") == null) {
            roleService.save(adminRole);
        }
        if (userService.findByUsername("user") == null) {
            User user = new User();
            user.setUsername("user");
            user.setPassword("user");
            user.setHobby("test_user");
            user.setRoles(List.of(userRole));
            userService.saveUser(user);
        }
        if (userService.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setHobby("test_admin");
            admin.setRoles(List.of(adminRole));
            userService.saveUser(admin);
        }
    }
}
