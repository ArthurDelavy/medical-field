package com.medicalfield.core.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.medicalfield.core.auth.dto.RegisterRequest;
import com.medicalfield.core.auth.exceptions.custom.DuplicatedValueException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService{

    private final UserRepository usrRep;

    private final PasswordEncoder pswEcd;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usrRep.findByIdentity(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }


    @Transactional
    public void registerUser(@Valid RegisterRequest data) {

        if (usrRep.existsByIdentityOrEmail(data.identity().trim(), data.email().trim())) throw new DuplicatedValueException("Identidade e/ou Email já cadastrados.");

        String password = pswEcd.encode(data.password());

        User user = User.builder()
            .identity(data.identity())
            .name(data.name())
            .email(data.email())
            .password(password)
            .build();

        usrRep.save(user);
    }
    
}
