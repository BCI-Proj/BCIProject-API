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

import javax.swing.text.html.Option;
import java.awt.color.ICC_Profile;
import java.util.Collection;
import java.util.List;
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
    public List<Profile> getAllProfiles() throws Exception {
        try {
            return this.profileRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Profile updateProfile(String profileName, byte[] modelData, byte[] icaModelData) throws Exception {
        try {
            Optional<Profile> existingProfile = this.profileRepository.findProfileByName(profileName.toLowerCase());

            if (existingProfile.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile Not Found");
            }

            Profile presentProfile = existingProfile.get();

            if (modelData.length != 0) {
                presentProfile.setModelData(modelData);
            }

            if (icaModelData.length != 0) {
                presentProfile.setIcaModelData(icaModelData);
            }

            return this.profileRepository.save(presentProfile);
        } catch (ResponseStatusException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Profile getProfileByName(String profileName) throws Exception {
        try {
            Optional<Profile> optionalProfile = this.profileRepository.findProfileByName(profileName.toLowerCase());
            Profile profile;

            if (optionalProfile.isPresent()) {
                profile = optionalProfile.get();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile Not Found");
            }

            return profile;

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

            profile.setName(profile.getName().toLowerCase());
            profile.setId(null);
            return this.profileRepository.save(profile);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
