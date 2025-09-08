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

    @PostMapping
    public Response createProposal(@RequestBody ProposalDTO proposalDTO) {
        return proposalService.createProposal(proposalDTO);
    }

    @GetMapping("/{id}")
    public Response getProposalById(@PathVariable Long id) {
        return proposalService.getProposalById(id);
    }

    @GetMapping
    public Response getAllProposals() {
        return proposalService.getAllProposals();
    }

    @PutMapping("/{id}")
    public Response updateProposal(@PathVariable Long id, @RequestBody ProposalDTO proposalDTO) {
        return proposalService.updateProposal(id, proposalDTO);
    }

    @DeleteMapping("/{id}")
    public Response deleteProposal(@PathVariable Long id) {
        return proposalService.deleteProposal(id);
    }
}
