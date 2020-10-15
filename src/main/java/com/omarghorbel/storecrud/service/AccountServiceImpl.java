package com.omarghorbel.storecrud.service;

import com.omarghorbel.storecrud.dao.AppRoleRepository;
import com.omarghorbel.storecrud.dao.AppUserRepository;
import com.omarghorbel.storecrud.entities.AppRole;
import com.omarghorbel.storecrud.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppRoleRepository appRoleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AppUser saveUser(String username, String password, String confirmedPassword) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user!=null) throw new RuntimeException("User already exist");
        if (!password.equals(confirmedPassword)) throw  new RuntimeException("Passwords don't match");
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setActived(true);
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        appUserRepository.save(appUser);

        addRoleToUser(username,"USER");

        return appUser;
    }

    @Override
    public AppRole save(AppRole role) {
        return appRoleRepository.save(role);
    }

    @Override
    public AppUser loadUserByUsername(String username) {

        return appUserRepository.findByUsername(username);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getRoles().add(appRole);

    }
}
