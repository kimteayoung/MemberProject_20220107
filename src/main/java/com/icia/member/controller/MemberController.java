package com.icia.member.controller;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.icia.member.common.SessionConst.LOGIN_EMAIL;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService ms;

    // 회원가입폼
    @GetMapping("save")
    public String saveForm(){
        return "member/save";
    }

    // 회원가입 처리
    @PostMapping("save")
    public String save(@ModelAttribute MemberSaveDTO memberSaveDTO){
        Long memberId = ms.save(memberSaveDTO);
        return "member/login";
    }

    // 로그인폼
    @GetMapping("login")
    public String loginForm(Model model){
        model.addAttribute("login",new MemberLoginDTO());
        return "member/login";
    }

    // 로그인
    @PostMapping("login")
    public String login(@ModelAttribute MemberLoginDTO memberLoginDTO, HttpSession session ){
//                        @RequestParam(defaultValue = "/") String redirectURL )
        System.out.println("MemberController.login");
//        System.out.println("redirectURL = " + redirectURL);
        boolean loginResult = ms.login(memberLoginDTO);
        if (loginResult) {
            session.setAttribute(LOGIN_EMAIL, memberLoginDTO.getMemberEmail());
//             return "redirect:/member/";
//             return "member/mypage";
//            String redirectURL = (String) session.getAttribute("redirectURL");
            // 인터셉터를 거쳐서 오면 redirectURL에 값이 있을 것이고 그냥 로그인을 해서 오면 redirectURL에 값이 없을것임.
            // 따라서 if else로 구분을 해줌.
//            if(redirectURL != null) {
//                return "redirect:" + redirectURL; // 사용자가 요청한 주소로 보내주기 위해
            return "member/mypage";
        }else {
            return "member/login";
        }
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    // 회원목록
    @GetMapping
    public String findAll(Model model) {
        List<MemberDetailDTO> memberList = ms.findAll();
        model.addAttribute("memberList", memberList);
        return "member/findAll";
    }

    // 회원조회 (/member/5)
    @GetMapping("{memberId}")
    public String findById(@PathVariable("memberId") Long memberId, Model model) {
        MemberDetailDTO member = ms.findById(memberId);
        model.addAttribute("member", member);
        return "member/findById";
    }

    // 회원조회 ajax
    @PostMapping("{memberId}")
    public @ResponseBody MemberDetailDTO detail(@PathVariable("memberId") Long memberId){
        MemberDetailDTO member = ms.findById(memberId);
        return member;
    }

    // 회원삭제 (/member/delete/5)
    @GetMapping("delete/{memberId}")
    public String deleteById(@PathVariable("memberId") Long memberId) {
        ms.deleteById(memberId);
        return "redirect:/member/";
    }

    // 회원삭제 (/member/5)
    @DeleteMapping("{memberId}")
    public ResponseEntity deleteById2(@PathVariable Long memberId) {
        ms.deleteById(memberId);
        /*
            // 단순 화면 출력이 아닌 데이터를 리턴하고할 때 사용하는 리턴방식식
           ResponseEntity: 데이터 & 상태코드(200, 400, 404, 405, 500 등)를 함께 리턴할 수 있음.
            @ResponseBody: 데이터를 리턴할 수 있음.
         */
        // 200 코드를 리턴
        return new ResponseEntity(HttpStatus.OK);
    }

    // 수정화면 요청
    @GetMapping("update")
    public String updateForm(Model model, HttpSession session) {
        String memberEmail = (String) session.getAttribute(LOGIN_EMAIL);
        MemberDetailDTO member = ms.findByIdEmail(memberEmail);
        model.addAttribute("member", member);
        return "member/update";
    }

    // 수정처리(post)
    @PostMapping("update")
    public String update(@ModelAttribute MemberDetailDTO memberDetailDTO) {
        Long memberId = ms.update(memberDetailDTO);
        // 수정완료 후 해당회원의 상세페이지(findBy.html) 출력
        return "redirect:/member/" + memberDetailDTO.getMemberId();

    }

    // 수정처리(put)
    @PutMapping("{memberId}")
    // json 으로 데이터가 전달되면 RequestBody로 받아줘야함.
    public ResponseEntity update2(@RequestBody MemberDetailDTO memberDetailDTO) {
        Long memberId = ms.update(memberDetailDTO);
        return new ResponseEntity(HttpStatus.OK);
    }



}
