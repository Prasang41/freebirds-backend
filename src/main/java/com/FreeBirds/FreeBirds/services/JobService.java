package com.FreeBirds.FreeBirds.services;

import com.FreeBirds.FreeBirds.dtos.JobDTO;
import com.FreeBirds.FreeBirds.dtos.Response;

public interface JobService {
    Response createJob(JobDTO jobDTO);
    Response getJobById(Long id);
    Response getAllJobs();
    Response updateJob(Long id, JobDTO jobDTO);
    Response deleteJob(Long id);
}
