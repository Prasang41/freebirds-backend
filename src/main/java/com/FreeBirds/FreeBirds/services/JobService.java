package com.FreeBirds.FreeBirds.services;

import com.FreeBirds.FreeBirds.dtos.JobDTO;
import com.FreeBirds.FreeBirds.dtos.Response;
import com.FreeBirds.FreeBirds.entities.User;

public interface JobService {
    Response createJob(JobDTO jobDTO);
    Response getJobById(Long id);
    Response getAllJobs();
    Response updateJob(Long id, JobDTO jobDTO);
    Response deleteJob(Long id);
    Response getJobsByClientId(Long clientId);
}
