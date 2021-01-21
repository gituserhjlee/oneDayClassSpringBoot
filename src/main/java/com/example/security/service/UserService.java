package com.example.security.service;

import com.example.security.dto.UserDto;
import com.example.security.entity.user.User;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Spring Security 필수 메소드 구현
     *
     * @param email 이메일
     * @return UserDetails
     * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
     */
    @Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 User로 반환 타입 지정 (자동으로 다운 캐스팅됨)
    public User loadUserByUsername(String email) throws UsernameNotFoundException { // 시큐리티 필수 메소드임
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException((email)));
    }

    public Long save(UserDto infoDto) {//회원가입하면 회원정보를 저장
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword())); //비밀번호를 암호화해서 저장
        validateDuplicateMember(infoDto);
        return userRepository.save(User.builder()
                .email(infoDto.getEmail())
                .auth(infoDto.getAuth())
                .password(infoDto.getPassword()).build()).getCode();
    }

    private void validateDuplicateMember(UserDto infoDto) {
        Optional<User> findUsers=userRepository.findByEmail(infoDto.getEmail());
        if(!findUsers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }



}