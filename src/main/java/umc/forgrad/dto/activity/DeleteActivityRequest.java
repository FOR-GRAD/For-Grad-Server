package umc.forgrad.dto.activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class DeleteActivityRequest {
    private Long activityId;
    private Long studentId;

}
