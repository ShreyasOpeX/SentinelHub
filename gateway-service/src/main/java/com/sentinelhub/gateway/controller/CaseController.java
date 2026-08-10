package com.sentinelhub.gateway.controller;


import com.sentinelhub.gateway.client.CaseGrpcClient;
import com.sentinelhub.grpc.case_service.CaseResponse;
import com.sentinelhub.grpc.case_service.CreateCaseRequest;
//import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cases")
public class CaseController {
    private final CaseGrpcClient caseGrpcClient;

    public CaseController(CaseGrpcClient caseGrpcClient) {
        this.caseGrpcClient = caseGrpcClient;
    }

    @PostMapping
    public CaseResponse createCase(@RequestParam String title, @RequestParam String description, @RequestParam String customerID) {
        CreateCaseRequest request = CreateCaseRequest.newBuilder()
                .setTitle(title)
                .setDescription(description)
                .setCustomerId(customerID)
                .build();
        return caseGrpcClient.createCase(request);
    }
}
