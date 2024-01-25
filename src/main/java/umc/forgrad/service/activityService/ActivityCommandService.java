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
import umc.forgrad.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityCommandService {
    private final S3Service s3Service;
    private final ActivityRepository activityRepository;
    private final ActivityFileService activityFileService;
    private final StudentRepository studentRepository;

    @Transactional
    public void save(Activity activity) {
        activityRepository.save(activity);
    }

    @Transactional
    public Activity createBoard(PostActivityRequest.RegistActivity postActivityReq, List<MultipartFile> multipartFiles, String studentId) {

        Student student = studentRepository.findById(Long.parseLong(studentId))
                .orElseThrow(()-> new RuntimeException("현재사용자가 조회되지않습니다."));

        Activity activity = ActivityConverter.toActivity(postActivityReq, multipartFiles, student);



        save(activity);
        if (multipartFiles != null) {
            List<GetS3Res> imgUrls = s3Service.uploadFile(multipartFiles);
            activityFileService.saveAllActivityFileByActivity(imgUrls, activity);
        }

        return activity;
    }
}
