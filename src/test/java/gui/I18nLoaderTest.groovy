package gui

import gui.i18n.CodesI18n
import gui.i18n.I18n
import org.apache.log4j.Logger
import spock.lang.Specification

import java.lang.reflect.Field
import java.lang.reflect.Modifier

/**
 * Created by michaello on 14.01.18.
 */
class I18nLoaderTest extends Specification {
    private Logger logger = Mock(Logger.class);
    private I18n i18n;

    def setup() {
        Field loggerField = I18n.class.getDeclaredField("logger")
        loggerField.setAccessible(true)

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(loggerField, loggerField.getModifiers() & ~Modifier.FINAL);

        loggerField.set(null, logger)
        i18n = I18n.getInstance();
        i18n.setLocale(new Locale("pl"))

    }

    def "Check whether all translations for locale = pl are correctly loaded"() {
        given:

        i18n.setLocale(new Locale("pl"))
        Field[] codes = CodesI18n.getFields()

        when:
        for (Field field : codes) {
            i18n.getString(field.get(String))
        }
        then:
        0 * logger.debug(_ as String)
    }

    def "Check whether incorect codes are logged"() {
        when:
        i18n.getString("CODE_WHICH_DOES_NOT_WORK_!@#%^&*()")
        then:
        1 * logger.debug(_)
    }
}
