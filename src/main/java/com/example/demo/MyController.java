package com.example.demo;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
public class MyController {


    @GetMapping(path = "/users")
    public String getUserInfo(Model model) {

        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();

        final Principal principal = (Principal) authentication.getPrincipal();

        String dob = "";

        if (principal instanceof KeycloakPrincipal) {

            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            IDToken token = kPrincipal.getKeycloakSecurityContext()
                    .getIdToken();

            Map<String, Object> customClaims = token.getOtherClaims();

            if (customClaims.containsKey("DOB")) {
                dob = String.valueOf(customClaims.get("DOB"));
            }
        }

        model.addAttribute("username", principal.getName());
        model.addAttribute("dob", dob);
        return "userInfo";
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "external";
    }
}
