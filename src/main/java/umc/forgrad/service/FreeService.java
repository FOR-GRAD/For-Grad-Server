package umc.forgrad.service;

import org.springframework.stereotype.Service;
import umc.forgrad.domain.Free;
import umc.forgrad.repository.FreeRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Transactional
@Service
public class FreeService{

    private final FreeRepository freeRepository;

    public FreeService(FreeRepository freeRepository){
        this.freeRepository = freeRepository;
    }

    /**
     * 메모 추가하기
     */
    public Free addMemo(Free free) {
        return freeRepository.save(free);
    }

    /**
     * 메모 조회하기
     */
    public List<Free> findMemos(Long stuId){
        return freeRepository.findAllByStuId(stuId);
    }

//    public Optional<Free> findOne(Long memoId){
//        return freeRepository.findById(memoId);
//    }
}