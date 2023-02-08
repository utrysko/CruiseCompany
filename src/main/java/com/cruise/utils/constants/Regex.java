package com.cruise.utils.constants;

/**
 * Class represents regexes needed to validate fields
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class Regex {
    public static final String EMAIL_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-z]{2,6}$";
    public static final String NAME_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,30}";
    public static final String PORT_REGEX = "^[A-ZА-ЩЬЮЯҐІЇЄ]{1}[a-zа-щьюяґіїє]{1,20}[- ]?[A-ZА-ЩЬЮЯҐІЇЄ]?[a-zа-щьюяґіїє]*";
}
