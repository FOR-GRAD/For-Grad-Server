package umc.forgrad.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostActivityRequest {

    @NotBlank(message = "활동명을 입력하세요.")
    @Size(min = 2, max = 20, message = "제목의 길이는 2~20글자까지 입력 가능합니다.")
    private String title;
    private String content;
}
