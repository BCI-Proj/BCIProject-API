package bciapi.bciapi.services;

import bciapi.bciapi.models.entities.Profile;
import bciapi.bciapi.models.repositories.ProfileRepository;
import bciapi.bciapi.services.interfaces.IProfileService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Collection;
import java.util.Optional;

/**
 * @author Raph
 * bci-api - bciapi.bciapi.services
 * ProfileServie
 * <p>
 * <p>
 * 1/27/2025
 */
@Service
@AllArgsConstructor
@Slf4j
@Builder
public class ProfileService implements IProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public Collection<Profile> getAllProfiles() throws Exception {
        try {
            return this.profileRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Profile updateProfile(Profile profile) throws Exception {
        try {
            Optional<Profile> existingProfile = this.profileRepository.findById(profile.getId());

            if (existingProfile.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile Not Found");
            }

            return this.profileRepository.save(profile);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Profile createProfile(Profile profile) throws Exception {
        try {
            Optional<Profile> existingProfile = this.profileRepository.findById(profile.getId());

            if (existingProfile.isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Profile Already Exists");
            }

            return this.profileRepository.save(profile);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
