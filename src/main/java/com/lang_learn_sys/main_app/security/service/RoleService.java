package com.lang_learn_sys.main_app.security.service;

import com.lang_learn_sys.main_app.security.entity.Role;
import com.lang_learn_sys.main_app.security.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository theRoleRepository;

    @Transactional
    public List<Role> getAllRoles(){
        return theRoleRepository.findAll();
    }
    @Transactional
    public Role getRoleById(Long id){return theRoleRepository.getById(id); }

    @Transactional
    public boolean deleteRoleById(Long id){
        if(getRoleById(id)==null)
            return false;
        theRoleRepository.delete(getRoleById(id));
        return true;
    }
    @Transactional
    public boolean addOrUpdateRole(Role theRole){
        theRoleRepository.save(theRole);
        return true;
    }
}
