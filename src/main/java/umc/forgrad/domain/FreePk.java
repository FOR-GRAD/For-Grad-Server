package umc.forgrad.domain;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class FreePk {


    @Id
    private Long id;

    @Id
    private Long stuId;
}
