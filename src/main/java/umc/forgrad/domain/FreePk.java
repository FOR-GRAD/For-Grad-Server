package umc.forgrad.domain;

import jakarta.persistence.Id;
import lombok.Data;
import java.io.Serializable;
@Data
public class FreePk implements Serializable{


    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Id
    private Long stuid;
}
