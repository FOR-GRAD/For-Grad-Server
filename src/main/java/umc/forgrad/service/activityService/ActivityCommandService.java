package umc.forgrad.service.activityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.forgrad.converter.ActivityConverter;
import umc.forgrad.domain.Activity;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.GetS3Res;
import umc.forgrad.dto.activity.PostActivityRequest;
import umc.forgrad.repository.ActivityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityCommandService {
    private final S3Service s3Service;
    private final ActivityRepository activityRepository;
    private final ActivityFileService activityFileService;

    @Transactional
    public void save(Activity activity) {
        activityRepository.save(activity);
    }

    @Transactional
    public Activity createBoard(PostActivityRequest.RegistActivity postActivityReq, List<MultipartFile> multipartFiles) {

        Activity activity = ActivityConverter.toActivity(postActivityReq, multipartFiles);
        System.out.println(activity);

        save(activity);
        if (multipartFiles != null) {
            List<GetS3Res> imgUrls = s3Service.uploadFile(multipartFiles);
            activityFileService.saveAllActivityFileByActivity(imgUrls, activity);
        }


        return activity;
    }
}
