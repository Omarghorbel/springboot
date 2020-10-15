package com.omarghorbel.storecrud.sec;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omarghorbel.storecrud.entities.AppUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthentificationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    // jwtauthentification en fait contenu deux methode principale la 1ere c attemptAuthentication dans la quelle on va
    // recuperier les informations sur l'utilisateur authentifié
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // dans cette mthode on a les parametre objet request et objet response
        //alors pour lire les donnée de la requete si l utilisateur envoyé username et passworde sous fome par exemple  username = valeur et password = valeur
        //pour ce on va utilisier request.getparameter//
        // String password=request.getParameter("password");
        //on utilise cette methode si les donné sont envoyé au format wwwurlencoded
        //mais les donné sont envoye au format json donc on ne vaps utilisé ce qu on dit on va utiliser
        // je vais utiliser la librairie jaxon qui fait partie de spring a vec objetmapper
        //request.getInputStream(), AppUser.class on fait c  du text json mais il devient objet java c la  desirliation  du json vers objet java
        try {
            AppUser appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
            System.out.println("2 "+appUser);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUsername(),appUser.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // bref qu en l utilisateur va saisir son username son password on recuperé les donnée et on va retourner a spring  securité un objet de type
        //Authentication qui contient les information username et mot de passe

    }

    // successfulAuthentication  cad que luilisateur maintenet,(  sprinq quesil  faire il va verifier le username et mot de passe sont correct
    // il va vers la bd  couch e sercie dao il va recupere l utilisateur il va verifie et si toot va bien il appel ceete methode successfulAuthentication
    // successfulAuthentication cette methode pour generer le jwttoken
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user=(User)authResult.getPrincipal();
        List<String> roles = new ArrayList<>();
        authResult.getAuthorities().forEach(a->{
            roles.add(a.getAuthority());
        });

        String jwt = JWT.create()
                .withIssuer(request.getRequestURI())
                .withSubject(user.getUsername())
                .withArrayClaim("roles",roles.toArray(new String[roles.size()]))
                .withExpiresAt(new Date(System.currentTimeMillis()+SecurityParams.EXPIRATION))
                .sign(Algorithm.HMAC256(SecurityParams.SECRET));
        response.addHeader(SecurityParams.JWT_HEADER_NAME,jwt);
    }




}
