package com.poolu.poolu.config;

import com.poolu.poolu.model.model.Driver;
import com.poolu.poolu.model.model.Location;
import com.poolu.poolu.model.model.Pool;
import com.poolu.poolu.model.model.Pooler;
import com.poolu.poolu.model.model.User;
import com.poolu.poolu.model.model.Vehicle;
import com.poolu.poolu.repository.UserRepository;
import com.poolu.poolu.service.AuthService;
import com.poolu.poolu.service.PoolService;
import com.poolu.poolu.service.ReviewService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    @ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true")
    CommandLineRunner seedData(
            AuthService authService,
            PoolService poolService,
            ReviewService reviewService,
            UserRepository userRepository
    ) {
        return args -> {
            boolean freshDatabase = userRepository.count() == 0;

            Driver driver = ensureDriver(
                    userRepository,
                    authService,
                    "Rahul",
                    "rahul@example.com",
                    "9876543210",
                    "DL12345",
                    "MG Road, Bengaluru",
                    12.975,
                    77.605,
                    "Honda",
                    "City",
                    "KA01AB1234",
                    4
            );

            Pooler poolerOne = ensurePooler(
                    userRepository,
                    authService,
                    "Ananya",
                    "ananya@example.com",
                    "9123456780",
                    "UPI",
                    "Indiranagar, Bengaluru",
                    12.978,
                    77.640,
                    "Electronic City, Bengaluru",
                    12.845,
                    77.660
            );

            ensurePooler(
                    userRepository,
                    authService,
                    "Arjun",
                    "arjun@example.com",
                    "9988776655",
                    "Card",
                    "Koramangala, Bengaluru",
                    12.935,
                    77.624,
                    "Whitefield, Bengaluru",
                    12.969,
                    77.750
            );

            ensureDriver(
                    userRepository,
                    authService,
                    "Sneha",
                    "sneha.driver@example.com",
                    "9012345678",
                    "DL23456",
                    "Jayanagar, Bengaluru",
                    12.929,
                    77.593,
                    "Hyundai",
                    "Verna",
                    "KA03CD4567",
                    4
            );

            ensureDriver(
                    userRepository,
                    authService,
                    "Kiran",
                    "kiran.driver@example.com",
                    "9345678901",
                    "DL34567",
                    "Hebbal, Bengaluru",
                    13.035,
                    77.597,
                    "Maruti Suzuki",
                    "Ertiga",
                    "KA05EF8910",
                    6
            );

            ensurePooler(
                    userRepository,
                    authService,
                    "Bimbu",
                    "bimbu@gmail.com",
                    "9090328909",
                    "Card",
                    "JP Nagar, Bengaluru",
                    12.907,
                    77.585,
                    "Marathahalli, Bengaluru",
                    12.959,
                    77.701
            );

            ensurePooler(
                    userRepository,
                    authService,
                    "Meera",
                    "meera.pooler@example.com",
                    "9876501234",
                    "UPI",
                    "Rajajinagar, Bengaluru",
                    12.991,
                    77.554,
                    "Bellandur, Bengaluru",
                    12.926,
                    77.676
            );

            ensurePooler(
                    userRepository,
                    authService,
                    "Varun",
                    "varun.pooler@example.com",
                    "9988012345",
                    "Cash",
                    "Banashankari, Bengaluru",
                    12.925,
                    77.546,
                    "Manyata Tech Park, Bengaluru",
                    13.049,
                    77.620
            );

            ensurePooler(
                    userRepository,
                    authService,
                    "Nisha",
                    "nisha.pooler@example.com",
                    "9765401234",
                    "Card",
                    "Malleshwaram, Bengaluru",
                    13.003,
                    77.571,
                    "HSR Layout, Bengaluru",
                    12.912,
                    77.644
            );

            if (freshDatabase) {
                Pool pool = new Pool();
                Driver driverReference = new Driver();
                driverReference.setUserId(driver.getUserId());
                pool.setDriver(driverReference);
                pool.setOrigin(buildLocation("BTM Layout, Bengaluru", 12.916, 77.610));
                pool.setDestination(buildLocation("Whitefield, Bengaluru", 12.969, 77.750));
                poolService.createPool(pool);

                reviewService.submitReview(
                        poolerOne.getUserId(),
                        driver.getUserId(),
                        5,
                        "Safe and comfortable ride"
                );
            }
        };
    }

    private static Driver ensureDriver(
            UserRepository userRepository,
            AuthService authService,
            String name,
            String email,
            String phone,
            String licenseNumber,
            String address,
            double latitude,
            double longitude,
            String make,
            String model,
            String plateNumber,
            int seats
    ) {
        User existingUser = findUserByEmail(userRepository, email);
        if (existingUser instanceof Driver existingDriver) {
            return existingDriver;
        }

        Driver driver = new Driver();
        driver.setName(name);
        driver.setEmail(email);
        driver.setPhone(phone);
        driver.setLicenseNumber(licenseNumber);
        driver.setIsAvailable(true);
        driver.setCurrentLocation(buildLocation(address, latitude, longitude));
        driver.setVehicleInfo(buildVehicle(make, model, plateNumber, seats));
        return authService.registerDriver(driver);
    }

    private static Pooler ensurePooler(
            UserRepository userRepository,
            AuthService authService,
            String name,
            String email,
            String phone,
            String paymentMethod,
            String currentAddress,
            double currentLatitude,
            double currentLongitude,
            String destinationAddress,
            double destinationLatitude,
            double destinationLongitude
    ) {
        User existingUser = findUserByEmail(userRepository, email);
        if (existingUser instanceof Pooler existingPooler) {
            return existingPooler;
        }

        Pooler pooler = new Pooler();
        pooler.setName(name);
        pooler.setEmail(email);
        pooler.setPhone(phone);
        pooler.setPaymentMethod(paymentMethod);
        pooler.setCurrentLocation(buildLocation(currentAddress, currentLatitude, currentLongitude));
        pooler.setDestination(buildLocation(destinationAddress, destinationLatitude, destinationLongitude));
        return authService.registerPooler(pooler);
    }

    private static User findUserByEmail(UserRepository userRepository, String email) {
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail() != null && user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    private static Location buildLocation(String address, double latitude, double longitude) {
        Location location = new Location();
        location.setAddress(address);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    private static Vehicle buildVehicle(String make, String model, String plateNumber, int seats) {
        Vehicle vehicle = new Vehicle();
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setPlateNumber(plateNumber);
        vehicle.setSeats(seats);
        return vehicle;
    }
}
