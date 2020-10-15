package com.omarghorbel.storecrud.service;


import com.omarghorbel.storecrud.entities.AppRole;
import com.omarghorbel.storecrud.entities.AppUser;

public interface AccountService {

    public AppUser saveUser(String username, String password, String confirmedPassword);
    public AppRole save(AppRole role);
    public  AppUser loadUserByUsername(String username);
    public void addRoleToUser(String username, String roleName);


}
