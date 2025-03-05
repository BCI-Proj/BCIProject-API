package bciapi.bciapi.services.interfaces;

import bciapi.bciapi.models.entities.Profile;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collection;

public interface IProfileService {

    /**
     * Gets all profiles.
     * @return All the profiles saved.
     * @throws Exception General Exception.
     */
    Collection<Profile> getAllProfiles() throws Exception;

    /**
     * Updates a profile.
     * @param profileName The profile's name.
     * @param modelData The profile's model data.
     * @param icaModelData The profile's ICA model data.
     * @return The updated profile.
     * @throws HttpClientErrorException.NotFound Thrown when profile not found based on ID.
     */
    public Profile updateProfile(String profileName, byte[] modelData, byte[] icaModelData) throws Exception;

    /**
     * Get profile by name.
     * @return The profile.
     * @throws Exception General Exception.
     */
    Profile getProfileByName(String profileName) throws Exception;

    /**
     * Creates a profile.
     * @param profile The profile to create.
     * @return the profile with an ID assigned.
     * @throws HttpClientErrorException.Conflict Throws this when profile already exists.
     */
    Profile createProfile(Profile profile) throws Exception;
}
