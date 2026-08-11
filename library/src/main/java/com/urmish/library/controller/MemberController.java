package com.urmish.library.controller;

import com.urmish.library.dto.MemberRequestDto;
import com.urmish.library.dto.MemberResponseDto;
import com.urmish.library.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {

    MemberService memberService;
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/member")
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberRequestDto memberRequestDto){
        MemberResponseDto mem = memberService.createMember(memberRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mem);
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponseDto> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable Long id, @RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.updateMember(id, memberRequestDto));
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<MemberResponseDto> deleteMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.deleteMember(id));
    }

}
