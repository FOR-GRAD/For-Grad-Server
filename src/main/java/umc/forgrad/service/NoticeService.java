package umc.forgrad.service;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

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
            departmentLinks = mapper.readValue(
                    Paths.get("src/main/resources/departments.json").toFile(),
                    new TypeReference<Map<String, String>>() {}
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to load department links", e);
        }
    }

    public String getLink(String department) {
        return departmentLinks.get(department);
    }

}
