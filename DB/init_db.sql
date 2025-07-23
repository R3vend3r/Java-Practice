-- Drop constraints if they exist
ALTER TABLE IF EXISTS room_bookings DROP CONSTRAINT IF EXISTS room_bookings_client_id_fkey;
ALTER TABLE IF EXISTS room_bookings DROP CONSTRAINT IF EXISTS room_bookings_room_number_fkey;
ALTER TABLE IF EXISTS amenity_orders DROP CONSTRAINT IF EXISTS amenity_orders_client_id_fkey;
ALTER TABLE IF EXISTS amenity_orders DROP CONSTRAINT IF EXISTS amenity_orders_amenity_id_fkey;
ALTER TABLE IF EXISTS clients DROP CONSTRAINT IF EXISTS fk_client_room;
ALTER TABLE IF EXISTS rooms DROP CONSTRAINT IF EXISTS FK_ROOMS_CLIENT;

-- Recreate tables in proper order
CREATE TABLE IF NOT EXISTS clients (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS rooms (
    number INT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    capacity INT NOT NULL,
    condition VARCHAR(50) NOT NULL,
    stars INT NOT NULL,
    is_available BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS amenities (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS room_bookings (
    id VARCHAR(50) PRIMARY KEY,
    client_id VARCHAR(50) NOT NULL,
    room_number INT NOT NULL,
    check_in_date TIMESTAMP NOT NULL,
    check_out_date TIMESTAMP NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT room_bookings_client_id_fkey FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
    CONSTRAINT room_bookings_room_number_fkey FOREIGN KEY (room_number) REFERENCES rooms(number) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS amenity_orders (
    id VARCHAR(50) PRIMARY KEY,
    client_id VARCHAR(50) NOT NULL,
    amenity_id VARCHAR(50) NOT NULL,
    service_date TIMESTAMP NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT amenity_orders_client_id_fkey FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
    CONSTRAINT amenity_orders_amenity_id_fkey FOREIGN KEY (amenity_id) REFERENCES amenities(id) ON DELETE CASCADE
);