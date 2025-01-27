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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Raph
 * bci-api - bciapi.bciapi.web
 * ProfileController
 *
 * 1/27/2025
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
            @ApiResponse(
                    responseCode = "400", description = "Bad Request.",
                    content = @Content
            )
    })
    public ResponseEntity<Collection<Profile>> getAllProfiles() {
        try {
            return ResponseEntity.ok(profileService.getAllProfiles());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
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
            @ApiResponse(
                    responseCode = "400", description = "Bad request.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404", description = "Profile not found.",
                    content = @Content
            )
    })
    public ResponseEntity<?> UpdateProfile(Profile profile) {
        try {
            return ResponseEntity.ok(this.profileService.updateProfile(profile));
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
            @ApiResponse(
                    responseCode = "400", description = "Bad request.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409", description = "User already exists.",
                    content = @Content
            )
    })
    public ResponseEntity<?> CreateProfile(Profile profile) {
        try {
            return ResponseEntity.ok(this.profileService.createProfile(profile));
        } catch (ResponseStatusException customeException) {
            if (customeException.getStatusCode() == HttpStatus.CONFLICT) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
