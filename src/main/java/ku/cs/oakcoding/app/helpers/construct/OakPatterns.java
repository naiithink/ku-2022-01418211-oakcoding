/**
 * @file OakPatterns.java
 * @version 1.0.0-dev
 * 
 * @note Current raw-regex pattern to auto-insert an escape token '\' := /((?<=[^\W\[])(?<!\\)\^|(?<!\\)\\(?=\w)|(?<!\\)\$(?=\w))/g
 *                                                                       "((?<=[^\\W\\[])(?<!\\\\)\\^|(?<!\\\\)\\\\(?=\\w)|(?<!\\\\)\\$(?=\\w))"
 * 
 *                                                                    With unbalanced curly braces inside of a parenthesis pair: "(ab{cs)" -> "(ab\{cd)"
 *                                                                         /((?<=[^\W\[])(?<!\\)\^|(?<!\\)\\(?=\w)|(?<=[\(\[])(?:\w*)[\{\}](?:\w*)(?=[\)\]])|(?<!\\)\$(?=\w))/g
 *                                                                         "((?<=[^\\W\\[])(?<!\\\\)\\^|(?<!\\\\)\\\\(?=\\w)|(?<=[\\(\\[])(?:\\w*)[\\{\\}](?:\\w*)(?=[\\)\\]])|(?<!\\\\)\\$(?=\\w))"
 */

package ku.cs.oakcoding.app.helpers.construct;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ku.cs.oakcoding.app.helpers.configurations.OakAppConfigs;
import ku.cs.oakcoding.app.helpers.configurations.OakSpecialAppToken;

public abstract class OakPatterns {

    private static final Character[] REGEX_SPECIAL_CHARS = {
        '^',    '$',    '{',    '}',
        '?',    '*',    '+',    '|',
        '(',    ')',    '<',    '>',
        '=',    '!',    ':'
    };

    private static final String LINT_PATTERN;

    private static volatile ConcurrentHashMap<Character, Character> regexSpecialCharacters;

    private static class OakPatternPool {

        private static OakPatternPool instance;

        private static Pattern patternSpecialParameterExpansion;

        private static Pattern patternSpecialComment;

        private static Pattern patternAppConstruct;

        private OakPatternPool() {}

        public static OakPatternPool getOakPatternPool() {
            if (instance == null) {
                synchronized (OakPatternPool.class) {
                    if (instance == null) {
                        instance = new OakPatternPool();
                    }
                }
            }

            return instance;
        }

        public static Pattern getPatternSpecialComment() {
            if (patternSpecialComment == null) {
                synchronized (OakPatterns.class) {
                    if (patternSpecialComment == null) {
                        patternSpecialComment = Pattern.compile(
                            rawRegexSpecialCharLint(String.format("\\S*(?=%c)", OakSpecialAppToken.AsChar.COMMENT.value()))
                        );
                    }
                }
            }

            return patternSpecialComment;
        }

        public static Pattern getPatternSpecialParameterExpansion() {
            if (patternSpecialParameterExpansion == null) {
                synchronized (OakPatterns.class) {
                    if (patternSpecialParameterExpansion == null) {
                        patternSpecialParameterExpansion = Pattern.compile(
                            rawRegexSpecialCharLint(String.format("(?<=%c%c).*(?=%c)", OakSpecialAppToken.AsChar.PARAM_EXPANSION.value(),
                                                                                       OakSpecialAppToken.AsChar.PARAM_EXPANSION_START.value(),
                                                                                       OakSpecialAppToken.AsChar.PARAM_EXPANSION_END.value()))
                        );
                    }
                }
            }

            return patternSpecialParameterExpansion;
        }
    }

    static {
        regexSpecialCharacters = new ConcurrentHashMap<Character, Character>();
        LINT_PATTERN = "((?<=[^\\W\\[])(?<!\\\\)\\^|(?<!\\\\)\\\\(?=\\w)|(?<=[\\(\\[])(?:\\w*)[\\{\\}](?:\\w*)(?=[\\)\\]])|(?<!\\\\)\\$(?=\\w))";

        for (Character ch : REGEX_SPECIAL_CHARS) {
            regexSpecialCharacters.put(ch, ch);
        }
    }

    /**
     * @warning Experimental internal feature
     */
    public static synchronized String rawRegexSpecialCharLint(final String string) {

        if (OakAppConfigs.useExperimentalFeature()) {
            final String UNBALANCED_BRACES_PATTERN = "((?<!\\{)\\}|\\{(?!\\}))";
            Pattern p = Pattern.compile(LINT_PATTERN);
            Matcher m = p.matcher(string);

            if (m.find() == false) {
                return new String(string);
            }

            Pattern regexUnbalancedGroupersPattern = Pattern.compile(UNBALANCED_BRACES_PATTERN);
            Matcher regexUnbalancedGroupersMatcher = regexUnbalancedGroupersPattern.matcher(string);
            StringBuffer localResultBuffer = new StringBuffer();
            StringBuffer localResultBuffer2 = new StringBuffer();
            String localString1 = new String();

            localResultBuffer.append(string);

            for (int i = 1, groups = m.groupCount() + 1, a = 0, b = 0; i < groups; ++i) {
                b = m.start(i) - 1;

                if (m.start(i) != a) {
                    localResultBuffer2.append(string.substring(a, b));
                }

                if (regexUnbalancedGroupersMatcher.find()) {
                    localString1 = regexUnbalancedGroupersMatcher.group(i);
                    localString1 = localString1.replaceAll(UNBALANCED_BRACES_PATTERN, "\\\\$1");
                    localResultBuffer2.append(localString1);
                }

                a = b;
            }

            return localResultBuffer.toString();
        } else {
            return new String(string);
        }
    }

    public static Pattern getPatternSpecialComment() {
        return OakPatternPool.getPatternSpecialComment();
    }
}
