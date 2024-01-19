package umc.forgrad.service;

import umc.forgrad.repository.FreeRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class FreeService{

    private final FreeRreository freeRepository;

    public FreeService(FreeRepository freeRepository){
        this.freeRepository = freeRepository;
    }

    /**
     * 메모 추가하기
     */
    public Long addMemo(Free free){
        freeRepository.save(free);
        return free.getId();
    }

    /**
     * 메모 조회하기
     */
    public List<Free> findMemos(){
        return freeRepository.findAll();
    }

    public Optional<Free> findOne(Long memoId){
        return freeRepository.findById(memoId);
    }
}