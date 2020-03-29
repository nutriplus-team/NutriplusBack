package com.nutriplus.NutriPlusBack.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import com.nutriplus.NutriPlusBack.Repositories.ApplicationUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final ApplicationUserRepository applicationUserRepository;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, ApplicationUserRepository applicationUserRepository)
    {
        super(authenticationManager);
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    protected void  doFilterInternal(HttpServletRequest req,
                                     HttpServletResponse res,
                                     FilterChain chain) throws IOException, ServletException{
        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX))
        {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
    {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if (token != null)
        {
            token = token.replace("Port ", "");

            try{
                Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.SECRET);
                JWTVerifier verifier = JWT.require(algorithm)
                        .build();
                DecodedJWT jwt = verifier.verify(token);
            }
            catch (JWTVerificationException exception)
            {
                return null;
            }

            try {
                DecodedJWT jwt = JWT.decode(token);
                Date expiresAt = jwt.getExpiresAt();
                if(expiresAt.getTime() < System.currentTimeMillis())
                {
                    return null;
                }
                Map<String, Claim> claims = jwt.getClaims();

                Claim refreshClaim = claims.get("refresh");
                if(refreshClaim.asBoolean())
                {
                    return null;
                }

                Claim claim = claims.get("username");
                String user = claim.asString();

                if(user == null)
                {
                    return null;
                }

                Claim userIdClaim = claims.get("id");
                Long id = Long.valueOf(userIdClaim.asInt());
                Optional<UserCredentials> userCredentials = applicationUserRepository.findById(id);

                return userCredentials.map(credentials -> new UsernamePasswordAuthenticationToken(user, credentials, new ArrayList<>())).orElse(null);

            }
            catch (JWTDecodeException exception)
            {
                return null;
            }
            catch (java.lang.NullPointerException e)
            {
                return null;
            }

        }

        return null;
    }
}