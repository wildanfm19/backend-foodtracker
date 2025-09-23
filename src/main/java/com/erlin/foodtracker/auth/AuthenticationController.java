package com.erlin.foodtracker.auth;

import com.erlin.foodtracker.Dto.ResponseDTO;
import com.erlin.foodtracker.Dto.UserDTO;
import com.erlin.foodtracker.constant.AuthenticationConstant;
import com.erlin.foodtracker.entity.User;
import com.erlin.foodtracker.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final AuthUtils authUtils;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(
            @RequestBody RegisterRequest request
    ){
        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                        .statusCode(AuthenticationConstant.HTTP_OK_200)
                        .statusMessage(AuthenticationConstant.USER_REGISTERED_SUCCESS)
                        .data(response)
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                        .statusCode(AuthenticationConstant.HTTP_OK_200)
                        .statusMessage(AuthenticationConstant.USER_LOGGED_IN_SUCCESS)
                        .data(response)
                .build());
    }

    @GetMapping("/current-user")
    public ResponseEntity<ResponseDTO> getCurrentLoggedInUser(){
        User user = authUtils.loggedInUser();
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setName(user.getFirstName() + " " + user.getLastName());
        userDTO.setEmail(user.getEmail());
        return ResponseEntity.ok(ResponseDTO.builder()
                        .statusCode(AuthenticationConstant.HTTP_OK_200)
                        .statusMessage(AuthenticationConstant.USER_LOGGED_IN_SUCCESS)
                        .data(userDTO)
                .build());


    }
}
