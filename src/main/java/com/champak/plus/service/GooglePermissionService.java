package com.champak.plus.service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class GooglePermissionService {

    @Value("${scuapi.google.permission.type}")
    String type;

    @Value("${scuapi.google.permission.role}")
    String role;

    @Value("${scuapi.google.permission.email}")
    String emails;

    Permission insertPermission(Drive service, String fileId) {
        Permission newPermission = new Permission();
        newPermission.setEmailAddress(emails);
        newPermission.setType(type);
        newPermission.setRole(role);
        try {
            return service.permissions().create(fileId, newPermission).execute();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
        return null;
    }


}