package com.iwaneez.stuffer.core.util;

import com.iwaneez.stuffer.core.service.SecurityService;
import com.iwaneez.stuffer.core.ui.component.Localizable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.UI;
import org.springframework.context.MessageSource;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Localization {

    private static final String COOKIE_LOCALE = "locale";

    public static final List<Locale> supportedLocales = Arrays.asList(Locale.US, new Locale("sk", "SK"));

    public static Locale getLocale() {
        Locale locale = getUserLocale();
        if (locale == null) {
            locale = getBrowserLocale();
        }
        if (locale == null) {
            locale = getDefaultLocale();
        }
        return locale;
    }

    private static Locale getUserLocale() {
        try {
            SecurityService securityService = ApplicationContextUtils.getApplicationContext().getBean(SecurityService.class);
            return Locale.forLanguageTag(securityService.getCurrentUser().getLanguage());
        } catch (Exception e) {
            return null;
        }
    }

    private static Locale getCookieLocale() {
        if (VaadinService.getCurrentRequest().getCookies() == null) {
            return null;
        }
        for (Cookie cookie : VaadinService.getCurrentRequest().getCookies()) {
            if (cookie.getName().equals(COOKIE_LOCALE)) {
                String languageTag = cookie.getValue();
                for (Locale locale : supportedLocales) {
                    if (locale.toLanguageTag().equals(languageTag)) {
                        return locale;
                    }
                }
            }
        }

        return null;
    }

    private static Locale getBrowserLocale() {
        Locale browserLocale = VaadinService.getCurrentRequest().getLocale();
        if (browserLocale == null) {
            return null;
        }
        for (Locale item : supportedLocales) {
            if (item.equals(browserLocale)) {
                return browserLocale;
            }
        }
        return null;
    }

    private static Locale getDefaultLocale() {
        Locale defaultLocale = supportedLocales.get(0);

        return defaultLocale;
    }

    public static void setLocaleCookie(Locale locale) {
        Cookie cookie = new Cookie(COOKIE_LOCALE, locale.toLanguageTag());
        cookie.setMaxAge(600);
        cookie.setPath("/");

        // Save cookie
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    public static void localize(Component component) {
        if (component instanceof Localizable) {
            ((Localizable) component).localize();
        }
        if (component instanceof HasComponents) {
            ((HasComponents) component).iterator().forEachRemaining(Localization::localize);
        }
    }

    public static String get(String code) {
        MessageSource messageSource = ApplicationContextUtils.getApplicationContext().getBean(MessageSource.class);
        return messageSource.getMessage(code, null, UI.getCurrent().getLocale());
    }

    public static String get(String code, Object... args) {
        MessageSource messageSource = ApplicationContextUtils.getApplicationContext().getBean(MessageSource.class);
        return messageSource.getMessage(code, args, UI.getCurrent().getLocale());
    }

}