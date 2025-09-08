package com.FreeBirds.FreeBirds.repository;

import com.FreeBirds.FreeBirds.entities.Contract;
import com.FreeBirds.FreeBirds.entities.Job;
import com.FreeBirds.FreeBirds.entities.User;
import com.FreeBirds.FreeBirds.enums.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByJob(Job job);
    List<Contract> findByStatus(ContractStatus status);

    Optional<Contract> findByUser_Id(Long id);
}
