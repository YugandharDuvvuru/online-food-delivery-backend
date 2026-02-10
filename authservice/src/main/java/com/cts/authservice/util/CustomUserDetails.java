package com.cts.authservice.util;

import com.cts.authservice.entity.UserAuthDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private UserAuthDetails userAuthDetails;

    public CustomUserDetails(UserAuthDetails userAuthDetails) {
        this.userAuthDetails=userAuthDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority("ROLE_"+userAuthDetails.getRoles()));
    }

    @Override
    public String getPassword() {

        return userAuthDetails.getUserPassword();
    }

    @Override
    public String getUsername() {

        return userAuthDetails.getUserEmail();
    }
    public Long getAuthId(){
        return userAuthDetails.getAuthId();
    }
}
