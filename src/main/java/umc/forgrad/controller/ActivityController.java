package umc.forgrad.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.converter.ActivityConverter;
import umc.forgrad.domain.Activity;
import umc.forgrad.domain.enums.Category;
import umc.forgrad.dto.GetFileIdAndUrl;
import umc.forgrad.dto.activity.DeleteActivityRequest;
import umc.forgrad.dto.activity.PostActivityRequest;
import umc.forgrad.dto.activity.PostActivityResponse;
import umc.forgrad.service.activityService.ActivityCommandService;
import umc.forgrad.service.activityService.ActivityFileService;
import umc.forgrad.service.activityService.ActivityQueryService;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityCommandService activityCommandService;
    private final ActivityQueryService activityQueryService;
    private final ActivityFileService activityFileService;

    @PostMapping("/activity")
    public ApiResponse<PostActivityResponse.RegistActivityResultDto> createActivity(@RequestPart(value = "requestDto", required = false) PostActivityRequest.RegistActivity registActivity,
                                                                                    @RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles,
                                                                                    @SessionAttribute(name = "student") long studentId) throws IOException {

        Activity activity = activityCommandService.createBoard(registActivity, multipartFiles, studentId);
        return ApiResponse.onSuccess(ActivityConverter.activityResultDto(activity));

    }
    @GetMapping("/career-detail")
    public ApiResponse<PostActivityResponse.ActivityDetailDto> getActivityDetail(@RequestParam Long activityId)  {
        Activity activity = activityQueryService.findActivity(activityId);
        List<GetFileIdAndUrl> fileUrls = activityFileService.getFileUrls(activityId);

        return ApiResponse.onSuccess(ActivityConverter.activityDetailDto(activity, fileUrls));
    }

    @GetMapping("/career-list/{category}")
    public ApiResponse<PostActivityResponse.onlyAccumulatedList> getActivityAcc(@PathVariable Category category, @SessionAttribute(name = "student") long studentId) throws IOException {

        List<PostActivityResponse.ActivityWithAccumulatedHours> careerAccumulatedList = activityQueryService.getActivities(category, studentId);

        return ApiResponse.onSuccess(ActivityConverter.activityResult(careerAccumulatedList));
    }


    @GetMapping("/career-list-search/{category}")
    public ApiResponse<PostActivityResponse.onlyAccumulatedList> getActivitySearchResult(@PathVariable Category category,
                                                                                         @RequestParam String searchWord,
                                                                                         @SessionAttribute(name = "student") long studentId

                                                                                   ) throws IOException{
        List<PostActivityResponse.ActivityWithAccumulatedHours> searchedList = activityQueryService.getActivitiesByTitleAndCategory(searchWord, category, studentId);
        return ApiResponse.onSuccess(ActivityConverter.activityResult(searchedList));

    }

    @DeleteMapping("/career-delete")
    public ApiResponse<String> deleteActivity(@RequestParam Long activityId, @SessionAttribute(name = "student") long studentId) throws IOException
    {
        DeleteActivityRequest build = DeleteActivityRequest.builder()
                .activityId(activityId)
                .studentId(studentId)
                .build();
        return ApiResponse.onSuccess(activityCommandService.deleteActivity(build));
    }

    @PatchMapping("/career-update")
    public ApiResponse<String> updateActivity(@RequestPart Long activityId,
                                              @RequestPart(value = "updateDto", required = false) PostActivityRequest.UpdateDto updateDto,
                                              @RequestPart(value = "addFiles", required = false) List<MultipartFile> multipartFiles,
                                              @SessionAttribute(name = "student") long studentId) throws IOException
    {

        Long updatedActivityId = activityCommandService.updateActivity(activityId, studentId, updateDto, multipartFiles);
        return ApiResponse.onSuccess(String.format("id %s 커리어 업데이트 완료", updatedActivityId));

    }


}
