package com.gdw.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.generated.Person;
import thrift.generated.PersonService;

public class ThriftClient {
    public static void main(String[] args) throws TException {
        TSocket socket = new TSocket("127.0.0.1", 8089, 600);
        TTransport transport = new TFramedTransport(socket, 600);
        TProtocol protocol = new TCompactProtocol(transport);
        PersonService.Client client = new PersonService.Client(protocol);

        try{
            transport.open();

            Person person = client.getPersonByUsername("gdw");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            System.out.println("======================");

            Person person2 = new Person();
            person2.setUsername("lisi");
            person2.setAge(26);
            person2.setMarried(false);
            client.savePerson(person2);
        }finally {
            transport.close();
        }
    }
}
