package com.pruebatecnica.servicioavlachile.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pruebatecnica.dto.UserDTO;
import com.pruebatecnica.servicioavlachile.entity.UserEntity;
import com.pruebatecnica.servicioavlachile.repositories.UsersRepository;

@Service
public class UserService {

    @Autowired
    UsersRepository userRepository;

    public UserEntity createUser(UserDTO userDTO) {

        if (userDTO.getName() == null || userDTO.getName().isEmpty() ||
            userDTO.getEmail() == null || userDTO.getEmail().isEmpty() ||
            userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
                
            throw new IllegalArgumentException("Nombre,email y password son campos requeridos");
        }

        return userRepository.save(userDTO.toEntity());
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
