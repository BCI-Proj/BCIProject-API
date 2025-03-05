package bciapi.bciapi.web;

import bciapi.bciapi.models.entities.Profile;
import bciapi.bciapi.services.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Profile Controller
 * Profile web REST-API controller.
 *
 * @author Raph
 * @since 1/27/2025
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileWebClient {
    private final ProfileService profileService;

    @GetMapping("/profiles")
    @Operation(description = "Gets all the profiles.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Profile.class)))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request.")
    })
    public ResponseEntity<List<Profile>> getAllProfiles() {
        try {
            return ResponseEntity.ok(profileService.getAllProfiles());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(List.of());
        }
    }

    @GetMapping("/profile/model/{profileName}")
    @Operation(summary = "Downloads a profile's ICA model file.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/octet-stream")),
            @ApiResponse(responseCode = "400", description = "Bad Request."),
            @ApiResponse(responseCode = "404", description = "Profile not found.")
    })
    public ResponseEntity<byte[]> downloadModelByProfileName(@PathVariable String profileName) {
        try {
            Profile profile = this.profileService.getProfileByName(profileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(profile.getModelData());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new byte[]{});
        }
    }

    @GetMapping("/profile/ica_model/{profileName}")
    @Operation(summary = "Downloads a profile's ICA model file.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/octet-stream")),
            @ApiResponse(responseCode = "400", description = "Bad Request."),
            @ApiResponse(responseCode = "404", description = "Profile not found.")
    })
    public ResponseEntity<byte[]> downloadIcaModelByProfileName(@PathVariable String profileName) {
        try {
            Profile profile = this.profileService.getProfileByName(profileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(profile.getIcaModelData());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new byte[]{});
        }
    }

    @GetMapping("/profile")
    @Operation(summary = "Gets a profile by name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profile.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request."),
            @ApiResponse(responseCode = "404", description = "Profile not found.")
    })
    public ResponseEntity<Profile> getProfileByName(@RequestParam String profileName) {
        try {
            Profile profile = profileService.getProfileByName(profileName);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update-profile")
    @Operation(summary = "Updates a profile.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "User info updated.",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Profile.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Profile not found.")
    })
    public ResponseEntity<?> updateProfile(@RequestParam String profileName,
                                           @RequestParam("modelData") MultipartFile modelData,
                                           @RequestParam("ica_model") MultipartFile icaModel) {
        try {
            return ResponseEntity.ok(this.profileService.updateProfile(profileName, modelData.getBytes(), icaModel.getBytes()));
        } catch (ResponseStatusException customException) {
            if (customException.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/create")
    @Operation(summary = "Creates a profile.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "User info created.",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Profile.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "409", description = "User already exists.")
    })
    public ResponseEntity<?> createProfile(
            @RequestParam("name") String name,
            @RequestParam("model_data") MultipartFile modelData,
            @RequestParam("ica_model") MultipartFile icaModel) {

        Profile profile = new Profile();
        profile.setId((long) -1);
        profile.setName(name.toLowerCase());

        try {
            profile.setModelData(modelData.getBytes());
            profile.setIcaModelData(icaModel.getBytes());
            return ResponseEntity.ok(this.profileService.createProfile(profile));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
