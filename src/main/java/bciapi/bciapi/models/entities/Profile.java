package bciapi.bciapi.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Setter
@Getter
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    @Column(name = "model_data", columnDefinition = "BLOB")
    @Schema(description = "The model data file", type = "string", format = "binary")
    private byte[] modelData;
    @Lob
    @Column(name = "ica_model_data", columnDefinition = "BLOB")
    @Schema(description = "The model data file", type = "string", format = "binary")
    private byte[] icaModelData;
}
