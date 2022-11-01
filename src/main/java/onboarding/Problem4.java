package onboarding;

public class Problem4 {

    private static final int UPPER_CASE_STANDARD = 155;
    private static final int LOWER_CASE_STANDARD = 219;

    public static String solution(String word) {
        char[] wordToChars = word.toCharArray();
        char[] reverseChars = new char[wordToChars.length];

        for (int i = 0; i < wordToChars.length; i++) {
            char aChar = wordToChars[i];

            if (Character.isAlphabetic(aChar)) {

                if (Character.isUpperCase(aChar)) {
                    reverseChars[i] = (char) (UPPER_CASE_STANDARD - aChar);
                    continue;
                }

                if (Character.isLowerCase(aChar)) {
                    reverseChars[i] = (char) (LOWER_CASE_STANDARD - aChar);
                    continue;
                }

            }

            reverseChars[i] = aChar;
        }

        return String.valueOf(reverseChars);
    }
}
