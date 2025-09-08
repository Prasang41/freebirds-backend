package com.FreeBirds.FreeBirds.repository;

import com.FreeBirds.FreeBirds.entities.Job;
import com.FreeBirds.FreeBirds.entities.Proposal;
import com.FreeBirds.FreeBirds.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    List<Proposal> findByJob(Job job);

    Optional<Proposal> findByUser_Id(Long id);
}
