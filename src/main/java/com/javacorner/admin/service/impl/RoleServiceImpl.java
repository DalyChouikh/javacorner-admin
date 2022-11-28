package com.javacorner.admin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javacorner.admin.dao.RoleDao;
import com.javacorner.admin.entity.Role;
import com.javacorner.admin.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    RoleDao roleDao;

    

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    
    @Override
    public Role loadRoleByName(String roleName) {
        return roleDao.findByName(roleName);
    }

    @Override
    public Role createRole(String roleName) {
        return roleDao.save(new Role(roleName));
    }

    
    
}
