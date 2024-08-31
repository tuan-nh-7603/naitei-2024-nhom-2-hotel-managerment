package com.app.initialize;


import com.app.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.app.models.User;
import com.app.constants.Role;

import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {

        try {
            log.warn("Checking if test data persistence is required...");

            if (userRepository.count() == 0) {
                log.info("Initiating test data persistence");


                User user1 = User.builder().email("admin@hotel.com").password(passwordEncoder.encode("123456")).name("Admin").role(Role.valueOf("ADMIN")).build();
                User user2 = User.builder().email("customer1@hotel.com").password(passwordEncoder.encode("123456")).name("Kaya Alp").role(Role.valueOf("CUSTOMER")).build();
                User user3 = User.builder().email("manager1@hotel.com").password(passwordEncoder.encode("123456")).name("John").role(Role.valueOf("HOTEL_STAFF")).build();
                User user4 = User.builder().email("manager2@hotel.com").password(passwordEncoder.encode("123456")).name("Max").role(Role.valueOf("HOTEL_STAFF")).build();


                userRepository.save(user1);
                userRepository.save(user2);
                userRepository.save(user3);
                userRepository.save(user4);



            } else {
                log.info("Test data persistence is not required");
            }
            log.warn("App ready");
        } catch (DataAccessException e) {
            log.error("Exception occurred during data persistence: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected exception occurred: " + e.getMessage());
        }
    }
}
