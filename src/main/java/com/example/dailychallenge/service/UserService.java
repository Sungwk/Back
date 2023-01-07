package com.example.dailychallenge.service;

import com.example.dailychallenge.dto.UserDto;
import com.example.dailychallenge.dto.UserEditor;
import com.example.dailychallenge.entity.User;
import com.example.dailychallenge.exception.UserNotFound;
import com.example.dailychallenge.repository.UserRepository;
import com.example.dailychallenge.vo.RequestUpdateUser;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public User saveUser(UserDto userDto, PasswordEncoder passwordEncoder) {
        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // 비밀번호 암호화해서 저장
        userRepository.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if(user == null)
            throw new UsernameNotFoundException(username);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                true, true, true, true,
                new ArrayList<>()
        );
    }

    public UserDto getUserDetailsByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        UserDto userDto = new ModelMapper().map(user, UserDto.class);
        return userDto;
    }

    /** to do
     * 회원 가입 시 중복 회원 예외 처리
     * 로그인 실패 시 예외 처리 ( 1. 비밀번호, 2. 없는 회원 )
     */


    public void updateUser(Long userId, RequestUpdateUser requestUpdateUser, PasswordEncoder passwordEncoder) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        UserEditor.UserEditorBuilder editorBuilder = findUser.toEditor();
        UserEditor userEditor = editorBuilder
                .userName(requestUpdateUser.getUserName())
                .email(requestUpdateUser.getEmail())
                .password(passwordEncoder.encode(requestUpdateUser.getPassword()))
                .info(requestUpdateUser.getInfo())
                .build();

        findUser.update(userEditor);
    }

    public void delete(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        userRepository.delete(findUser);
    }
}
