package com.icia.member.service;

import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceimpl implements MemberService {
    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO memberSaveDTO){
        // JpaRepository는 무조건 Entity만 받음.

        // MemberSaveDTO -> MemberENtity
        MemberEntity memberEntity = MemberEntity.saveMember(memberSaveDTO);
//        Long memberId = mr.save(memberEntity).getId();
//        return memberId;
        // 줄여서 쓴게 밑에꺼
        return mr.save(memberEntity).getId();
    }
}
