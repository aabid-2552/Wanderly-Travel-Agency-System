package com.travelagency.config;

import com.travelagency.model.Role;
import com.travelagency.model.TourPackage;
import com.travelagency.model.User;
import com.travelagency.repository.PackageRepository;
import com.travelagency.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PackageRepository packageRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PackageRepository packageRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.packageRepository = packageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Seed a default admin account: admin@travelagency.com / admin123
        if (!userRepository.existsByEmail("admin@travelagency.com")) {
            User admin = new User("Admin", "admin@travelagency.com",
                    passwordEncoder.encode("admin123"), Role.ROLE_ADMIN);
            userRepository.save(admin);
            System.out.println(">>> Seeded admin login: admin@travelagency.com / admin123");
        }

        // Seed sample tour packages if none exist
        if (packageRepository.count() == 0) {
            packageRepository.save(new TourPackage(
                    "French Polynesia Escape",
                    "Crystal clear lagoons, overwater bungalows and unforgettable sunsets.",
                    "French Polynesia", "Beach", 199.0, 6,
                    "https://images.unsplash.com/photo-1544644181-1484b3fdfc62?w=800"));

            packageRepository.save(new TourPackage(
                    "Bengal Wild Safari",
                    "Track royal Bengal tigers through dense forests on a guided jeep safari.",
                    "Sundarbans, India", "Wildlife", 249.0, 5,
                    "https://images.unsplash.com/photo-1615397349754-cfa2066a298e?w=800"));

            packageRepository.save(new TourPackage(
                    "Swiss Alps Adventure",
                    "Hike snow-capped peaks and relax in cozy alpine villages.",
                    "Switzerland", "Adventure", 599.0, 8,
                    "https://images.unsplash.com/photo-1531366936337-7c912a4589a7?w=800"));

            packageRepository.save(new TourPackage(
                    "Maldives Honeymoon Special",
                    "Private villas, turquoise waters and world-class diving spots.",
                    "Maldives", "Honeymoon", 899.0, 7,
                    "https://images.unsplash.com/photo-1573843981267-be1999ff37cd?w=800"));

            packageRepository.save(new TourPackage(
                    "African Safari Explorer",
                    "Witness the great migration and spot the big five in their natural habitat.",
                    "Kenya", "Wildlife", 799.0, 9,
                    "https://images.unsplash.com/photo-1516426122078-c23e76319801?w=800"));

            packageRepository.save(new TourPackage(
                    "Japan Culture Tour",
                    "Explore ancient temples, cherry blossoms and vibrant city life.",
                    "Japan", "Cultural", 749.0, 10,
                    "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?w=800"));

            System.out.println(">>> Seeded 6 sample tour packages");
        }
    }
}
