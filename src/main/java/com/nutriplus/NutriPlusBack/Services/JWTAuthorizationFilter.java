package com.nutriplus.NutriPlusBack.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager)
    {
        super(authenticationManager);
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
//            Jws<Claims> claims = Jwts.parser()
//                    .setSigningKey(SecurityConstants.SECRET.getBytes())
//                    .parseClaimsJws(token);
//
//            String user = claims.getBody().get("username").toString();

            try {
                DecodedJWT jwt = JWT.decode(token);
                Date expiresAt = jwt.getExpiresAt();
                if(expiresAt.getTime() < System.currentTimeMillis())
                {
                    return null;
                }
                Map<String, Claim> claims = jwt.getClaims();
                Claim claim = claims.get("username");
                String user = claim.asString();
                return user != null ?
                        new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>()) :
                        null;
            }
            catch (JWTDecodeException exception)
            {
                return null;
            }

        }

        return null;
    }
}