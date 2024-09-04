package com.app.initialize;


import com.app.constants.*;
import com.app.models.*;
import com.app.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final BookingRoomRepository bookingRoomRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void run(String... args) {

        try {
            log.warn("Checking if test data persistence is required...");

            // Creating users
            User admin = User.builder().email("admin@hotel.com").password(passwordEncoder.encode("123456")).name("Admin").role(Role.ADMIN).build();
            User customer = User.builder().email("customer1@hotel.com").password(passwordEncoder.encode("123456")).name("Kaya Alp").role(Role.CUSTOMER).build();
            User staff1 = User.builder().email("manager1@hotel.com").password(passwordEncoder.encode("123456")).name("John").role(Role.HOTEL_STAFF).build();
            User staff2 = User.builder().email("manager2@hotel.com").password(passwordEncoder.encode("123456")).name("Max").role(Role.HOTEL_STAFF).build();

            userRepository.save(admin);
            userRepository.save(customer);
            userRepository.save(staff1);
            userRepository.save(staff2);

            // Creating hotels
            Hotel hotel1 = Hotel.builder().name("Luxury Stay").location("New York").description("A luxury hotel in New York.").contactInfo("contact@luxurystay.com").build();
            Hotel hotel2 = Hotel.builder().name("Budget Inn").location("Los Angeles").description("Affordable rooms in Los Angeles.").contactInfo("contact@budgetinn.com").build();

            hotelRepository.save(hotel1);
            hotelRepository.save(hotel2);

            // Creating rooms
            Room room1 = Room.builder().hotel(hotel1).name("Deluxe Room").type(RoomType.SINGLE).pricePerNight(BigDecimal.valueOf(200.00)).status(RoomStatus.AVAILABLE).description("A luxurious single room.").build();
            Room room2 = Room.builder().hotel(hotel1).name("Family Suite").type(RoomType.FAMILY).pricePerNight(BigDecimal.valueOf(400.00)).status(RoomStatus.AVAILABLE).description("A spacious family suite.").build();
            Room room3 = Room.builder().hotel(hotel2).name("Standard Room").type(RoomType.DOUBLE).pricePerNight(BigDecimal.valueOf(100.00)).status(RoomStatus.AVAILABLE).description("A standard double room.").build();

            roomRepository.save(room1);
            roomRepository.save(room2);
            roomRepository.save(room3);

            // Creating a booking
            Booking booking = Booking.builder()
                    .customer(customer)
                    .name(customer.getName())
                    .email(customer.getEmail())
                    .phone("1234567890")
                    .address("123 Main St, New York, NY")
                    .checkinDate(LocalDate.now().plusDays(1))
                    .checkoutDate(LocalDate.now().plusDays(5))
                    .status(BookingStatus.CONFIRMED)
                    .totalAmount(BigDecimal.valueOf(1000.00))
                    .build();

            bookingRepository.save(booking);

            // Associating rooms with the booking
            BookingRoom bookingRoom1 = BookingRoom.builder()
                    .booking(booking)
                    .room(room1)
                    .currentPricePerNight(room1.getPricePerNight())
                    .build();

            bookingRoomRepository.save(bookingRoom1);

            // Creating a payment
            Payment payment = Payment.builder()
                    .customer(customer)
                    .booking(booking)
                    .method("Credit Card")
                    .amount(booking.getTotalAmount())
                    .status(PaymentStatus.COMPLETED)
                    .transactionId("TX123456789")
                    .build();

            paymentRepository.save(payment);


            log.warn("App ready");
        } catch (DataAccessException e) {
            log.error("Exception occurred during data persistence: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected exception occurred: " + e.getMessage());
        }
    }
}
