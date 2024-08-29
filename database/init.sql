create database final_hms;
CREATE TABLE USERS (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(15),
                       address VARCHAR(400),
                       role ENUM('customer', 'hotel_staff', 'admin') NOT NULL
);

CREATE TABLE HOTELS (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        location VARCHAR(255),
                        description TEXT,
                        contact_info VARCHAR(255)
);

CREATE TABLE ROOMS (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       hotel_id INT,
                       name VARCHAR(100) NOT NULL,
                       type ENUM('SINGLE', 'DOUBLE', 'FAMILY'),
                       price_per_night DECIMAL(10, 2),
                       status ENUM('available', 'booked', 'occupied', 'maintenance'),
                       description TEXT,
                       FOREIGN KEY (hotel_id) REFERENCES HOTELS(id)
);

ALTER TABLE USERS
    ADD COLUMN hotel_id INT,
ADD FOREIGN KEY (hotel_id) REFERENCES HOTELS(id);

CREATE TABLE REVIEWS (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         customer_id INT,
                         room_id INT,
                         content TEXT,
                         rating INT CHECK (rating >= 1 AND rating <= 5),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (customer_id) REFERENCES USERS(id),
                         FOREIGN KEY (room_id) REFERENCES ROOMS(id)
);

CREATE TABLE BOOKINGS (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          customer_id INT,
                          name VARCHAR(100),
                          email VARCHAR(100),
                          phone VARCHAR(15),
                          address VARCHAR(400),
                          checkin_date DATE,
                          checkout_date DATE,
                          status ENUM('pending', 'confirmed', 'cancelled'),
                          total_amount DECIMAL(10, 2),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (customer_id) REFERENCES USERS(id)
);


CREATE TABLE BOOKING_ROOMS (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               booking_id INT,
                               room_id INT,
                               current_price_per_night DECIMAL(10, 2),
                               FOREIGN KEY (booking_id) REFERENCES BOOKINGS(id),
                               FOREIGN KEY (room_id) REFERENCES ROOMS(id)
);

CREATE TABLE PAYMENTS (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          customer_id INT,
                          booking_id INT,
                          method VARCHAR(50),
                          amount DECIMAL(10, 2),
                          status ENUM('pending', 'completed', 'failed', 'refunded'),
                          timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          transaction_id VARCHAR(100) UNIQUE,
                          FOREIGN KEY (customer_id) REFERENCES USERS(id),
                          FOREIGN KEY (booking_id) REFERENCES BOOKINGS(id)
);