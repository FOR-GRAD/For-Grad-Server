package umc.forgrad.controller;

import jakarta.servlet.http.HttpSession;
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
import umc.forgrad.service.activityService.ActivityFileService;
import umc.forgrad.service.activityService.ActivityQueryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityCommandService activityCommandService;
    private final ActivityQueryService activityQueryService;
    private final ActivityFileService activityFileService;

    @PostMapping("/activity")
    public ApiResponse<PostActivityResponse.RegistActivityResultDto> createActivity(@RequestPart(value = "requestDto", required = false) PostActivityRequest.RegistActivity registActivity,
                                                                                    @RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles,
                                                                                    HttpSession httpSession
    ) {
        String studentId = (String) httpSession.getAttribute("studentId");
        Activity activity = activityCommandService.createBoard(registActivity, multipartFiles, studentId);
        return ApiResponse.onSuccess(ActivityConverter.activityResultDto(activity));

    }

    @GetMapping("/activity-list/{category}")
    public ApiResponse<PostActivityResponse.ActivityListDto> getActivityList(@PathVariable Category category,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Activity> careerList = activityQueryService.getCareerList(category, pageable);
        PostActivityResponse.ActivityListDto activityListDto = ActivityConverter.activityListDto(careerList);
        return ApiResponse.onSuccess(activityListDto);
    }

    @GetMapping("/activity-detail")
    public ApiResponse<PostActivityResponse.ActivityDetailDto> getActivityDetail(@RequestParam Long activityId){
        List<String> fileUrls = activityFileService.getFileUrls(activityId);
        Activity activity = activityQueryService.findActivity(activityId);
        return ApiResponse.onSuccess(ActivityConverter.activityDetailDto(activity, fileUrls));
    }
}