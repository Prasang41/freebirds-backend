package com.FreeBirds.FreeBirds.services.impl;

import com.FreeBirds.FreeBirds.dtos.JobDTO;
import com.FreeBirds.FreeBirds.dtos.Response;
import com.FreeBirds.FreeBirds.entities.Job;
import com.FreeBirds.FreeBirds.repository.JobRepository;
import com.FreeBirds.FreeBirds.services.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final ModelMapper mapper;

    @Override
    public Response createJob(JobDTO jobDTO) {
        Job job = mapper.map(jobDTO, Job.class);
        Job saved = jobRepository.save(job);

        return Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Job created successfully")
                .job(mapper.map(saved, JobDTO.class))
                .build();
    }

    @Override
    public Response getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Job retrieved successfully")
                .job(mapper.map(job, JobDTO.class))
                .build();
    }

    @Override
    public Response getAllJobs() {
        List<JobDTO> jobs = jobRepository.findAll()
                .stream()
                .map(j -> mapper.map(j, JobDTO.class))
                .collect(Collectors.toList());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Jobs retrieved successfully")
                .jobs(jobs)
                .build();
    }

    @Override
    public Response updateJob(Long id, JobDTO jobDTO) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        mapper.map(jobDTO, job);
        job.setId(id);
        Job updated = jobRepository.save(job);

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Job updated successfully")
                .job(mapper.map(updated, JobDTO.class))
                .build();
    }

    @Override
    public Response deleteJob(Long id) {
        jobRepository.deleteById(id);

        return Response.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Job deleted successfully")
                .build();
    }
}
