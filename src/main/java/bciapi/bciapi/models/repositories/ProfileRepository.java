package bciapi.bciapi.models.repositories;

import bciapi.bciapi.models.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Raphael Paquin
 * @version 01
 * Provides data-access to Invoices.
 * 2024-04-10
 * AutoPass
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findProfileByName(String name);
}
