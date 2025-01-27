package bciapi.bciapi.models.entities;

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
    private byte[] modelData;
}
