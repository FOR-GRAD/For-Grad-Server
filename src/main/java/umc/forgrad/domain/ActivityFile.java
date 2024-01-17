package umc.forgrad.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    //==객체 생성 메서드==//
    public void createActivity(Activity activity) {
        this.activity = activity;

    }

}
