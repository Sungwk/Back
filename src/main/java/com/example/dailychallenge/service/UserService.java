package com.example.dailychallenge.service;

import com.example.dailychallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

}
