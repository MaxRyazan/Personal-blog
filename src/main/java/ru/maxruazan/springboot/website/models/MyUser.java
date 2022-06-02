package ru.maxruazan.springboot.website.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "my_users")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NonNull
    @Pattern(regexp = "\\w+@\\w+\\.\\w+", message = "{0-9}{a-z}{а-я} + '@.' + {0-9}{a-z}{а-я}")
    @Column(name = "email")
    private String email;

    @NonNull
    @Size(min = 3, max = 30, message = "Пароль должен быть длинной от 3 до 30 символов.")
    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}
