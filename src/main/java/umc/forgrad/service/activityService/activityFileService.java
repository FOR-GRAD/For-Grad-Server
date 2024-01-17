package umc.forgrad.service.activityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.forgrad.repository.ActivityFileRepository;

@Service
@RequiredArgsConstructor
public class activityFileService {
    private final ActivityFileRepository activityFileRepository;
    private final S3Service s3Service;
}
