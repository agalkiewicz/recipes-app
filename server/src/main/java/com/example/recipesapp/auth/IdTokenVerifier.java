package com.example.recipesapp.auth;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.stereotype.Component;

@Component
public class IdTokenVerifier {

    private static final HttpTransport transport = new NetHttpTransport();
    private static final JsonFactory jsonFactory = new JacksonFactory();
    private final String CLIENT_ID = "680092708222-dv2se5gsv573lmas6saf6j4m7niee35b.apps.googleusercontent.com";

    public IdTokenVerifier() {
    }

    public Payload verifyToken(String idTokenString) throws GeneralSecurityException, IOException, InvalidTokenException {
        final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                .Builder(transport, jsonFactory)
                .setIssuers(Arrays.asList("https://accounts.google.com", "accounts.google.com"))
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(idTokenString);
        } catch (Exception e) {
            throw e;
        }

        if (idToken == null) {
            throw new InvalidTokenException("ID token is invalid");
        }

        return idToken.getPayload();
    }
}
