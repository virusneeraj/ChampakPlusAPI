package com.champak.plus.service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class GooglePermissionService {

    Permission insertPermission(Drive service, String fileId) {
        Permission newPermission = new Permission();
        String type = "user";
        String role = "reader";

        newPermission.setEmailAddress("tg31121991@gmail.com");

        newPermission.setType(type);
        newPermission.setRole(role);
        try {
            return service.permissions().create(fileId, newPermission).execute();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
        return null;
    }

    // ...

}