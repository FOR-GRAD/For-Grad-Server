package umc.forgrad.dto;

import lombok.Data;

@Data
public class GetS3Res {
    private String fileUrl;
    private String fileName;

    public GetS3Res(String fileUrl, String fileName) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;
    }
}