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
                                                                                    @RequestPart(value = "requestDto", required = false)PostActivityRequest.RegistActivity registActivity) {


        Activity activity = activityCommandService.createBoard(registActivity, multipartFiles);

        return ApiResponse.onSuccess(ActivityConverter.activityResultDto(activity));

    }
}
