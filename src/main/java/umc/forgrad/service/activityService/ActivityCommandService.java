//package umc.forgrad.service.activityService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.forgrad.domain.Activity;

import javax.swing.text.StyledEditorKit;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

//@Service
//public class ActivityCommandService {
//
//    @Transactional
//    public String createBoard(PostActivityReq postActivityReq, List<MultipartFile> multipartFiles) {

//        Activity activity = Activity.builder()
//                .title(postActivityReq.getTitle())
//                .contents(postActivityReq.getContent())
//                .photoList(new ArrayList<>())
//                .build();
//        save(activity);
//
//
//        if (multipartFiles != null) {
//            List<GetS3Res> imgUrls = s3Service.uploadFile(mul)
//        }
//        }
//}
