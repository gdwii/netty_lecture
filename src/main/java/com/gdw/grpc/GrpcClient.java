package com.gdw.grpc;

import com.gdw.proto.Student;
import com.gdw.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;

public class GrpcClient {
    public static void main(String[] args) throws Exception{
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("127.0.0.1", 8089)
                .usePlaintext()
                .build();

        StudentServiceGrpc.StudentServiceBlockingStub studentServiceBlockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);
        Student.MyRequest request = Student.MyRequest.newBuilder().setUsername("username").build();
        Student.MyResponse response = studentServiceBlockingStub.getRealNameByUserName(request);
        System.out.println(response.getRealname());

        System.out.println("========================================");

        Iterator<Student.StudentResponse> iterator =
                studentServiceBlockingStub.getStudentsByAge(Student.StudentRequest.newBuilder().setAge(15).build());
        iterator.forEachRemaining(person -> {
            System.out.println("name:" + person.getName() + ",age:" + person.getAge() + ",city:" + person.getCity());
        });

        System.out.println("========================================");

        StudentServiceGrpc.StudentServiceStub studentServiceStub = StudentServiceGrpc.newStub(managedChannel);
        StreamObserver<Student.StudentResponseList> studentStreamObserverResponse = new StreamObserver<Student.StudentResponseList>() {
            @Override
            public void onNext(Student.StudentResponseList value) {
                System.out.println("-----------------------------------");
                value.getStudentResponseList().forEach(person -> {
                    System.out.println("name:" + person.getName() + ",age:" + person.getAge() + ",city:" + person.getCity());
                });
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("completed!");
            }
        };
        StreamObserver<Student.StudentRequest> studentStreamObserverRequest = studentServiceStub.getStudentsWrapperByAges(studentStreamObserverResponse);
        studentStreamObserverRequest.onNext(Student.StudentRequest.newBuilder().setAge(25).build());
        studentStreamObserverRequest.onNext(Student.StudentRequest.newBuilder().setAge(30).build());
        studentStreamObserverRequest.onNext(Student.StudentRequest.newBuilder().setAge(35).build());
        studentStreamObserverRequest.onCompleted();

        // ========================================
        StreamObserver<Student.StreamResponse> biTalkStreamResponse = new StreamObserver<Student.StreamResponse>() {
            @Override
            public void onNext(Student.StreamResponse value) {
                System.out.println("*******************************************");
                System.out.println("bi talk on next:" + value.getResponseInfo());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("bi talk on error:" + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("bi talk on completed!");
            }
        };
        StreamObserver<Student.StreamRequest> biTalkStreamRequest = studentServiceStub.biTalk(biTalkStreamResponse);
        biTalkStreamRequest.onNext(Student.StreamRequest.newBuilder().setRequestInfo("one").build());
        biTalkStreamRequest.onNext(Student.StreamRequest.newBuilder().setRequestInfo("two").build());
        biTalkStreamRequest.onNext(Student.StreamRequest.newBuilder().setRequestInfo("three").build());
        biTalkStreamRequest.onCompleted();

        System.in.read();
        managedChannel.shutdown();
    }
}