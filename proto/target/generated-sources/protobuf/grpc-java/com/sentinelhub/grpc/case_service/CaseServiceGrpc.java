package com.sentinelhub.grpc.case_service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.2)",
    comments = "Source: case_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CaseServiceGrpc {

  private CaseServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.sentinelhub.case_service.CaseService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.sentinelhub.grpc.case_service.CreateCaseRequest,
      com.sentinelhub.grpc.case_service.CaseResponse> getCreateCaseMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateCase",
      requestType = com.sentinelhub.grpc.case_service.CreateCaseRequest.class,
      responseType = com.sentinelhub.grpc.case_service.CaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.sentinelhub.grpc.case_service.CreateCaseRequest,
      com.sentinelhub.grpc.case_service.CaseResponse> getCreateCaseMethod() {
    io.grpc.MethodDescriptor<com.sentinelhub.grpc.case_service.CreateCaseRequest, com.sentinelhub.grpc.case_service.CaseResponse> getCreateCaseMethod;
    if ((getCreateCaseMethod = CaseServiceGrpc.getCreateCaseMethod) == null) {
      synchronized (CaseServiceGrpc.class) {
        if ((getCreateCaseMethod = CaseServiceGrpc.getCreateCaseMethod) == null) {
          CaseServiceGrpc.getCreateCaseMethod = getCreateCaseMethod =
              io.grpc.MethodDescriptor.<com.sentinelhub.grpc.case_service.CreateCaseRequest, com.sentinelhub.grpc.case_service.CaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateCase"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sentinelhub.grpc.case_service.CreateCaseRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sentinelhub.grpc.case_service.CaseResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CaseServiceMethodDescriptorSupplier("CreateCase"))
              .build();
        }
      }
    }
    return getCreateCaseMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.sentinelhub.grpc.case_service.GetCaseRequest,
      com.sentinelhub.grpc.case_service.CaseResponse> getGetCaseMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCase",
      requestType = com.sentinelhub.grpc.case_service.GetCaseRequest.class,
      responseType = com.sentinelhub.grpc.case_service.CaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.sentinelhub.grpc.case_service.GetCaseRequest,
      com.sentinelhub.grpc.case_service.CaseResponse> getGetCaseMethod() {
    io.grpc.MethodDescriptor<com.sentinelhub.grpc.case_service.GetCaseRequest, com.sentinelhub.grpc.case_service.CaseResponse> getGetCaseMethod;
    if ((getGetCaseMethod = CaseServiceGrpc.getGetCaseMethod) == null) {
      synchronized (CaseServiceGrpc.class) {
        if ((getGetCaseMethod = CaseServiceGrpc.getGetCaseMethod) == null) {
          CaseServiceGrpc.getGetCaseMethod = getGetCaseMethod =
              io.grpc.MethodDescriptor.<com.sentinelhub.grpc.case_service.GetCaseRequest, com.sentinelhub.grpc.case_service.CaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCase"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sentinelhub.grpc.case_service.GetCaseRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sentinelhub.grpc.case_service.CaseResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CaseServiceMethodDescriptorSupplier("GetCase"))
              .build();
        }
      }
    }
    return getGetCaseMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.sentinelhub.grpc.case_service.UpdateCaseStatusRequest,
      com.sentinelhub.grpc.case_service.CaseResponse> getUpdateCaseStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateCaseStatus",
      requestType = com.sentinelhub.grpc.case_service.UpdateCaseStatusRequest.class,
      responseType = com.sentinelhub.grpc.case_service.CaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.sentinelhub.grpc.case_service.UpdateCaseStatusRequest,
      com.sentinelhub.grpc.case_service.CaseResponse> getUpdateCaseStatusMethod() {
    io.grpc.MethodDescriptor<com.sentinelhub.grpc.case_service.UpdateCaseStatusRequest, com.sentinelhub.grpc.case_service.CaseResponse> getUpdateCaseStatusMethod;
    if ((getUpdateCaseStatusMethod = CaseServiceGrpc.getUpdateCaseStatusMethod) == null) {
      synchronized (CaseServiceGrpc.class) {
        if ((getUpdateCaseStatusMethod = CaseServiceGrpc.getUpdateCaseStatusMethod) == null) {
          CaseServiceGrpc.getUpdateCaseStatusMethod = getUpdateCaseStatusMethod =
              io.grpc.MethodDescriptor.<com.sentinelhub.grpc.case_service.UpdateCaseStatusRequest, com.sentinelhub.grpc.case_service.CaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateCaseStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sentinelhub.grpc.case_service.UpdateCaseStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sentinelhub.grpc.case_service.CaseResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CaseServiceMethodDescriptorSupplier("UpdateCaseStatus"))
              .build();
        }
      }
    }
    return getUpdateCaseStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.sentinelhub.grpc.case_service.AssignCaseRequest,
      com.sentinelhub.grpc.case_service.CaseResponse> getAssignCaseMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AssignCase",
      requestType = com.sentinelhub.grpc.case_service.AssignCaseRequest.class,
      responseType = com.sentinelhub.grpc.case_service.CaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.sentinelhub.grpc.case_service.AssignCaseRequest,
      com.sentinelhub.grpc.case_service.CaseResponse> getAssignCaseMethod() {
    io.grpc.MethodDescriptor<com.sentinelhub.grpc.case_service.AssignCaseRequest, com.sentinelhub.grpc.case_service.CaseResponse> getAssignCaseMethod;
    if ((getAssignCaseMethod = CaseServiceGrpc.getAssignCaseMethod) == null) {
      synchronized (CaseServiceGrpc.class) {
        if ((getAssignCaseMethod = CaseServiceGrpc.getAssignCaseMethod) == null) {
          CaseServiceGrpc.getAssignCaseMethod = getAssignCaseMethod =
              io.grpc.MethodDescriptor.<com.sentinelhub.grpc.case_service.AssignCaseRequest, com.sentinelhub.grpc.case_service.CaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AssignCase"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sentinelhub.grpc.case_service.AssignCaseRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sentinelhub.grpc.case_service.CaseResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CaseServiceMethodDescriptorSupplier("AssignCase"))
              .build();
        }
      }
    }
    return getAssignCaseMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CaseServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CaseServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CaseServiceStub>() {
        @java.lang.Override
        public CaseServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CaseServiceStub(channel, callOptions);
        }
      };
    return CaseServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CaseServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CaseServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CaseServiceBlockingStub>() {
        @java.lang.Override
        public CaseServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CaseServiceBlockingStub(channel, callOptions);
        }
      };
    return CaseServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CaseServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CaseServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CaseServiceFutureStub>() {
        @java.lang.Override
        public CaseServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CaseServiceFutureStub(channel, callOptions);
        }
      };
    return CaseServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void createCase(com.sentinelhub.grpc.case_service.CreateCaseRequest request,
        io.grpc.stub.StreamObserver<com.sentinelhub.grpc.case_service.CaseResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateCaseMethod(), responseObserver);
    }

    /**
     */
    default void getCase(com.sentinelhub.grpc.case_service.GetCaseRequest request,
        io.grpc.stub.StreamObserver<com.sentinelhub.grpc.case_service.CaseResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCaseMethod(), responseObserver);
    }

    /**
     */
    default void updateCaseStatus(com.sentinelhub.grpc.case_service.UpdateCaseStatusRequest request,
        io.grpc.stub.StreamObserver<com.sentinelhub.grpc.case_service.CaseResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateCaseStatusMethod(), responseObserver);
    }

    /**
     */
    default void assignCase(com.sentinelhub.grpc.case_service.AssignCaseRequest request,
        io.grpc.stub.StreamObserver<com.sentinelhub.grpc.case_service.CaseResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAssignCaseMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service CaseService.
   */
  public static abstract class CaseServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CaseServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service CaseService.
   */
  public static final class CaseServiceStub
      extends io.grpc.stub.AbstractAsyncStub<CaseServiceStub> {
    private CaseServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CaseServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CaseServiceStub(channel, callOptions);
    }

    /**
     */
    public void createCase(com.sentinelhub.grpc.case_service.CreateCaseRequest request,
        io.grpc.stub.StreamObserver<com.sentinelhub.grpc.case_service.CaseResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateCaseMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getCase(com.sentinelhub.grpc.case_service.GetCaseRequest request,
        io.grpc.stub.StreamObserver<com.sentinelhub.grpc.case_service.CaseResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCaseMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateCaseStatus(com.sentinelhub.grpc.case_service.UpdateCaseStatusRequest request,
        io.grpc.stub.StreamObserver<com.sentinelhub.grpc.case_service.CaseResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateCaseStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void assignCase(com.sentinelhub.grpc.case_service.AssignCaseRequest request,
        io.grpc.stub.StreamObserver<com.sentinelhub.grpc.case_service.CaseResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAssignCaseMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service CaseService.
   */
  public static final class CaseServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CaseServiceBlockingStub> {
    private CaseServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CaseServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CaseServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.sentinelhub.grpc.case_service.CaseResponse createCase(com.sentinelhub.grpc.case_service.CreateCaseRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateCaseMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.sentinelhub.grpc.case_service.CaseResponse getCase(com.sentinelhub.grpc.case_service.GetCaseRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCaseMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.sentinelhub.grpc.case_service.CaseResponse updateCaseStatus(com.sentinelhub.grpc.case_service.UpdateCaseStatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateCaseStatusMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.sentinelhub.grpc.case_service.CaseResponse assignCase(com.sentinelhub.grpc.case_service.AssignCaseRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAssignCaseMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service CaseService.
   */
  public static final class CaseServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<CaseServiceFutureStub> {
    private CaseServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CaseServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CaseServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.sentinelhub.grpc.case_service.CaseResponse> createCase(
        com.sentinelhub.grpc.case_service.CreateCaseRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateCaseMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.sentinelhub.grpc.case_service.CaseResponse> getCase(
        com.sentinelhub.grpc.case_service.GetCaseRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCaseMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.sentinelhub.grpc.case_service.CaseResponse> updateCaseStatus(
        com.sentinelhub.grpc.case_service.UpdateCaseStatusRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateCaseStatusMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.sentinelhub.grpc.case_service.CaseResponse> assignCase(
        com.sentinelhub.grpc.case_service.AssignCaseRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAssignCaseMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_CASE = 0;
  private static final int METHODID_GET_CASE = 1;
  private static final int METHODID_UPDATE_CASE_STATUS = 2;
  private static final int METHODID_ASSIGN_CASE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_CASE:
          serviceImpl.createCase((com.sentinelhub.grpc.case_service.CreateCaseRequest) request,
              (io.grpc.stub.StreamObserver<com.sentinelhub.grpc.case_service.CaseResponse>) responseObserver);
          break;
        case METHODID_GET_CASE:
          serviceImpl.getCase((com.sentinelhub.grpc.case_service.GetCaseRequest) request,
              (io.grpc.stub.StreamObserver<com.sentinelhub.grpc.case_service.CaseResponse>) responseObserver);
          break;
        case METHODID_UPDATE_CASE_STATUS:
          serviceImpl.updateCaseStatus((com.sentinelhub.grpc.case_service.UpdateCaseStatusRequest) request,
              (io.grpc.stub.StreamObserver<com.sentinelhub.grpc.case_service.CaseResponse>) responseObserver);
          break;
        case METHODID_ASSIGN_CASE:
          serviceImpl.assignCase((com.sentinelhub.grpc.case_service.AssignCaseRequest) request,
              (io.grpc.stub.StreamObserver<com.sentinelhub.grpc.case_service.CaseResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCreateCaseMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.sentinelhub.grpc.case_service.CreateCaseRequest,
              com.sentinelhub.grpc.case_service.CaseResponse>(
                service, METHODID_CREATE_CASE)))
        .addMethod(
          getGetCaseMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.sentinelhub.grpc.case_service.GetCaseRequest,
              com.sentinelhub.grpc.case_service.CaseResponse>(
                service, METHODID_GET_CASE)))
        .addMethod(
          getUpdateCaseStatusMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.sentinelhub.grpc.case_service.UpdateCaseStatusRequest,
              com.sentinelhub.grpc.case_service.CaseResponse>(
                service, METHODID_UPDATE_CASE_STATUS)))
        .addMethod(
          getAssignCaseMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.sentinelhub.grpc.case_service.AssignCaseRequest,
              com.sentinelhub.grpc.case_service.CaseResponse>(
                service, METHODID_ASSIGN_CASE)))
        .build();
  }

  private static abstract class CaseServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CaseServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.sentinelhub.grpc.case_service.CaseServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CaseService");
    }
  }

  private static final class CaseServiceFileDescriptorSupplier
      extends CaseServiceBaseDescriptorSupplier {
    CaseServiceFileDescriptorSupplier() {}
  }

  private static final class CaseServiceMethodDescriptorSupplier
      extends CaseServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    CaseServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CaseServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CaseServiceFileDescriptorSupplier())
              .addMethod(getCreateCaseMethod())
              .addMethod(getGetCaseMethod())
              .addMethod(getUpdateCaseStatusMethod())
              .addMethod(getAssignCaseMethod())
              .build();
        }
      }
    }
    return result;
  }
}
