package com.example.fakebukproject.service.implementations;

import com.example.fakebukproject.domain.entities.Role;
import com.example.fakebukproject.domain.models.service.RoleServiceModel;
import com.example.fakebukproject.repository.RoleRepository;
import com.example.fakebukproject.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void putRolesInDataBase() {
        if(this.roleRepository.count() == 0){
            this.roleRepository.saveAndFlush(new Role("USER"));
            this.roleRepository.saveAndFlush(new Role("ADMIN"));
        }
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(r -> this.modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return this.modelMapper.map(this.roleRepository.findByAuthority(authority),RoleServiceModel.class);
    }
}
