package umc.forgrad.service.activityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.forgrad.domain.ActivityFile;
import umc.forgrad.repository.ActivityFileRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class activityFileService {
    private final ActivityFileRepository activityFileRepository;
    private final S3Service s3Service;

    @Transactional
    public void savePostPhoto(List<ActivityFile> activityFiles) {
        activityFileRepository.saveAll(activityFiles);
    }
}
