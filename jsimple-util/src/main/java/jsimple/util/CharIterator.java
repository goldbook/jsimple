package jsimple.util;

/**
 * @author Bret Johnson
 * @since 11/25/12 4:00 PM
 */
public class CharIterator {
    private String str;
    private int index = 0;
    private int length;

    public CharIterator(String string) {
        this.str = string;
        this.length = string.length();
    }

    public String getString() {
        return str;
    }

    public String getRemaining() {
        return str.substring(index);
    }

    public int getIndex() {
        return index;
    }

    public boolean atEnd() {
        return index >= length;
    }

    /**
     * @return current character or '\0' if at end of str
     */
    public char curr() {
        if (index >= length)
            return '\0';
        else return str.charAt(index);
    }

    /**
     * Return the current character (same as curr()) then advance to the next one.
     *
     * @return current character or -1 if at end of str
     */
    public char currAndAdvance() {
        char c = curr();
        advance();
        return c;
    }

    /**
     * Advance the iterator to the next character.  If already at the end of the str, this method is a no-op.
     */
    public void advance() {
        if (index < length)
            ++index;
    }

    /**
     * Search for the the next occurrence of the specified substring & advance the iterator just past it.  If that
     * substring isn't found, an exception is thrown.
     *
     * @param substr substring to search for
     */
    public void skipAheadPast(String substr) {
        if (!skipAheadPastIfExists(substr))
            throw new InvalidFormatException("'" + substr + "' not found in string '" + str + "'");
    }

    /**
     * Search for the the next occurrence of the specified substring & advance the iterator just past it.  Return true
     * if the substring is found, false if it isn't.
     *
     * @param substr substring to search for
     * @return true if the substring is found
     */
    public boolean skipAheadPastIfExists(String substr) {
        int substringIndex = str.indexOf(substr, index);
        if (substringIndex == -1)
            return false;

        index = substringIndex + substr.length();
        return true;
    }

    /**
     * Verify that the current character is c, throwing an exception if it isn't, then advance to the next character.
     *
     * @param c character to verify that occurs at the current iterator position
     */
    public void checkAndAdvance(char c) {
        if (atEnd())
            throw new InvalidFormatException("Already at end of string");

        if (str.charAt(index) != c)
            throw new InvalidFormatException("Character '" + c + "' expected but not found at position " + index);

        ++index;
    }

    public boolean isLineBreak() {
        int c = curr();
        return c == '\r' || c == '\n';
    }

    /**
     * @return true if the current character is a whitespace character.
     */
    public boolean isWhitespace() {
        int c = curr();
        return c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }

    public boolean isDigit() {
        int c = curr();
        return c >= '0' && c <= '9';
    }

    public boolean isWhitespaceOnSameLine() {
        int c = curr();
        return c == ' ' || c == '\t';
    }

    /**
     * Advance past any whitespace characters in the str, leaving the iterator at the first non-whitespace character
     * following.
     */
    public void advancePastWhitespace() {
        while (isWhitespace())
            advance();
    }

    public void advancePastWhitespaceOnSameLine() {
        while (isWhitespaceOnSameLine())
            advance();
    }

    public String readWhitespaceDelimitedToken() {
        StringBuilder token = new StringBuilder();
        while (!isWhitespace() && !atEnd())
            token.append(currAndAdvance());
        return token.toString();
    }

    public String readWhitespaceDelimitedTokenOnSameLine() {
        StringBuilder token = new StringBuilder();
        while (!isWhitespaceOnSameLine() && !isLineBreak() && !atEnd())
            token.append(currAndAdvance());
        return token.toString();
    }
}
