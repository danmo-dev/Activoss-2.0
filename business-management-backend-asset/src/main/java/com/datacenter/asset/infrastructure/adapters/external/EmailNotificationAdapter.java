package com.datacenter.asset.infrastructure.adapters.external;

import com.datacenter.asset.domain.assignment.AssetAssignment;
import com.datacenter.asset.domain.ports.external.out.EmailNotificationPort;

import org.springframework.stereotype.Component;

@Component
public class EmailNotificationAdapter implements EmailNotificationPort {
    @Override
    public void sendAssignmentPendingNotification(String emailTo, AssetAssignment assignment) {
        System.out.println("[Email Sender] Enviando notificación de asignación PENDIENTE a: " + emailTo);
    }

    @Override
    public void sendAssignmentAcceptedNotification(String emailTo, AssetAssignment assignment, String pdfPath) {
        System.out.println("[Email Sender] Enviando notificación de asignación ACEPTADA a: " + emailTo);
        System.out.println("[Email Sender] Adjuntando acta: " + pdfPath);
    }
}