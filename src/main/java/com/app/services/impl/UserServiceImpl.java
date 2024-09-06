package com.app.services.impl;

import com.app.constants.Role;
import com.app.dtos.UserDTO;
import com.app.dtos.UserInfoDTO;
import com.app.dtos.UserRegistrationDTO;
import com.app.exceptions.UserDeletedException;
import com.app.models.Hotel;
import com.app.repositories.HotelRepository;
import com.app.services.UserService;
import groovy.util.logging.Slf4j;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.app.models.User;
import com.app.repositories.UserRepository;
import com.app.exceptions.UsernameAlreadyExistsException;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HotelRepository hotelRepository;

    @Override
    @Transactional
    public User saveUser(UserRegistrationDTO registrationDTO) {
//        log.info("Attempting to save a new user: {}", registrationDTO.getUsername());

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(registrationDTO.getEmail()));
        if (existingUser.isPresent()){
            User user = existingUser.get();
            if (user.getDeletedAt() != null){
                throw new UserDeletedException("This email has been deleted. Please check your email to recover your account.");
            }
        }
        if (existingUser.isPresent()) {
            throw new UsernameAlreadyExistsException("This email is already registered!");
        }

        User user = mapRegistrationDtoToUser(registrationDTO);

        User savedUser = userRepository.save(user);
//        log.info("Successfully saved new user: {}", registrationDTO.getUsername());
        return savedUser;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id.longValue());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user;
        }
        return null;
    }

    @Override
    public Page<User> findPaginatedUsers(Pageable pageable) {
        return userRepository.findAllByDeletedAtIsNull(pageable);
    }

    private User mapRegistrationDtoToUser(UserRegistrationDTO registrationDTO) {
        Role userRole = registrationDTO.getRoleType(); //string
        return User.builder()
                .email(registrationDTO.getEmail().trim())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .name(formatText(registrationDTO.getName()))
                .role(userRole)
                .build();
    }

    @Override
    @Transactional
    public void updateLoggedInUser(UserDTO userDTO) {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userRepository.findByEmail(loggedInUsername);
//        log.info("Attempting to update logged in user with ID: {}", loggedInUser.getId());

        if (usernameExistsAndNotSameUser(userDTO.getEmail(), Long.valueOf(loggedInUser.getId()))) {
            throw new UsernameAlreadyExistsException("This username is already registered!");
        }

        setFormattedDataToUser(loggedInUser, userDTO);
        userRepository.save(loggedInUser);
//        log.info("Successfully updated logged in user with ID: {}", loggedInUser.getId());

        // Create new authentication token
        updateAuthentication(userDTO);
    }

    private void setFormattedDataToUser(User user, UserDTO userDTO) {
        user.setEmail(userDTO.getEmail());
        user.setName(formatText(userDTO.getName()));
    }

    private boolean usernameExistsAndNotSameUser(String email, Long userId) {
        Optional<User> existingUserWithSameUsername = Optional.ofNullable(userRepository.findByEmail(email));
        return existingUserWithSameUsername.isPresent() && !existingUserWithSameUsername.get().getId().equals(userId);
    }

    private String formatText(String text) {
        return StringUtils.capitalize(text.trim());
    }

    private void updateAuthentication(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name().toUpperCase()));

        UserDetails newUserDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );

        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
                newUserDetails,
                null,
                newUserDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }

    @Override
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username);
    }

    @Override
    public int countUsers() {
        return (int) userRepository.count();
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUser(Integer id, UserInfoDTO userDTO) {
        User user = userRepository.findById(Long.valueOf(id)).orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());

        userRepository.save(user);

    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id.longValue());
    }

    @Override
    public UserInfoDTO convertToDTO(User user) {
        return UserInfoDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .role(user.getRole())
                .hotelId(user.getHotel() != null ? user.getHotel().getId() : null)
                .build();
    }

    @Override
    public User convertToEntity(UserInfoDTO userDTO) {
        User user = User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .address(userDTO.getAddress())
                .role(userDTO.getRole())
                .build();

        // If hotelId is set, associate the user with a hotel
        if (userDTO.getHotelId() != null) {
            Hotel hotel = hotelRepository.findById(userDTO.getHotelId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid hotel ID"));
            user.setHotel(hotel);
        }

        return user;
    }

    @Override
    public void saveUserInfo(UserInfoDTO userDTO) {

        User user = userRepository.findById(Long.valueOf(userDTO.getId())).orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());

        userRepository.save(user);
    }

    @Override
    public void softDeleteUser(Integer id) {
        User user = userRepository.findById(id.longValue())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public void restoreUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null){
            user.setDeletedAt(null);
            userRepository.save(user);
        }
    }

}
