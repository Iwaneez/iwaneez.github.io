package com.iwaneez.stuffer.util;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Localization {

    private static final String COOKIE_LOCALE = "locale";

    public static final List<Locale> supportedLocales = Arrays.asList(Locale.US, new Locale("sk", "SK"));

    public static Locale getLocale() {
        Locale locale = getBrowserLocale();
        if (locale == null) {
            locale = getDefaultLocale();
        }
        return locale;
    }

//    private static Locale getCookieLocale() {
//        if (VaadinService.getCurrentRequest().getCookies() == null) {
//            return null;
//        }
//        for (Cookie cookie : VaadinService.getCurrentRequest().getCookies()) {
//            if (cookie.getName().equals(COOKIE_LOCALE)) {
//                String languageTag = cookie.getValue();
//                for (Locale locale : supportedLocales) {
//                    if (locale.toLanguageTag().equals(languageTag)) {
//                        return locale;
//                    }
//                }
//            }
//        }
//        return null;
//    }

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

    public static String get(String code) {
        MessageSource messageSource = ApplicationContextUtils.getApplicationContext().getBean(MessageSource.class);
        return messageSource.getMessage(code, null, UI.getCurrent().getLocale());
    }

    public static String get(String code, Object... args) {
        MessageSource messageSource = ApplicationContextUtils.getApplicationContext().getBean(MessageSource.class);
        return messageSource.getMessage(code, args, UI.getCurrent().getLocale());
    }

}