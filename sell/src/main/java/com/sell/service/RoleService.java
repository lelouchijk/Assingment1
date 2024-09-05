package com.sell.service;

import com.sell.model.Role;
import com.sell.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepo;

    public void saveRole(Role role){
        roleRepo.save(role);
    }

    public void updateRole(long id, Role role){
        role.setRoleId(id);
        roleRepo.save(role);
    }

    public void deleteRole(long id){
        roleRepo.deleteById(id);
    }

    public Role getRole(long id){
        return roleRepo.findById(id).get();
    }

    public Optional<Role> showRoleById(long id){
        return roleRepo.findById(id);
    }


    public List<Role>getAll(){
        return roleRepo.findAll();
    }
}
