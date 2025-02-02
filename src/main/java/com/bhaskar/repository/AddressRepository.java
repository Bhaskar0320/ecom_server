package com.bhaskar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bhaskar.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
