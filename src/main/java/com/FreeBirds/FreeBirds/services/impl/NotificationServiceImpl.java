package com.FreeBirds.FreeBirds.services.impl;

import com.FreeBirds.FreeBirds.dtos.Response;
import com.FreeBirds.FreeBirds.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;

    @Override
    public Response sendUserRegistrationNotification(Long userId, String email, String firstName) {
        Response response = new Response();
        try {
            String subject = "Welcome to FreeBirds!";
            String message = "Hi " + firstName + ",\n\nThank you for registering with FreeBirds. Your account ID is " + userId + ".\n\nBest regards,\nFreeBirds Team";

            sendEmail(email, subject, message);

            response.setMessage("User registration notification sent successfully");
            response.setStatus(200);
        } catch (Exception e) {
            response.setMessage("Failed to send user registration notification: " + e.getMessage());
            response.setStatus(500);
        }
        return response;
    }

    @Override
    public Response sendContractStatusNotification(Long userId, String email, String contractTitle, String status) {
        Response response = new Response();
        try {
            String subject = "Contract Status Update";
            String message = "Hi User,\n\nThe contract \"" + contractTitle + "\" has been updated to status: " + status + ".\nUser ID: " + userId + "\n\nBest regards,\nFreeBirds Team";

            sendEmail(email, subject, message);

            response.setMessage("Contract status notification sent successfully");
            response.setStatus(200);
        } catch (Exception e) {
            response.setMessage("Failed to send contract status notification: " + e.getMessage());
            response.setStatus(500);
        }
        return response;
    }

    @Override
    public Response sendProposalNotification(Long userId, String email, String jobTitle, String proposalStatus) {
        Response response = new Response();
        try {
            String subject = "Proposal Status Update";
            String message = "Hi User,\n\nYour proposal for the job \"" + jobTitle + "\" has been " + proposalStatus + ".\nUser ID: " + userId + "\n\nBest regards,\nFreeBirds Team";

            sendEmail(email, subject, message);

            response.setMessage("Proposal notification sent successfully");
            response.setStatus(200);
        } catch (Exception e) {
            response.setMessage("Failed to send proposal notification: " + e.getMessage());
            response.setStatus(500);
        }
        return response;
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
    }
}
