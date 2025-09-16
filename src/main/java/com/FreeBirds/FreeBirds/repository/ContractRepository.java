package com.FreeBirds.FreeBirds.repository;

import com.FreeBirds.FreeBirds.entities.Contract;
import com.FreeBirds.FreeBirds.entities.Job;
import com.FreeBirds.FreeBirds.entities.User;
import com.FreeBirds.FreeBirds.enums.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    // Find all contracts for a specific job
    List<Contract> findByJob(Job job);

    // Find all contracts by status
    List<Contract> findByStatus(ContractStatus status);

    // Find contracts by client
    List<Contract> findByClient(User client);

    // Find contracts by freelancer
    List<Contract> findByFreelancer(User freelancer);


}
