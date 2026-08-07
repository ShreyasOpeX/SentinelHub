package grpc;

import com.sentinelhub.grpc.case_service.CaseResponse;
import com.sentinelhub.grpc.case_service.CaseServiceGrpc;
import com.sentinelhub.grpc.case_service.CaseStatus;
import com.sentinelhub.grpc.case_service.CreateCaseRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CaseGrpcService extends CaseServiceGrpc.CaseServiceImplBase{

    @Override
    public void createCase(CreateCaseRequest request, StreamObserver<CaseResponse> responseObserver){
        System.out.println("Received CreateCase Request");
        System.out.println("Title: " + request.getTitle());
        System.out.println("Customer: " + request.getCustomerId());

        CaseResponse response = CaseResponse.newBuilder()
                .setCaseId("CASE-001")
                .setTitle(request.getTitle())
                .setDescription(request.getDescription())
                .setCustomerId(request.getCustomerId())
                .setPriority(request.getPriority())
                .setStatus(CaseStatus.OPEN)
                .setAssignedAnalystId("ANALYST-001")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
