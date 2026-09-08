package com.datacenter.asset.domain.ports.out;

import com.datacenter.asset.domain.assignment.AssetAssignment;

public interface EmailNotificationPort {
    void sendAssignmentPendingNotification(String emailTo, AssetAssignment assignment);
    void sendAssignmentAcceptedNotification(String emailTo, AssetAssignment assignment, String pdfPath);
}