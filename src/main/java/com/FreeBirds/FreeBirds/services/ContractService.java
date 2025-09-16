package com.FreeBirds.FreeBirds.services;

import com.FreeBirds.FreeBirds.dtos.ContractDTO;
import com.FreeBirds.FreeBirds.dtos.Response;

public interface ContractService {
    Response createContract(ContractDTO contractDTO);
    Response getContractById(Long id);
    Response getAllContracts();
    Response updateContract(Long id, ContractDTO contractDTO);
    Response deleteContract(Long id);
}
