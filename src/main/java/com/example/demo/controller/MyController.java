package com.example.demo.controller;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
public class MyController {


    @GetMapping(path = "/users")
    public ModelAndView getUserInfo(Model model) {
    	ModelAndView view = new ModelAndView();
     /*   KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext()
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
        model.addAttribute("dob", dob); */
    	view.setViewName("home/homeSignedIn");
        return view;
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "external";
    }
}
