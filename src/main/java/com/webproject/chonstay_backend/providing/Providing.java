package com.webproject.chonstay_backend.providing;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Providing {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long providingId;

    @Column(nullable = false)
    private String providingBody;
}
