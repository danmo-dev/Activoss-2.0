package com.datacenter.asset.domain.ports.out.external;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;

public interface EmailNotificationPort {
    void sendAssignmentPendingNotification(String emailTo, AssetAssignment assignment);
    void sendAssignmentAcceptedNotification(String emailTo, AssetAssignment assignment, String pdfPath);
    void sendAssignmentRejectedNotification(String emailTo, AssetAssignment assignment, String reason); // <-- agregado
}