package kpi.lab_3.controller.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class StringParser {

  private static final String HTML_FORM_BOOLEAN_TRUE = "on";

  public static Long toLong(String stringToParse) {
    try {
      return Long.parseLong(stringToParse);
    } catch (NumberFormatException e) {
      log.warn("{} cannot be parsed to Long.", stringToParse);
      return null;
    }
  }

  public static boolean toBoolean(String stringToParse) {
    return stringToParse != null
        && (stringToParse.equals(HTML_FORM_BOOLEAN_TRUE) || Boolean.parseBoolean(stringToParse));
  }

}
