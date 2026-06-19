-- Hotel Management System Database Schema Setup Script
-- Database name: hotel_management (matches MySqlConnection.java default)
CREATE DATABASE IF NOT EXISTS hotel_management;
USE hotel_management;

-- 1. Create table: rooms
CREATE TABLE IF NOT EXISTS rooms (
    room_number VARCHAR(10) PRIMARY KEY,
    room_type VARCHAR(50) NOT NULL,
    status VARCHAR(20) DEFAULT 'Available', -- 'Available', 'Occupied', 'Maintenance'
    price_per_night DECIMAL(10, 2) NOT NULL
);

-- 2. Create table: guests
CREATE TABLE IF NOT EXISTS guests (
    guest_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20) NOT NULL,
    document_id VARCHAR(50)
);

-- 3. Create table: bookings
CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_id INT NOT NULL,
    room_number VARCHAR(10) NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'Confirmed', -- 'Confirmed', 'CheckedIn', 'CheckedOut', 'Cancelled'
    FOREIGN KEY (guest_id) REFERENCES guests(guest_id) ON DELETE CASCADE,
    FOREIGN KEY (room_number) REFERENCES rooms(room_number) ON DELETE CASCADE
);

-- 4. Create table: billings
CREATE TABLE IF NOT EXISTS billings (
    billing_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_status VARCHAR(20) DEFAULT 'Paid', -- 'Paid', 'Pending', 'Refunded'
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE
);

-- 5. Create table: meal_orders
CREATE TABLE IF NOT EXISTS meal_orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    meal_type VARCHAR(50) NOT NULL, -- 'Breakfast', 'Lunch', 'Dinner'
    quantity INT DEFAULT 1,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE
);

-- Clear existing data if any (optional, for clean run)
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE meal_orders;
TRUNCATE TABLE billings;
TRUNCATE TABLE bookings;
TRUNCATE TABLE guests;
TRUNCATE TABLE rooms;
SET FOREIGN_KEY_CHECKS = 1;

-- -------------------------------------------------------------
-- INSERT SAMPLE/INITIAL DATA FOR DEMO & TESTING
-- -------------------------------------------------------------

-- Insert rooms (60 total rooms)
INSERT INTO rooms (room_number, room_type, status, price_per_night) VALUES 
('101', 'Single', 'Occupied', 80.00),
('102', 'Single', 'Available', 80.00),
('103', 'Single', 'Available', 80.00),
('104', 'Single', 'Occupied', 80.00),
('105', 'Single', 'Maintenance', 80.00),
('201', 'Double', 'Available', 120.00),
('202', 'Double', 'Occupied', 120.00),
('203', 'Double', 'Available', 120.00),
('204', 'Double', 'Available', 120.00),
('301', 'Suite', 'Occupied', 250.00),
('302', 'Suite', 'Available', 250.00),
('303', 'Suite', 'Occupied', 250.00);

-- Insert remainder rooms quickly to simulate total rooms = 60
-- (Simulated bulk inserts)
INSERT INTO rooms (room_number, room_type, status, price_per_night) VALUES
('106', 'Single', 'Available', 80.00), ('107', 'Single', 'Available', 80.00), ('108', 'Single', 'Available', 80.00),
('109', 'Single', 'Available', 80.00), ('110', 'Single', 'Available', 80.00), ('111', 'Single', 'Available', 80.00),
('112', 'Single', 'Available', 80.00), ('113', 'Single', 'Available', 80.00), ('114', 'Single', 'Available', 80.00),
('115', 'Single', 'Available', 80.00), ('116', 'Single', 'Available', 80.00), ('117', 'Single', 'Available', 80.00),
('205', 'Double', 'Available', 120.00), ('206', 'Double', 'Available', 120.00), ('207', 'Double', 'Available', 120.00),
('208', 'Double', 'Available', 120.00), ('209', 'Double', 'Available', 120.00), ('210', 'Double', 'Available', 120.00),
('211', 'Double', 'Available', 120.00), ('212', 'Double', 'Available', 120.00), ('213', 'Double', 'Available', 120.00),
('214', 'Double', 'Available', 120.00), ('215', 'Double', 'Available', 120.00), ('216', 'Double', 'Available', 120.00),
('304', 'Suite', 'Available', 250.00), ('305', 'Suite', 'Available', 250.00), ('306', 'Suite', 'Available', 250.00),
('307', 'Suite', 'Available', 250.00), ('308', 'Suite', 'Available', 250.00), ('309', 'Suite', 'Available', 250.00),
('310', 'Suite', 'Available', 250.00), ('311', 'Suite', 'Available', 250.00), ('312', 'Suite', 'Available', 250.00),
('401', 'Deluxe', 'Available', 350.00), ('402', 'Deluxe', 'Available', 350.00), ('403', 'Deluxe', 'Available', 350.00),
('404', 'Deluxe', 'Available', 350.00), ('405', 'Deluxe', 'Available', 350.00), ('406', 'Deluxe', 'Available', 350.00),
('407', 'Deluxe', 'Available', 350.00), ('408', 'Deluxe', 'Available', 350.00), ('409', 'Deluxe', 'Available', 350.00),
('410', 'Deluxe', 'Available', 350.00), ('411', 'Deluxe', 'Available', 350.00), ('412', 'Deluxe', 'Available', 350.00),
('501', 'Penthouse', 'Available', 800.00), ('502', 'Penthouse', 'Available', 800.00), ('503', 'Penthouse', 'Available', 800.00);

-- Insert Guests
INSERT INTO guests (first_name, last_name, email, phone, document_id) VALUES
('John', 'Doe', 'john.doe@gmail.com', '+123456789', 'PASSPORT123'),
('Jane', 'Smith', 'jane.smith@yahoo.com', '+987654321', 'DL-45678'),
('Bob', 'Johnson', 'bob.j@outlook.com', '+1122334455', 'ID-7890'),
('Alice', 'Brown', 'alice.brown@gmail.com', '+9988776655', 'PASS-987');

-- Insert Bookings
INSERT INTO bookings (guest_id, room_number, check_in_date, check_out_date, status) VALUES
(1, '101', '2026-05-25', '2026-05-30', 'CheckedIn'),
(2, '202', '2026-05-28', '2026-06-02', 'CheckedIn'),
(3, '301', '2026-05-29', '2026-06-05', 'CheckedIn'),
(4, '303', '2026-05-30', '2026-06-03', 'CheckedIn');

-- Insert Billings/Payments
INSERT INTO billings (booking_id, amount, payment_status) VALUES
(1, 400.00, 'Paid'),
(2, 600.00, 'Paid'),
(3, 1750.00, 'Paid'),
(4, 1000.00, 'Paid');

-- Insert Meal Orders
INSERT INTO meal_orders (booking_id, meal_type, quantity) VALUES
(1, 'Breakfast', 2),
(1, 'Lunch', 1),
(2, 'Dinner', 2),
(3, 'Breakfast', 3),
(4, 'Dinner', 1);
