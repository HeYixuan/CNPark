package org.springframe.web.system;

import org.springframe.util.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SystemUserController {

    @PostMapping("/user/me")
    public Principal user(Principal principal) {
        return principal;
    }

    @PostMapping("/user/test")
    public ResponseEntity user() {
        int i = 1/0;
        //int i = 1;
        return new ResponseEntity(i);
    }
}
