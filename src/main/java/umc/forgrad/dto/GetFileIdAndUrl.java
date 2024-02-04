package umc.forgrad.dto;

import lombok.Getter;


@Getter
public class GetFileIdAndUrl {
    private Long id;
    private String url;

    public GetFileIdAndUrl(Long id, String fileUrl) {
        this.id = id;
        url = fileUrl;
    }
}
