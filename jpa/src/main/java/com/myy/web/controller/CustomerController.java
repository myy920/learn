package com.myy.web.controller;

import com.myy.dao.entity.CustomerEntity;
import com.myy.dao.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    @Resource
    private CustomerRepository customerRepository;

    @PostMapping("")
    public CustomerEntity create(@RequestBody CustomerEntity entity) {
        CustomerEntity create = customerRepository.save(entity);
        return create;
    }

    @PutMapping("")
    public CustomerEntity update(@RequestBody CustomerEntity entity) {
        CustomerEntity update = customerRepository.save(entity);
        return update;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }

    @GetMapping("")
    public List<CustomerEntity> query() {
        List<CustomerEntity> all = customerRepository.findAll();
        return all;
    }

}
