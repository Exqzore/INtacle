package com.exqzore.intacle.controller;

public class WebPageRequest {
    public final static String REDIRECT = "redirect:";
    public final static String GO_LOGIN_PAGE = "main?command=to_login_page";
    public final static String GO_REGISTRATION_PAGE = "main?command=to_registration_page";
    public final static String USER_ACTIVATION = "main?command=activate_user&login=%s&activation_code=%s";
    public final static String SHOW_PROFILE = "main?command=show_profile&login=%s";
}
