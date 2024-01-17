package umc.forgrad.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Free {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memo;

    public void setId(Long id) {
        this.id = id;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
