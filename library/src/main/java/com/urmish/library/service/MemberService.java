package com.urmish.library.service;

import com.urmish.library.dto.MemberRequestDto;
import com.urmish.library.dto.MemberResponseDto;
import com.urmish.library.model.Member;
import com.urmish.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())){
            throw new RuntimeException("Member with this email already exists!");
        }
        Member member = memberRepository.save(mapToEntity(memberRequestDto));
        return mapToDto(member);
    }

    public List<MemberResponseDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public MemberResponseDto getMemberById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Member not found with ID: " + id));
        return mapToDto(member);
    }

    public MemberResponseDto updateMember(Long id, MemberRequestDto memberRequestDto) {
        Member existingMember = memberRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Member not found with ID: " + id));

        if (!existingMember.getEmail().equals(memberRequestDto.getEmail()) &&
                memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new RuntimeException("Email is already taken by another member!");
        }

        existingMember.setName(memberRequestDto.getName());
        existingMember.setEmail(memberRequestDto.getEmail());

        Member updatedMember = memberRepository.save(existingMember);
        return mapToDto(updatedMember);
    }

    public MemberResponseDto deleteMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Member not found with ID: " + id));
        memberRepository.delete(member);
        return mapToDto(member);
    }

    public MemberResponseDto mapToDto(Member member) {
        MemberResponseDto dto = new MemberResponseDto();
        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setEmail(member.getEmail());
        return dto;
    }

    public Member mapToEntity(MemberRequestDto memberRequestDto) {
        Member member = new Member();
        member.setName(memberRequestDto.getName());
        member.setEmail(memberRequestDto.getEmail());
        return member;
    }
}
