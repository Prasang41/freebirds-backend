package com.FreeBirds.FreeBirds.services.impl;

import com.FreeBirds.FreeBirds.dtos.JobDTO;
import com.FreeBirds.FreeBirds.dtos.Response;
import com.FreeBirds.FreeBirds.entities.Job;
import com.FreeBirds.FreeBirds.entities.User;
import com.FreeBirds.FreeBirds.repository.JobRepository;
import com.FreeBirds.FreeBirds.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public Response createJob(JobDTO jobDTO) {
        // ✅ Find client
        User client = userRepository.findById(jobDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with id " + jobDTO.getClientId()));

        // ✅ Map DTO -> Entity
        Job job = mapper.map(jobDTO, Job.class);
        job.setClient(client); // ✅ Set relation
        Job saved = jobRepository.save(job);

        // ✅ Map back to DTO and include clientId
        JobDTO savedDTO = mapper.map(saved, JobDTO.class);
        savedDTO.setClientId(client.getId());

        return Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Job created successfully")
                .job(savedDTO)
                .build();
    }

    @Override
    public Response getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        JobDTO dto = mapper.map(job, JobDTO.class);
        dto.setClientId(job.getClient().getId());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Job retrieved successfully")
                .job(dto)
                .build();
    }

    @Override
    public Response getAllJobs() {
        List<JobDTO> jobs = jobRepository.findAll()
                .stream()
                .map(job -> {
                    JobDTO dto = mapper.map(job, JobDTO.class);
                    dto.setClientId(job.getClient().getId());
                    return dto;
                })
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

        // ✅ Update client if clientId is provided
        if (jobDTO.getClientId() != null) {
            User client = userRepository.findById(jobDTO.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found with id " + jobDTO.getClientId()));
            job.setClient(client);
        }

        // ✅ Update other fields
        mapper.map(jobDTO, job);
        job.setId(id);

        Job updated = jobRepository.save(job);

        JobDTO updatedDTO = mapper.map(updated, JobDTO.class);
        updatedDTO.setClientId(updated.getClient().getId());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Job updated successfully")
                .job(updatedDTO)
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

    @Override
    public Response getJobsByClientId(Long clientId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client Not Found with this id"));

        List<JobDTO> jobDTOs = jobRepository.findByClient(client).stream()
                .map(job -> {
                    JobDTO dto = mapper.map(job, JobDTO.class);
                    dto.setClientId(job.getClient().getId());
                    return dto;
                })
                .collect(Collectors.toList());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Jobs fetched successfully")
                .jobs(jobDTOs)
                .build();
    }
}
