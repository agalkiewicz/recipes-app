package com.example.recipesapp.security;

//@EnableOAuth2Sso
//@Configuration
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http
////                .csrf()
////                .disable()
////                .antMatcher("/**")
////                .authorizeRequests()
////                .antMatchers("/", "/index.html")
////                .permitAll()
////                .anyRequest()
////                .authenticated();
////    }
//
//
//        @Autowired
//        private OAuth2RestTemplate restTemplate;
//
//        @Bean
//        public OpenIdConnectFilter openIdConnectFilter() {
//            OpenIdConnectFilter filter = new OpenIdConnectFilter("/google-login");
//            filter.setRestTemplate(restTemplate);
//            return filter;
//        }
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .addFilterAfter(new OAuth2ClientContextFilter(),
//                            AbstractPreAuthenticatedProcessingFilter.class)
//                    .addFilterAfter(OpenIdConnectFilter(),
//                            OAuth2ClientContextFilter.class)
//                    .httpBasic()
//                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/google-login"))
//                    .and()
//                    .authorizeRequests()
//                    .anyRequest().authenticated();
//        }
//}

//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private OAuth2RestTemplate restTemplate;
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/resources/**");
//    }
//
//    @Bean
//    public OpenIdConnectFilter myFilter() {
//        final OpenIdConnectFilter filter = new OpenIdConnectFilter("/google-login");
//        filter.setRestTemplate(restTemplate);
//        return filter;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
//        http
//                .cors()
//                .and()
//                .addFilterAfter(new OAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
//                .addFilterAfter(myFilter(), OAuth2ClientContextFilter.class)
//                .httpBasic().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/google-login"))
//                .and()
//                .authorizeRequests()
//                // .antMatchers("/","/index*").permitAll()
//                .anyRequest().authenticated();
//
//        // @formatter:on
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//    @Bean
//    @Order(0)
//    public RequestContextListener requestContextListener() {
//        return new RequestContextListener();
//    }
//}
