package com.marcosilv7.narutodelivery.configuration.api;

public class Api {
    public static final String TOKEN_TEST="Beaber eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYXJjb3NpbHY3ND" +
            "g2QGdtYWlsLmNvbSIsInNjb3BlcyI6WyJST0xFX0NSRUFURV9PUkRFUlMiLCJST0xFX1JFQURfT1JE" +
            "RVJTIiwiUk9MRV9VUERBVEVfT1JERVJTIiwiUk9MRV9ERUxFVEVfT1JERVJTIl0sInVzZXJJZCI6MS" +
            "widXNlcl9uYW1lIjoibWFyY29zaWx2NzQ4NkBnbWFpbC5jb20iLCJ1c2VyX2Z1bGxuYW1lIjoiTWF" +
            "yY28gU2lsdmVyaW8gQ2FzdHJvIiwidXNlcl9hdmF0YXIiOiIiLCJqdGkiOiI4NDczZjQ1Mi1mODQ0" +
            "LTRiNmEtOTlmOC0zNzg1YjJhZmJiZjgiLCJpc3MiOiJydW1ib2FsbXVuZGlhbDIwMTgiLCJpYXQiO" +
            "jE1MjMxMTk2NDIsImV4cCI6MTUyNTI3OTY0Mn0.x1zlVbvRm_d9MJszyMUSxt6kflZydXLAIIL-7V6" +
            "rNCwqVw54cLALKvayLrLmPRBBgOFHxNe6gWmyGd5PKT1TGA";

    public static final String ROOT_PATH="/api/v1";
    public static final String ROOT_PATH_V2="/api/v2";
    public static final String LOGIN_PATH=ROOT_PATH+"/security/authenticacion";
    public static final String REFRESH_TOKEN_PATH=ROOT_PATH+"/security/refreshtoken";
    public static final String USER_PATH=ROOT_PATH+"/users";
    public static final String USER_REGISTRATION_PATH=ROOT_PATH+USER_PATH+"/";
    public static final String SWAGGER_PATH="/swagger-resources/configuration/ui/**";
    public static final String SWAGGER_RESOURCES_PATH="/configuration/ui/**";

    public static final String PROFILE_PATH=ROOT_PATH+"/profile";


}
