package com.app.ridesync.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.ridesync.dto.requests.RegisterRequest;
import com.app.ridesync.entities.User;
import com.app.ridesync.repositories.DocumentRepository;
import com.app.ridesync.repositories.UserRepository;
import com.app.ridesync.repositories.VehicleRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AccountExpiredException;
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

    /**
     * Method under test:
     * {@link AuthenticationService#updateUserDetails(RegisterRequest, Integer)}
     */
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
        user2.setPassword("iloveyou");
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

    /**
     * Method under test:
     * {@link AuthenticationService#updateUserDetails(RegisterRequest, Integer)}
     */
    @Test
    void testUpdateUserDetails2() {
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
        when(userRepository.save(Mockito.<User>any())).thenThrow(new AccountExpiredException("Msg"));
        when(userRepository.findByUserId(Mockito.<Integer>any())).thenReturn(user);

        // Act and Assert
        assertThrows(AccountExpiredException.class,
                () -> authenticationService.updateUserDetails(new RegisterRequest(), 1));
        verify(userRepository).findByUserId(Mockito.<Integer>any());
        verify(userRepository).save(Mockito.<User>any());
    }

    /**
     * Method under test:
     * {@link AuthenticationService#updateUserDetails(RegisterRequest, Integer)}
     */
    @Test
    void testUpdateUserDetails3() {
        // Arrange
        User user = mock(User.class);
        doNothing().when(user).setAddress(Mockito.<String>any());
        doNothing().when(user).setDateOfBirth(Mockito.<Date>any());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setFullName(Mockito.<String>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setPhoneNumber(Mockito.<String>any());
        doNothing().when(user).setUserId(Mockito.<Integer>any());
        doNothing().when(user).setVerified(anyBoolean());
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
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("6625550144");
        user2.setUserId(1);
        user2.setVerified(true);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findByUserId(Mockito.<Integer>any())).thenReturn(user);

        // Act
        User actualUpdateUserDetailsResult = authenticationService.updateUserDetails(new RegisterRequest(), 1);

        // Assert
        verify(user, atLeast(1)).setAddress(Mockito.<String>any());
        verify(user, atLeast(1)).setDateOfBirth(Mockito.<Date>any());
        verify(user).setEmail(eq("jane.doe@example.org"));
        verify(user, atLeast(1)).setFullName(Mockito.<String>any());
        verify(user).setPassword(eq("iloveyou"));
        verify(user, atLeast(1)).setPhoneNumber(Mockito.<String>any());
        verify(user).setUserId(Mockito.<Integer>any());
        verify(user).setVerified(eq(true));
        verify(userRepository).findByUserId(Mockito.<Integer>any());
        verify(userRepository).save(Mockito.<User>any());
        assertSame(user2, actualUpdateUserDetailsResult);
    }
}
