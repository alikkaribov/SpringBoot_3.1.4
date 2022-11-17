package com.example.springboot3.controller;


import com.example.springboot3.entity.Role;
import com.example.springboot3.entity.User;
import com.example.springboot3.service.RoleService;
import com.example.springboot3.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@Controller
public class UserController {

    private UserServiceImpl userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/user")
    public String userInfo(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "userpage";
    }

    @GetMapping(value = "/admin")
    public String listUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", user);
        return "all-user";
    }

    @GetMapping(value = "/admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "info";
    }

    @PostMapping(value = "/admin/add-user")
    public String addUser(@ModelAttribute User user, @RequestParam(value = "checkBoxRoles") String[] checkBoxRoles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : checkBoxRoles) {
            roleSet.add(roleService.getRoleByName(role));
        }
        user.setRoles(roleSet);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/{id}/edit")
    public String editUserForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }

    @PatchMapping(value = "/{id}")
    public String editUser(@ModelAttribute User user, @RequestParam(value = "checkBoxRoles") String[] checkBoxRoles) {
        Set<Role> roleSet = new HashSet<>();
        for (String roles : checkBoxRoles) {
            roleSet.add(roleService.getRoleByName(roles));
        }
        user.setRoles(roleSet);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/remove/{id}")
    public String removeUser(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @GetMapping(value ="/login")
    public String login(){
        return "login";
    }
}
