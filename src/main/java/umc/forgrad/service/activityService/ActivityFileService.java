package umc.forgrad.service.activityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.forgrad.domain.Activity;
import umc.forgrad.domain.ActivityFile;
import umc.forgrad.dto.GetS3Res;
import umc.forgrad.repository.ActivityFileRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityFileService {
    private final ActivityFileRepository activityFileRepository;
    private final S3Service s3Service;

    @Transactional
    public void saveActivityFile(List<ActivityFile> activityFiles) {
        activityFileRepository.saveAll(activityFiles);
    }

    @Transactional
    public void saveAllActivityFileByActivity(List<GetS3Res> getS3ResList, Activity activity) {
        // ActivityFile 리스트를 받아옴
        List<ActivityFile> activityFiles = new ArrayList<>();
        for (GetS3Res getS3Res : getS3ResList) {
            ActivityFile newActivityFile = ActivityFile.builder()
                    .fileUrl(getS3Res.getFileUrl())
                    .fileName(getS3Res.getFileName())
                    .build();
            activityFiles.add(newActivityFile);
            activity.addFileList(newActivityFile);
        }
        saveActivityFile(activityFiles);

    }

    @Transactional(readOnly = true)
    public List<String> getFileUrls(Long activityId) {
        List<ActivityFile> allByActivityId = activityFileRepository.findAllByActivityId(activityId);
        List<String> fileUrls = new ArrayList<>();
        for (ActivityFile activityFile : allByActivityId) {
            fileUrls.add(activityFile.getFileUrl());
        }
        return fileUrls;
    }

    public List<ActivityFile> findAllByActivityId(Long activityId){
        return activityFileRepository.findAllByActivityId(activityId);

    }

    public void deleteAllActivityFilesInS3(List<ActivityFile> activityFiles) {

        for (ActivityFile activityFile : activityFiles) {
            s3Service.deleteFile(activityFile.getFileName());
        }
    }
    public List<Long> findAllId(Long id){
        return activityFileRepository.findAllId(id);
    }

    public void deleteAllActivityFiles(List<Long> ids) {

        activityFileRepository.deleteAllByActivityIds(ids);
    }
}
