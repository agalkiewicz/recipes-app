package com.example.recipesapp.security;


//public class OpenIdConnectFilter extends AbstractAuthenticationProcessingFilter {
//    @Value("${google.clientId}")
//    private String clientId;
//
//    @Value("${google.issuer}")
//    private String issuer;
//
//    @Value("${google.jwkUrl}")
//    private String jwkUrl;
//
//    public OAuth2RestOperations restTemplate;
//
//    public OpenIdConnectFilter(String defaultFilterProcessesUrl) {
//        super(defaultFilterProcessesUrl);
//        setAuthenticationManager(new NoopAuthenticationManager());
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//
//        OAuth2AccessToken accessToken;
//        try {
//            accessToken = restTemplate.getAccessToken();
//        } catch (final OAuth2Exception e) {
//            throw new BadCredentialsException("Could not obtain access token", e);
//        }
//        try {
//            final String idToken = accessToken.getAdditionalInformation().get("id_token").toString();
//            String kid = JwtHelper.headers(idToken).get("kid");
//            final Jwt tokenDecoded = JwtHelper.decodeAndVerify(idToken, verifier(kid));
//            final Map<String, String> authInfo = new ObjectMapper().readValue(tokenDecoded.getClaims(), Map.class);
//            verifyClaims(authInfo);
//            final OpenIdConnectUserDetails user = new OpenIdConnectUserDetails(authInfo, accessToken);
//            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//        } catch (final Exception e) {
//            throw new BadCredentialsException("Could not obtain user details from token", e);
//        }
//
//    }
//
//    public void verifyClaims(Map claims) {
//        int exp = (int) claims.get("exp");
//        Date expireDate = new Date(exp * 1000L);
//        Date now = new Date();
//        if (expireDate.before(now) || !claims.get("iss").equals(issuer) || !claims.get("aud").equals(clientId)) {
//            throw new RuntimeException("Invalid claims");
//        }
//    }
//
//
//    private RsaVerifier verifier(String kid) throws Exception {
//        JwkProvider provider = new UrlJwkProvider(new URL(jwkUrl));
//        Jwk jwk = provider.get(kid);
//        return new RsaVerifier((RSAPublicKey) jwk.getPublicKey());
//    }
//
//    public void setRestTemplate(OAuth2RestTemplate restTemplate2) {
//        restTemplate = restTemplate2;
//
//    }
//
//    private static class NoopAuthenticationManager implements AuthenticationManager {
//
//        @Override
//        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//            throw new UnsupportedOperationException("No authentication should be done with this AuthenticationManager");
//        }
//
//    }
//}