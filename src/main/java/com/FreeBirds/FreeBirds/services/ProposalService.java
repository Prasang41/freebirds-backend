package com.FreeBirds.FreeBirds.services;

import com.FreeBirds.FreeBirds.dtos.ProposalDTO;
import com.FreeBirds.FreeBirds.dtos.Response;

public interface ProposalService {
    Response createProposal(ProposalDTO proposalDTO);
    Response getProposalById(Long id);
    Response getAllProposals();
    Response updateProposal(Long id, ProposalDTO proposalDTO);
    Response deleteProposal(Long id);
}
