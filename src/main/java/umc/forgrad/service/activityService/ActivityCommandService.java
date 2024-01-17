package umc.forgrad.service.activityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.forgrad.domain.Activity;
import umc.forgrad.dto.GetS3Res;
import umc.forgrad.dto.PostActivityRequest;
import umc.forgrad.repository.ActivityRepository;

import javax.swing.text.StyledEditorKit;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityCommandService {
    private final S3Service s3Service;
    private final ActivityRepository activityRepository;

    @Transactional
    public void save(Activity activity) {
        activityRepository.save(activity);
    }

    @Transactional
    public String createBoard(PostActivityRequest postActivityReq, List<MultipartFile> multipartFiles) {

        Activity activity = Activity.builder()
                .title(postActivityReq.getTitle())
                .content(postActivityReq.getContent())
                .fileList(new ArrayList<>())
                .build();
        save(activity);


        if (multipartFiles != null) {
            List<GetS3Res> imgUrls = s3Service.uploadFile(multipartFiles);
        }

        return "activityId: " + activity.getId() + "인 게시글을 생성했습니다.";
    }
}
