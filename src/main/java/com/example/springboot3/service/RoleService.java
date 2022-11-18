package com.example.springboot3.service;

import com.example.springboot3.entity.Role;
import com.example.springboot3.entity.User;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RoleService {
    void checkBoxRole(String[] checkBoxRoles);

    void addRole(Role role);

    void updateRole(Role role);

    void removeRoleById(long id);

    List<Role> getAllRoles();

    Role getRoleByName(String name);
}
