package com.gdw.grpc;

import com.gdw.proto.Student;
import com.gdw.proto.StudentServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.stream.IntStream;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {
    @Override
    public void getRealNameByUserName(Student.MyRequest request, StreamObserver<Student.MyResponse> responseObserver) {
        System.out.println("receive:" + request.getUsername());

        responseObserver.onNext(Student.MyResponse.newBuilder().setRealname("gdw").build());
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentsByAge(Student.StudentRequest request, StreamObserver<Student.StudentResponse> responseObserver) {
        System.out.println("receive:" + request.getAge());

        IntStream.range(0, 10).forEach(index -> {
            Student.StudentResponse response = Student.StudentResponse.newBuilder()
                    .setAge(request.getAge())
                    .setCity("北京" + index)
                    .setName("name" + index).build();
            responseObserver.onNext(response);
        });

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Student.StudentRequest> getStudentsWrapperByAges(StreamObserver<Student.StudentResponseList> responseObserver) {
        return new StreamObserver<Student.StudentRequest>() {
            private Student.StudentResponseList.Builder responseListBuilder = Student.StudentResponseList.newBuilder();
            @Override
            public void onNext(Student.StudentRequest value) {
                System.out.println("onNext:" + value.getAge());
                responseListBuilder.addStudentResponse(
                        Student.StudentResponse.newBuilder()
                                .setName(value.getAge() + "的gdw")
                                .setAge(value.getAge())
                                .setCity("beijin" + value.getAge())
                                .build());
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(responseListBuilder.build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<Student.StreamRequest> biTalk(StreamObserver<Student.StreamResponse> responseObserver) {
        return new StreamObserver<Student.StreamRequest>() {
            @Override
            public void onNext(Student.StreamRequest value) {
                System.out.println("bi talk on next:" + value.getRequestInfo());
                responseObserver.onNext(Student.StreamResponse.newBuilder().setResponseInfo("reponse").build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("bi talk error:" + t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
                System.out.println("bi talk completed");
            }
        };
    }
}
