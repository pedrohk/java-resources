DROP TABLE IF EXISTS service_item;
DROP TABLE IF EXISTS booking;

CREATE TABLE booking (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         guest_name VARCHAR(255),
                         room_number VARCHAR(10)
);

CREATE TABLE service_item (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              booking BIGINT,
                              description VARCHAR(255),
                              price DOUBLE
);
