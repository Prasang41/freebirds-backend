package com.FreeBirds.FreeBirds.services;

import com.FreeBirds.FreeBirds.dtos.Response;

public interface NotificationService {
    Response sendUserRegistrationNotification(Long userId, String email, String firstName);
    Response sendContractStatusNotification(Long userId, String email, String contractTitle, String status);
    Response  sendProposalNotification(Long userId, String email, String jobTitle, String proposalStatus);

}
