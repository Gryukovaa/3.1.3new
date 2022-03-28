package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }


    @Override
    public Role getByName(String roleName) {
        return roleRepository.findByRole(roleName);
    }

    @Override
    public HashSet<Role> getRoleFromSet(String[] receivedRoles) {
        HashSet<Role> setOfRoles = new HashSet<>();
        for (String roles: receivedRoles) {
            setOfRoles.add(getByName(roles));
        }


        return setOfRoles;
    }
}
