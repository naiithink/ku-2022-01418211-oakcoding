package ku.cs.oakcoding.app.helpers.configurations;

public abstract class OakSpecialAppToken {

    public enum AsChar {

        DIR_NAME_DELIMITER      ('-'),
        FIELD_DELIMITER         (','),
        PARAM_EXPANSION         ('฿'),
        PARAM_EXPANSION_START   ('{'),
        PARAM_EXPANSION_END     ('}'),
        COMMENT                 ('#');

        private final char value;

        private AsChar(char value) {
            this.value = value;
        }

        public char value() {
            return value;
        }
    }

    public enum AsString {

        NIL                     ("NIL");

        private final String value;

        private AsString(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }
}
