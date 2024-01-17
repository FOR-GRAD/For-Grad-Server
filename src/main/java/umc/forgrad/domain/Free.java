package umc.forgrad.domain;

import jakarta.persistence.Entity;
<<<<<<< HEAD

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
=======
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

>>>>>>> e80b4ddef4dfcaf85828cbca38d998be5028396c

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
