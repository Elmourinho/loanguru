package com.elmar.loanguru.service;

import com.elmar.loanguru.model.User;
import com.elmar.loanguru.model.dto.UserDto;
import com.elmar.loanguru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto add(String username) {
        User user = new User();
        user.setUsername(username);
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

}
