package org.example.autobase.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autobase.entity.AppUser;
import org.example.autobase.repository.UserRepository;
import org.example.autobase.repository.UserRoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser = this.userRepository.findUserAccount(userName)
            .orElseThrow(RuntimeException::new);

        if (appUser == null) {
           log.error("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        log.debug("Found User: " + appUser);

        List<String> roleNames = this.userRoleRepository.getRoleNames(appUser.getId());

        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roleNames != null) {
            grantList = roleNames.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        return new User(appUser.getUserName(), appUser.getEncryptedPassword(), grantList);
    }

}
