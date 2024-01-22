package umc.forgrad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.converter.ActivityConverter;
import umc.forgrad.domain.Activity;
import umc.forgrad.dto.activity.PostActivityRequest;
import umc.forgrad.dto.activity.PostActivityResponse;
import umc.forgrad.service.activityService.ActivityCommandService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ActivityController {


    private final ActivityCommandService activityCommandService;

    @PostMapping("/activity")
    public ApiResponse<PostActivityResponse.RegistActivityResultDto> createActivity(@RequestPart(value = "image", required = false)List<MultipartFile> multipartFiles,
                                                                                    @RequestPart(value = "file", required = false)PostActivityRequest.RegistActivity registActivity) {

//            PostActivityRequest postActivityRequest = new PostActivityRequest(title, content); dto를 여기서 만들었던건데, 스터디에서의 방식은 dto를 컨트롤러 인자로 받아서 서비스에는 dto를 그대로 넣어버림.
//            그럼 서비스에는 컨버터 이용하는ㄴ 부분이 있어서 dto를 엔티티로 잘 변환해서 씀
        Activity activity = activityCommandService.createBoard(registActivity, multipartFiles);
//            return ApiResponse.onSuccess(activityCommandService.createBoard(postActivityRequest, multipartFiles));
        return ApiResponse.onSuccess(ActivityConverter.activityResultDto(activity));

    }
}
