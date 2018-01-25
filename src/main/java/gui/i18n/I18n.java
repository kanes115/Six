package gui.i18n;


import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by michaello on 14.01.18.
 */
public class I18n {
    private static final List<Locale> supportedLocales = new LinkedList<>();
    private static final Logger logger = Logger.getLogger(I18n.class);
    private static final Locale defaultLocale = new Locale("pl");
    private static Locale selectedLocale;
    private static ResourceBundle resourceBundle;
    private static I18n instance = null;

    public static I18n getInstance (){
       return  Optional.ofNullable(instance)
                .orElse(instance = new I18n());
    }

    private I18n() {
        supportedLocales.add(defaultLocale);
    }

    public List<Locale> getSupportedLocales() {
        return supportedLocales;
    }

    public void setLocale(Locale locale) {
        if (!supportedLocales.contains(locale)) {
            logger.warn(String.format("Chosen not supported locale: %s, loaded default local %s:", locale.toString(), defaultLocale.toString()));
            resourceBundle = ResourceBundle.getBundle(I18n.class.getName(), defaultLocale, new UTF8Control());
            selectedLocale = defaultLocale;
        } else {
            resourceBundle = ResourceBundle.getBundle(I18n.class.getName(), locale, new UTF8Control());
            selectedLocale = locale;
        }

    }

    public String getString(String code) {
        String value;
        try {
            value = resourceBundle.getString(code);
        } catch (MissingResourceException e) {
            logger.debug(String.format("Cannot load from file %s with locale: %s transalated value for code: %s returned value to user: %s", resourceBundle.getBaseBundleName(), selectedLocale.getLanguage() , code, code));
            return code;
        }
        return value;
    }
}
