package com.FreeBirds.FreeBirds.controllers;

import com.FreeBirds.FreeBirds.dtos.ProposalDTO;
import com.FreeBirds.FreeBirds.dtos.Response;
import com.FreeBirds.FreeBirds.services.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proposals")
@RequiredArgsConstructor
public class ProposalController {

    private final ProposalService proposalService;

    // ✅ Create proposal
    @PostMapping
    public Response createProposal(@RequestBody ProposalDTO proposalDTO) {
        return proposalService.createProposal(proposalDTO);
    }
    // ✅ Get proposal by ID
    @GetMapping("/{id}")
    public Response getProposalById(@PathVariable Long id) {
        return proposalService.getProposalById(id);
    }
    // ✅ Get all proposals
    @GetMapping
    public Response getAllProposals() {
        return proposalService.getAllProposals();
    }
    // ✅ Update proposal
    @PutMapping("/{id}")
    public Response updateProposal(@PathVariable Long id, @RequestBody ProposalDTO proposalDTO) {
        return proposalService.updateProposal(id, proposalDTO);
    }
    // ✅ Delete proposal
    @DeleteMapping("/{id}")
    public Response deleteProposal(@PathVariable Long id) {
        return proposalService.deleteProposal(id);
    }
    // 🔎 Custom Finders
    // Get proposals by client
    @GetMapping("/client/{clientId}")
    public Response getProposalsByClientId(@PathVariable Long clientId) {
        return proposalService.getProposalsByClientId(clientId);
    }
    // Get proposals by freelancer
    @GetMapping("/freelancer/{freelancerId}")
    public Response getProposalsByFreelancerId(@PathVariable Long freelancerId) {
        return proposalService.getProposalsByFreelancerId(freelancerId);
    }
    // Get proposals by job
    @GetMapping("/job/{jobId}")
    public Response getProposalsByJobId(@PathVariable Long jobId) {
        return proposalService.getProposalsByJobId(jobId);
    }
    // Get proposals by job and client
    @GetMapping("/job/{jobId}/client/{clientId}")
    public Response getProposalsByJobAndClient(@PathVariable Long jobId, @PathVariable Long clientId) {
        return proposalService.getProposalsByJobIdAndClientId(jobId, clientId);
    }
    // Get proposals by job and freelancer
    @GetMapping("/job/{jobId}/freelancer/{freelancerId}")
    public Response getProposalsByJobAndFreelancer(@PathVariable Long jobId, @PathVariable Long freelancerId) {
        return proposalService.getProposalsByJobIdAndFreelancerId(jobId, freelancerId);
    }
}
