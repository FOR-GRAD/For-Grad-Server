package umc.forgrad.service;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import umc.forgrad.apipayload.code.status.ErrorStatus;
import umc.forgrad.exception.GeneralException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;


@Service
public class NoticeService {

    private Map<String, String> departmentLinks;

    @PostConstruct
    public void initialize() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String path;

            String env = System.getenv("ENV");
            if (env != null && env.equals("PROD")) {
                path = "/app/src/main/resources/departments.json";
            } else {
                path = "src/main/resources/departments.json";
            }

            departmentLinks = mapper.readValue(
                    new File(path),
                    new TypeReference<Map<String, String>>() {}
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to load department links", e);
        }
    }


    public String getLink(String department)  {
        if (departmentLinks.get(department) == null) {
            throw new GeneralException(ErrorStatus.NOTICE_ERROR);
        }
        return departmentLinks.get(department);
    }

}
