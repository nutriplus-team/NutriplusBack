package com.nutriplus.NutriPlusBack.Services;

import com.nutriplus.NutriPlusBack.Repositories.ApplicationUserRepository;
import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private ApplicationUserRepository applicationUserRepository;

    public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository)
    {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserCredentials userCredentials = applicationUserRepository.findByUsername(username);
        if(userCredentials == null)
        {
            throw new UsernameNotFoundException(username);
        }

        return new User(userCredentials.username, userCredentials.password, Collections.emptyList());
    }
}