package com.gbossoufolly.assivitoshopbackend.api.security;

import com.gbossoufolly.assivitoshopbackend.models.LocalUser;
import com.gbossoufolly.assivitoshopbackend.repository.LocalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class JUnitUserDetailsService {

    @Autowired
    private LocalUserRepository localUserRepository;

    public UserDetails loadUserByUsername(String username) {
        Optional<LocalUser> opUser = localUserRepository.findByUsernameIgnoreCase(username);
        if(opUser.isPresent())
            return opUser.get();
        return null;
    }
}
