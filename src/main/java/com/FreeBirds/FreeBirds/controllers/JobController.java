package com.FreeBirds.FreeBirds.controllers;

import com.FreeBirds.FreeBirds.dtos.JobDTO;
import com.FreeBirds.FreeBirds.dtos.Response;
import com.FreeBirds.FreeBirds.services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    // ✅ Create Job
    @PostMapping
    public ResponseEntity<Response> createJob(@RequestBody JobDTO jobDTO) {
        Response response = jobService.createJob(jobDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // ✅ Get Job By Id
    @GetMapping("/{id}")
    public ResponseEntity<Response> getJobById(@PathVariable Long id) {
        Response response = jobService.getJobById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // ✅ Get All Jobs
    @GetMapping
    public ResponseEntity<Response> getAllJobs() {
        Response response = jobService.getAllJobs();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // ✅ Update Job
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateJob(@PathVariable Long id, @RequestBody JobDTO jobDTO) {
        Response response = jobService.updateJob(id, jobDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // ✅ Delete Job
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteJob(@PathVariable Long id) {
        Response response = jobService.deleteJob(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
    @GetMapping("/client/{clientId}")
    public ResponseEntity<Response> getJobsByClientId(@PathVariable Long clientId)
    {
        Response response = jobService.getJobsByClientId(clientId);

        return  ResponseEntity
                .status(response.getStatus()).
                body(response);
    }
}