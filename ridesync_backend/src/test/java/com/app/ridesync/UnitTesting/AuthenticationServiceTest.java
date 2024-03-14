package com.app.ridesync.UnitTesting;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.ridesync.dto.requests.RegisterRequest;
import com.app.ridesync.entities.User;
import com.app.ridesync.repositories.DocumentRepository;
import com.app.ridesync.repositories.UserRepository;
import com.app.ridesync.repositories.VehicleRepository;
import com.app.ridesync.services.AuthenticationService;
import com.app.ridesync.services.JwtService;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationService.class, AuthenticationManager.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AuthenticationServiceTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private DocumentRepository documentRepository;

    @MockBean
    private JavaMailSender javaMailSender;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private VehicleRepository vehicleRepository;

    @Test
    void testUpdateUserDetails() {
        // Arrange
        User user = new User();
        user.setAddress("42 Main St");
        user.setDateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setFullName("Dr Jane Doe");
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setUserId(1);
        user.setVerified(true);

        User user2 = new User();
        user2.setAddress("42 Main St");
        user2.setDateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        user2.setEmail("jane.doe@example.org");
        user2.setFullName("Dr Jane Doe");
        user2.setPassword("123456");
        user2.setPhoneNumber("6625550144");
        user2.setUserId(1);
        user2.setVerified(true);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findByUserId(Mockito.<Integer>any())).thenReturn(user);

        // Act
        User actualUpdateUserDetailsResult = authenticationService.updateUserDetails(new RegisterRequest(), 1);

        // Assert
        verify(userRepository).findByUserId(Mockito.<Integer>any());
        verify(userRepository).save(Mockito.<User>any());
        assertSame(user2, actualUpdateUserDetailsResult);
    }

}
