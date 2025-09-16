package com.FreeBirds.FreeBirds.services.impl;

import com.FreeBirds.FreeBirds.dtos.ProposalDTO;
import com.FreeBirds.FreeBirds.dtos.Response;
import com.FreeBirds.FreeBirds.entities.Job;
import com.FreeBirds.FreeBirds.entities.Proposal;
import com.FreeBirds.FreeBirds.entities.User;
import com.FreeBirds.FreeBirds.exceptions.NotFoundException;
import com.FreeBirds.FreeBirds.repository.JobRepository;
import com.FreeBirds.FreeBirds.repository.ProposalRepository;
import com.FreeBirds.FreeBirds.repository.UserRepository;
import com.FreeBirds.FreeBirds.services.NotificationService;
import com.FreeBirds.FreeBirds.services.ProposalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ModelMapper mapper;
    private final NotificationService notificationService;

    @Override
    public Response createProposal(ProposalDTO proposalDTO) {
        User freelancer = userRepository.findById(proposalDTO.getFreelancerId())
                .orElseThrow(() -> new NotFoundException("Freelancer not found"));

        Job job = jobRepository.findById(proposalDTO.getJobId())
                .orElseThrow(() -> new NotFoundException("Job not found"));

        User client = job.getClient();

        Proposal proposal = Proposal.builder()
                .bidAmount(proposalDTO.getBidAmount())
                .coverLetter(proposalDTO.getCoverLetter())
                .status(proposalDTO.getStatus())
                .user(freelancer)   // Freelancer submitting
                .job(job)           // Related job
                .client(client)     // Job owner (client)
                .build();

        Proposal saved = proposalRepository.save(proposal);

        // Send notifications
        notificationService.sendProposalNotification(
                saved.getUser().getId(),
                saved.getUser().getEmail(),
                saved.getJob().getTitle(),
                saved.getStatus().name()
        );

        notificationService.sendProposalNotification(
                saved.getClient().getId(),
                saved.getClient().getEmail(),
                saved.getJob().getTitle(),
                "New proposal received"
        );

        return Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Proposal created successfully")
                .proposal(mapper.map(saved, ProposalDTO.class))
                .build();
    }

    @Override
    public Response getProposalById(Long id) {
        Proposal proposal = proposalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Proposal not found"));

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Proposal retrieved successfully")
                .proposal(mapper.map(proposal, ProposalDTO.class))
                .build();
    }

    @Override
    public Response getAllProposals() {
        List<ProposalDTO> proposals = proposalRepository.findAll()
                .stream()
                .map(p -> mapper.map(p, ProposalDTO.class))
                .collect(Collectors.toList());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Proposals retrieved successfully")
                .proposals(proposals)
                .build();
    }

    @Override
    public Response updateProposal(Long id, ProposalDTO proposalDTO) {
        Proposal proposal = proposalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Proposal not found"));

        proposal.setBidAmount(proposalDTO.getBidAmount());
        proposal.setCoverLetter(proposalDTO.getCoverLetter());
        proposal.setStatus(proposalDTO.getStatus());

        if (proposalDTO.getFreelancerId() != null) {
            User freelancer = userRepository.findById(proposalDTO.getFreelancerId())
                    .orElseThrow(() -> new NotFoundException("Freelancer not found"));
            proposal.setUser(freelancer);
        }

        if (proposalDTO.getJobId() != null) {
            Job job = jobRepository.findById(proposalDTO.getJobId())
                    .orElseThrow(() -> new NotFoundException("Job not found"));
            proposal.setJob(job);
            proposal.setClient(job.getClient()); // also update client if job changes
        }

        Proposal updated = proposalRepository.save(proposal);

        notificationService.sendProposalNotification(
                updated.getUser().getId(),
                updated.getUser().getEmail(),
                updated.getJob().getTitle(),
                updated.getStatus().name()
        );

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Proposal updated successfully")
                .proposal(mapper.map(updated, ProposalDTO.class))
                .build();
    }

    @Override
    public Response deleteProposal(Long id) {
        proposalRepository.deleteById(id);

        return Response.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Proposal deleted successfully")
                .build();
    }

    // ✅ New methods using repository queries

    public Response getProposalsByClientId(Long clientId) {
        List<ProposalDTO> proposals = proposalRepository.findByClient_Id(clientId)
                .stream()
                .map(p -> mapper.map(p, ProposalDTO.class))
                .collect(Collectors.toList());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Proposals fetched successfully for client " + clientId)
                .proposals(proposals)
                .build();
    }

    public Response getProposalsByFreelancerId(Long freelancerId) {
        List<ProposalDTO> proposals = proposalRepository.findByUser_Id(freelancerId)
                .stream()
                .map(p -> mapper.map(p, ProposalDTO.class))
                .collect(Collectors.toList());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Proposals fetched successfully for freelancer " + freelancerId)
                .proposals(proposals)
                .build();
    }

    public Response getProposalsByJobId(Long jobId) {
        List<ProposalDTO> proposals = proposalRepository.findByJob_Id(jobId)
                .stream()
                .map(p -> mapper.map(p, ProposalDTO.class))
                .collect(Collectors.toList());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Proposals fetched successfully for job " + jobId)
                .proposals(proposals)
                .build();
    }

    public Response getProposalsByJobIdAndClientId(Long jobId, Long clientId) {
        List<ProposalDTO> proposals = proposalRepository.findByJob_IdAndClient_Id(jobId, clientId)
                .stream()
                .map(p -> mapper.map(p, ProposalDTO.class))
                .collect(Collectors.toList());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Proposals fetched successfully for job " + jobId + " and client " + clientId)
                .proposals(proposals)
                .build();
    }

    public Response getProposalsByJobIdAndFreelancerId(Long jobId, Long freelancerId) {
        List<ProposalDTO> proposals = proposalRepository.findByJob_IdAndUser_Id(jobId, freelancerId)
                .stream()
                .map(p -> mapper.map(p, ProposalDTO.class))
                .collect(Collectors.toList());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Proposals fetched successfully for job " + jobId + " and freelancer " + freelancerId)
                .proposals(proposals)
                .build();
    }
}
