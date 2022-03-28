package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    /*public AdminController(UserService userService) {
        this.userService = userService;
    }*/

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /* @GetMapping()
    public String getUsers(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("listUsers", users);
        return "users";
    }*/

    // CREATE
    @PostMapping("/create")
    public String saveUser(@ModelAttribute User user) {

        System.out.println(user.toString());
        userService.saveUser(user);
        return "redirect:/admin/users";
    }


    @GetMapping("/users")
    public String showAllUsers(Model model){

        List<User> users = userService.getAllUsers();
        model.addAttribute("listUsers", users);

        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("allRoles", roles);

        model.addAttribute("newUser", new User());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("findedUser", user );

        System.out.println(userService.findByUsername(user.getEmail()));

        return "users";
    }

    // UPDATE
    @GetMapping(value = "/edit/{id}")
    public String getForEditUser(@PathVariable("id") Long id, ModelMap model) {
        User user = userService.getUser(id);
        model.addAttribute("editedUser",user);
        model.addAttribute("editedRole", roleService.getAllRoles());
        return "/users";
    }

    @PostMapping(value = "/edit/{id}")
    public String editUser(@PathVariable("id") Long id, @ModelAttribute("user") User user, @RequestParam(value = "receivedRoles") String[] receivedRoles) {
        User newUser =  userService.getUser(id);

        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setRoles(roleService.getRoleFromSet(receivedRoles));
        userService.saveUser(newUser);
        return "redirect:/admin/users";
    }

    // DELETE
    @PostMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}