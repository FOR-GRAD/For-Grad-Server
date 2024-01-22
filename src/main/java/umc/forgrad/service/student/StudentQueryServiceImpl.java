package umc.forgrad.service.student;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import umc.forgrad.dto.student.StudentResponseDto;

@Service
public class StudentQueryServiceImpl implements StudentQueryService {

    @Override
    public StudentResponseDto.HomeResponseDto queryHome(HttpSession session) {

        return null;
    }
}
