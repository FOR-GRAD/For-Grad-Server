package umc.forgrad.service;

import org.springframework.stereotype.Service;
import umc.forgrad.domain.Free;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.FreeDto;
import umc.forgrad.repository.FreeRepository;
import umc.forgrad.repository.StudentRepository;

@Service
public class FreeService{

    private final FreeRepository freeRepository;

    private final StudentRepository studentRepository;

    public FreeService(FreeRepository freeRepository, StudentRepository studentTrpository){
        this.freeRepository = freeRepository;
        this.studentRepository = studentTrpository;
    }

    /**
     * 메모 추가하기
     */
    public FreeDto.MemoResponseDto addMemo(FreeDto.MemoRequestDto dto, long stuId) {

        Student student = studentRepository.findById(stuId).orElseThrow();

        Free free = Free.builder()
                .memo(dto.getMemo())
                .student(student)
                .build();

        Free savedFree = freeRepository.save(free);

        return FreeDto.MemoResponseDto.builder()
                .memo(savedFree.getMemo())
                .build();
    }

    /**
     * 메모 조회하기
     */
    public FreeDto.MemoResponseDto findMemos(long stuId){

        Free byStudentId = freeRepository.findByStudentId(stuId);

        return FreeDto.MemoResponseDto.builder()
                .memo(byStudentId.getMemo())
                .build();
    }

    /**
     * 메모 수정하기
     */
    public FreeDto.MemoResponseDto updateMemo(FreeDto.MemoRequestDto dto, long stuId) {

        Free byStudentId = freeRepository.findByStudentId(stuId);

        Free updatedFree = Free.builder()
                .id(byStudentId.getId())
                .student(byStudentId.getStudent())
                .memo(dto.getMemo())
                .build();

        updatedFree = freeRepository.save(updatedFree);

        return FreeDto.MemoResponseDto.builder()
                .memo(updatedFree.getMemo())
                .build();
    }

//    public Optional<Free> findOne(Long memoId){
//        return freeRepository.findById(memoId);
//    }
}