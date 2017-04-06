INSERT INTO location (id, address, latitude, longitude, postcode, full_address, description) 
VALUES (1, '509 Forest Drive', null, null, 'L4L 6M6', '509 Forest Drive, Vaughan, Ontario, Canada', 'Home');

    
INSERT INTO listing (id, listing_type, status, input_date, modified_date, style, listing_price, location_id) 
VALUES (1, 'DETACHED', 'AVAILABLE', '2017-04-05', '2017-04-06', 'TWOSTOREY', 1234567.89, 1);
