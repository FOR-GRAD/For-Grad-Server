package umc.forgrad.service.activityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.forgrad.apipayload.code.status.ErrorStatus;
import umc.forgrad.converter.ActivityConverter;
import umc.forgrad.domain.Activity;
import umc.forgrad.domain.ActivityFile;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.GetS3Res;
import umc.forgrad.dto.activity.DeleteActivityRequest;
import umc.forgrad.dto.activity.PostActivityRequest;
import umc.forgrad.exception.GeneralException;
import umc.forgrad.repository.ActivityRepository;
import umc.forgrad.repository.StudentRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ActivityCommandService {
    private final S3Service s3Service;
    private final ActivityRepository activityRepository;
    private final ActivityFileService activityFileService;
    private final StudentRepository studentRepository;
    private final ActivityQueryService activityQueryService;


    public void save(Activity activity) {
        activityRepository.save(activity);
    }


    public Activity createBoard(PostActivityRequest.RegistActivity postActivityReq, List<MultipartFile> multipartFiles, Long studentId) throws IOException {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new GeneralException(ErrorStatus.STUDENT_NOT_FOUND));

        Activity activity = ActivityConverter.toActivity(postActivityReq, multipartFiles, student);



        save(activity);
        if (multipartFiles != null) {
            List<GetS3Res> imgUrls = s3Service.uploadFile(multipartFiles);
            activityFileService.saveAllActivityFileByActivity(imgUrls, activity);
        }

        return activity;
    }



    public String deleteActivity(DeleteActivityRequest deleteActivityRequest) throws IOException{

        Activity activity = activityQueryService.findActivity(deleteActivityRequest.getActivityId());

        Student author = activity.getStudent();
        Student user = activityQueryService.findStudent(deleteActivityRequest.getStudentId());

        if (author.getId() == user.getId()) {
            //s3의 파일들 삭제
            List<ActivityFile> allByActivityId = activityFileService.findAllByActivityId(activity.getId());
            activityFileService.deleteAllActivityFilesInS3(allByActivityId);

            List<Long> ids = activityFileService.findAllId(activity.getId());
            activityFileService.deleteAllActivityFiles(ids);

            activityRepository.delete(activity);

            String result = "게시글 삭제가 완료되었습니다";
            return result;

        }
        else{
            throw new GeneralException(ErrorStatus.USER_WITHOUT_PERMISSION);
        }

    }

    public Long updateActivity(Long activityId, Long studentId, PostActivityRequest.UpdateDto updateDto,
                                 List<MultipartFile> multipartFiles, List<Long> deleteFileIds) throws IOException {

        Activity activity = activityQueryService.findActivity(activityId);
        Student author = activity.getStudent();
        Student user = activityQueryService.findStudent(studentId);
        if (author == user) {
            activity.update(updateDto);
            if (deleteFileIds != null) {
                activityFileService.deleteSeveralActivityFiles(deleteFileIds);
            }

            if (multipartFiles != null) {
                List<GetS3Res> imgUrls = s3Service.uploadFile(multipartFiles);
                activityFileService.saveAllActivityFileByActivity(imgUrls, activity);

            }

            return activityId;
        }
        else {
            throw new GeneralException(ErrorStatus.USER_WITHOUT_PERMISSION);
        }





    }
}
