package com.FreeBirds.FreeBirds.services;

import com.FreeBirds.FreeBirds.dtos.ProposalDTO;
import com.FreeBirds.FreeBirds.dtos.Response;

public interface ProposalService {
    Response createProposal(ProposalDTO proposalDTO);
    Response getProposalById(Long id);
    Response getAllProposals();
    Response updateProposal(Long id, ProposalDTO proposalDTO);
    Response deleteProposal(Long id);

    // 🔎 Custom Finders
    Response getProposalsByClientId(Long clientId);
    Response getProposalsByFreelancerId(Long freelancerId);
    Response getProposalsByJobId(Long jobId);
    Response getProposalsByJobIdAndClientId(Long jobId, Long clientId);
    Response getProposalsByJobIdAndFreelancerId(Long jobId, Long freelancerId);
}
