package com.FreeBirds.FreeBirds.repository;

import com.FreeBirds.FreeBirds.entities.Job;
import com.FreeBirds.FreeBirds.entities.User;
import com.FreeBirds.FreeBirds.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByClient(User client);
    List<Job> findByStatus(JobStatus status);
}
