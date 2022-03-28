package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getByName(String roleName);
    HashSet<Role> getRoleFromSet(String[] receivedRoles);
}