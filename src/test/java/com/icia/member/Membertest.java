package com.icia.member;

import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

// 다로 복사해서 쓴것들
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@SpringBootTest
public class Membertest {
    @Autowired
    private MemberService ms;

    @Test
    @DisplayName("회원데이터생성")
    public void newMembers() {
        IntStream.rangeClosed(1, 15).forEach(i ->{
            ms.save(new MemberSaveDTO("email"+i, "pw"+i, "name"+i));
        });
    }

    /*
        회원삭제 테스트코드 만들어보기
        회원삭제 시나리오를 작성해보고 코드 짜보기
     */

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원삭제 테스트")
    public void memberDeleteTest() {
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("삭제용회원이메일1", "삭제용회원비밀번호1", "삭제용회원이름1");
        Long memberId = ms.save(memberSaveDTO);
        System.out.println(ms.findById(memberId));

        ms.deleteById(memberId);

        // System.out.println(ms.findById(memberId));
        // 삭제한 회원의 id로 조회를 시도했을 때 null 이어야 테스트통과
        // NoSuchElementException은 무시하고 테스트를 수행
        assertThrows(NoSuchElementException.class, () ->{
            assertThat(ms.findById(memberId)).isNull(); // 삭제회원 id 조회결과가 null 이면 테스트 통과
        });

    }


}






















