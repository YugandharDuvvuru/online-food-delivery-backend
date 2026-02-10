package com.cts.authservice.util;

import com.cts.authservice.entity.UserAuthDetails;
import com.cts.authservice.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AuthRepository authRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthDetails userExsists = authRepo.findByUserEmail(username);
        if (userExsists==null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomUserDetails(userExsists);
    }
    }

