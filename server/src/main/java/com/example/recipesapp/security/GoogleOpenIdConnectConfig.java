package com.example.recipesapp.security;


//@Configuration
//@EnableOAuth2Client
//public class GoogleOpenIdConnectConfig {
//    @Value("${google.clientId}")
//    private String clientId;
//
//    @Value("${google.clientSecret}")
//    private String clientSecret;
//
//    @Value("${google.accessTokenUri}")
//    private String accessTokenUri;
//
//    @Value("${google.userAuthorizationUri}")
//    private String userAuthorizationUri;
//
//    @Value("${google.redirectUri}")
//    private String redirectUri;
//
//    @Bean
//    public OAuth2ProtectedResourceDetails googleOpenId() {
//        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
//        details.setClientId(clientId);
//        details.setClientSecret(clientSecret);
//        details.setAccessTokenUri(accessTokenUri);
//        details.setUserAuthorizationUri(userAuthorizationUri);
//        details.setScope(Arrays.asList("openid", "email"));
//        details.setPreEstablishedRedirectUri(redirectUri);
//        details.setUseCurrentUri(false);
//        return details;
//    }
//
//    @Bean
//    public OAuth2RestTemplate googleOpenIdTemplate(OAuth2ClientContext clientContext) {
//        return new OAuth2RestTemplate(googleOpenId(), clientContext);
//    }
//}
