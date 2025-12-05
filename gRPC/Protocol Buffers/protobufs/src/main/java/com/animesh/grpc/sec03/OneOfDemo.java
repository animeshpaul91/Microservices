package com.animesh.grpc.sec03;

import com.animesh.grpc.sec03.models.Credentials;
import com.animesh.grpc.sec03.models.Email;
import com.animesh.grpc.sec03.models.Phone;
import com.animesh.grpc.sec03.models.School;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OneOfDemo {
    private static final Logger log = LoggerFactory.getLogger(OneOfDemo.class);

    static void main(String[] args) {
        final Email email = Email.newBuilder()
                .setAddress("ani.nitmz@gmail.com")
                .setPassword("password")
                .build();

        final Phone phone = Phone.newBuilder()
                .setNumber(1234567890)
                .setCode(202)
                .build();

        final Credentials emailCredentials = Credentials.newBuilder()
                .setEmail(email)
                .build();

        final Credentials phoneCredentials = Credentials.newBuilder()
                .setPhone(phone)
                .build();

        final Credentials bothCredentials = Credentials.newBuilder()
                .setEmail(email)
                .setPhone(phone)
                .build();

        login(emailCredentials);
        login(phoneCredentials);
        log.info("--- Both Credentials ---");
        login(bothCredentials); // Phone (last one set) will win
    }

    private static void login(Credentials credentials) {
        switch (credentials.getLoginMethodCase()) {
            case EMAIL -> log.info("Login with email: {}", credentials.getEmail());
            case PHONE -> log.info("Login with phone: {}", credentials.getPhone());
        }
    }
}
