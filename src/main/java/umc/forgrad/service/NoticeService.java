package umc.forgrad.service;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.forgrad.apipayload.code.status.ErrorStatus;
import umc.forgrad.domain.Student;
import umc.forgrad.exception.GeneralException;
import umc.forgrad.repository.StudentRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class NoticeService {
    private final StudentRepository studentRepository;

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
            throw new RuntimeException("Failed to load department links with json", e);
        }
        //@PostConstruct가 붙은 initialize 메소드는 서버가 시작될 때 한 번만 실행되어 departmentLinks 맵에 departments.json 파일의 내용을 모두 로딩합니다. 이렇게 하면 getLink 메소드가 호출될 때마다 파일을 읽지 않고, 이미 메모리에 로딩된 데이터를 바로 사용할 수 있습니다.
        //이런 방식은 파일 읽기 연산의 비용을 줄이고, API의 응답 시간을 단축시키는 데 도움이 됩니다. 특히 getLink와 같은 메소드가 자주 호출되는 경우에는 이런 방식이 효율적입니다.
        //그러나 이 방식에는 몇 가지 주의할 점이 있습니다:
        //departments.json 파일의 내용이 변경되면 서버를 재시작해야 departmentLinks 맵이 업데이트됩니다. 이를 해결하기 위해 파일 변경을 감지하고 자동으로 맵을 업데이트하는 코드를 추가할 수 있습니다.
        //departmentLinks 맵이 너무 크면 메모리를 많이 사용할 수 있습니다. 이 경우에는 파일을 분할하거나, 필요한 데이터만 메모리에 로딩하는 등의 방법을 고려해볼 수 있습니다.
    }


    public String getLink(int trackNum, Long studentId)  {
        Student byId = studentRepository.findById(studentId).orElseThrow(()->
        new GeneralException(ErrorStatus.STUDENT_NOT_FOUND));
        String department = null; //""로 할라다가 그냥 null로 함. ""은 null과 다름!!!!!!!!!!!!!
        // 빈 문자열임.department 변수는 trackNum이 1 또는 2일 때만 값을 가집니다.
        // trackNum이 1이나 2가 아닌 다른 값을 가지는 경우에는 department가 값이 없는 상태가 되어야 합니다. 이를 표현하는 데에는 null이 더 적절합니다.
        // department가 null인 경우에 departmentLinks.get(department)가 호출되더라도 Java에서는 오류가 발생하지 않습니다.
        //Java의 Map 인터페이스에서 get 메소드는 키가 null인 경우에도 호출할 수 있습니다. null을 키로 가지는 값이 맵에 없다면 get 메소드는 단순히 null을 반환합니다.
        //그러나 이는 Map 구현체에 따라 다를 수 있습니다. 예를 들어, HashMap은 null 키를 허용하지만, Hashtable은 null 키를 허용하지 않습니다. 따라서 departmentLinks가 Hashtable을 사용하는 경우에는 null 키에 대한 get 호출이 오류를 발생시킵니다.
        //하지만 대부분의 경우 Map 구현체는 null 키를 허용하므로, 이 경우에는 departmentLinks.get(department) 호출이 null을 반환하게 될 것입니다.
        // 따라서 이후에 null 체크를 통해 적절한 예외 처리를 할 수 있습니다.
        if (trackNum ==1) {
            department = byId.getTrack1();
        } else if (trackNum==2) {
            department = byId.getTrack2();

        }

        if (departmentLinks.get(department) == null) {
            throw new GeneralException(ErrorStatus.NOTICE_ERROR);
        }
        return departmentLinks.get(department);
    }

}
