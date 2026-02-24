package com.ex.basic.service;

import com.ex.basic.config.security.CustomUserDetails;
import com.ex.basic.entity.MemberEntity;
import com.ex.basic.exception.MemberNotFoundException;
import com.ex.basic.repository.BasicMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final BasicMemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository
                .findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("로그인 실패"));

        return new CustomUserDetails(
                memberEntity.getId(),
                memberEntity.getUsername(),
                memberEntity.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + memberEntity.getRole()))
        );
        /*
        return User.builder()
                .username(memberEntity.getUsername())
                .password(memberEntity.getPassword())
                .roles(memberEntity.getRole())
                .build();

         */
    }
}
