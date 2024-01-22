package umc.forgrad.service.student;

import umc.forgrad.dto.student.StudentRequestDto;

public interface StudentCommandService {

    String login(StudentRequestDto.LoginRequestDto loginRequestDto);

}
