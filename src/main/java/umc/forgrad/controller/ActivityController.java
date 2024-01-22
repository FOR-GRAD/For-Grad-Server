package umc.forgrad.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.domain.Activity;
import umc.forgrad.dto.PostActivityRequest;
import umc.forgrad.service.activityService.ActivityCommandService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ActivityController {

//    @Autowired
//    private AmazonS3Client amazonS3Client;
//
//    @PostMapping("/Activities")
//    public void streamUpload(HttpServletRequest request) throws IOException {
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentType(request.getContentType());
//        metadata.setContentLength(request.getContentLengthLong());
//
//        amazonS3Client.putObject("bucketName", "objectKey", request.getInputStream(), metadata);
//    }

    private final ActivityCommandService activityCommandService;

    @PostMapping("/activity")
    public ApiResponse<String> createActivity(@RequestPart(value = "image", required = false)List<MultipartFile> multipartFiles,
                                              @RequestParam(value = "title") String title,
                                              @RequestParam(value = "content") String content) {

            PostActivityRequest postActivityRequest = new PostActivityRequest(title, content);
            return ApiResponse.onSuccess(activityCommandService.createBoard(postActivityRequest, multipartFiles));


    }
}
