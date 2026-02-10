package com.cts.ownerservice.controller;

import com.cts.ownerservice.dto.MessageResponse;
import com.cts.ownerservice.dto.OwnerDetailsDto;
import com.cts.ownerservice.entity.OwnerEntity;
import com.cts.ownerservice.service.OwnerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("owner")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;
    @PostMapping("/save-details")
    public ResponseEntity<MessageResponse> saveOwnerDetails(@RequestBody OwnerEntity ownerEntity){
        return ownerService.saveOwnerDetails(ownerEntity);
    }
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/get-details-by-id/{id}")
    public ResponseEntity<OwnerDetailsDto> getOwnerDetails(@PathVariable Long id){
    	System.out.println("hello world");
    	return ownerService.getOwnerDetailsById(id);
    }
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/update/owner-details/{ownerId}")
    public ResponseEntity<OwnerDetailsDto> updateOwnerDetails(HttpServletRequest request, @PathVariable Long ownerId, @RequestBody OwnerDetailsDto ownerDetails){
        String authHeader=request.getHeader("Authorization");
         return ownerService.updateOwnerById(ownerId,ownerDetails,authHeader);
    }
    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/delete/owner-by/{ownerId}")
    public ResponseEntity<MessageResponse> deleteOwnerById(HttpServletRequest request,@PathVariable Long ownerId){
        String authHeader=request.getHeader("Authorization");
        return ownerService.deleteOwnerById(ownerId,authHeader);
    }


}
