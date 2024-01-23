package umc.forgrad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.converter.ActivityConverter;
import umc.forgrad.domain.Activity;
import umc.forgrad.domain.enums.Category;
import umc.forgrad.dto.activity.PostActivityRequest;
import umc.forgrad.dto.activity.PostActivityResponse;
import umc.forgrad.service.activityService.ActivityCommandService;
import umc.forgrad.service.activityService.ActivityQueryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ActivityController {


    private final ActivityCommandService activityCommandService;
    private final ActivityQueryService activityQueryService;

    @PostMapping("/activity")
    public ApiResponse<PostActivityResponse.RegistActivityResultDto> createActivity(@RequestPart(value = "requestDto", required = false) PostActivityRequest.RegistActivity registActivity,
                                                                                    @RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles
                                                                                    )
    {
        Activity activity = activityCommandService.createBoard(registActivity, multipartFiles);
        return ApiResponse.onSuccess(ActivityConverter.activityResultDto(activity));

    }

    @GetMapping("/activity-list/{category}")
    public ApiResponse<PostActivityResponse.ActivityListDto> getActivityList(@PathVariable Category category,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Activity> careerList = activityQueryService.getCareerList(category, pageable);
        return ApiResponse.onSuccess(ActivityConverter.activityListDto(careerList));
    }

}
