package com.FreeBirds.FreeBirds.controllers;

import com.FreeBirds.FreeBirds.dtos.ContractDTO;
import com.FreeBirds.FreeBirds.dtos.Response;
import com.FreeBirds.FreeBirds.services.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PostMapping
    public Response createContract(@RequestBody ContractDTO contractDTO) {
        return contractService.createContract(contractDTO);
    }

    @GetMapping("/{id}")
    public Response getContractById(@PathVariable Long id) {
        return contractService.getContractById(id);
    }

    @GetMapping
    public Response getAllContracts() {
        return contractService.getAllContracts();
    }

    @PutMapping("/{id}")
    public Response updateContract(@PathVariable Long id, @RequestBody ContractDTO contractDTO) {
        return contractService.updateContract(id, contractDTO);
    }

    @DeleteMapping("/{id}")
    public Response deleteContract(@PathVariable Long id) {
        return contractService.deleteContract(id);
    }
}
