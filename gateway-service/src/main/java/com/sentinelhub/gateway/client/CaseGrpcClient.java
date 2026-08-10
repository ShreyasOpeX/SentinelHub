package com.sentinelhub.gateway.client;


import com.sentinelhub.grpc.case_service.CaseResponse;
import com.sentinelhub.grpc.case_service.CaseServiceGrpc;
import com.sentinelhub.grpc.case_service.CreateCaseRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class CaseGrpcClient {
    @GrpcClient("case-service")
    private CaseServiceGrpc.CaseServiceBlockingStub blockingStub;
    public CaseResponse createCase(CreateCaseRequest request) {
        return blockingStub.createCase(request);
    }
}
