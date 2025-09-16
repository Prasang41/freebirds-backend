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
    List<Proposal> findByClient_Id(Long clientId);
    List<Proposal> findByUser_Id(Long freelancerId);
    List<Proposal> findByJob_Id(Long jobId);
    List<Proposal> findByJob_IdAndClient_Id(Long jobId, Long clientId);
    List<Proposal> findByJob_IdAndUser_Id(Long jobId, Long freelancerId);

}
