package com.hutech.demo.repository;

import com.hutech.demo.model.Shippers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippersRepository extends JpaRepository<Shippers, Long> {
}
