package org.springframe.web.system;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SystemUserController {

    @PostMapping("/user/me")
    public Principal user(Principal principal) {
        return principal;
    }
}
