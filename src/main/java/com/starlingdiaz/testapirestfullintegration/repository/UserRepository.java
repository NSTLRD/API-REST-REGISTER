/**
 * @author Starling Diaz on 4/11/2024.
 * @Academy StarAcademy
 * @version test-api-restfull-integration 1.0
 * @since 4/11/2024.
 */
package com.starlingdiaz.testapirestfullintegration.repository;

import com.starlingdiaz.testapirestfullintegration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}