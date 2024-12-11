USE carDealership;

-- ----------------------------------------------------------------------
-- Insert dummy data into Dealerships table
-- ----------------------------------------------------------------------

INSERT INTO `Dealerships` (`Name`, `Address`, `Phone`) VALUES
('Best Motors', '123 Main St, Springfield', '555-1234'),
('Auto World', '456 Elm St, Shelbyville', '555-5678'),
('Super Cars', '789 Oak St, Capital City', '555-8765');

-- ----------------------------------------------------------------------
-- Insert dummy data into Vehicles table
-- ----------------------------------------------------------------------

INSERT INTO `Vehicles` (`VIN`, `Year`, `Make`, `Model`, `VehicleType`, `Color`, `Odometer`, `Price`, `Sold`) VALUES
(1001, 2020, 'Toyota', 'Camry', 'Sedan', 'Red', 15000, 22000.00, FALSE),
(1002, 2021, 'Honda', 'Civic', 'Sedan', 'Blue', 10000, 20000.00, FALSE),
(1003, 2022, 'Ford', 'F-150', 'Truck', 'Black', 5000, 35000.00, TRUE),
(1004, 2019, 'Chevrolet', 'Tahoe', 'SUV', 'White', 30000, 45000.00, FALSE),
(1005, 2021, 'Tesla', 'Model 3', 'Sedan', 'Silver', 12000, 55000.00, TRUE);

-- ----------------------------------------------------------------------
-- Insert dummy data into Inventory table
-- ----------------------------------------------------------------------

INSERT INTO `Inventory` (`DealershipID`, `VIN`) VALUES
(1, 1001),
(1, 1002),
(2, 1003),
(2, 1004),
(3, 1005);

-- ----------------------------------------------------------------------
-- Insert dummy data into Customers table
-- ----------------------------------------------------------------------

INSERT INTO `Customers` (`Name`, `Email`) VALUES
('John Doe', 'john.doe@email.com'),
('Jane Smith', 'jane.smith@email.com'),
('Alice Johnson', 'alice.johnson@email.com');

-- ----------------------------------------------------------------------
-- Insert dummy data into Contracts table
-- ----------------------------------------------------------------------

-- John Doe purchases a Toyota Camry
INSERT INTO `Contracts` (`Date`, `isSalesContract`, `Financed`, `VIN`, `CustomerID`) VALUES
(20241211, TRUE, TRUE, 1001, 1);

-- Jane Smith purchases a Honda Civic
INSERT INTO `Contracts` (`Date`, `isSalesContract`, `Financed`, `VIN`, `CustomerID`) VALUES
(20241211, TRUE, FALSE, 1002, 2);

-- Alice Johnson purchases a Tesla Model 3
INSERT INTO `Contracts` (`Date`, `isSalesContract`, `Financed`, `VIN`, `CustomerID`) VALUES
(20241211, TRUE, TRUE, 1005, 3);