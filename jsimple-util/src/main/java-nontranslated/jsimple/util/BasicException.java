/*
 * Copyright (c) 2012-2015, Microsoft Mobile
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package jsimple.util;

/**
 * @author Bret Johnson
 * @since 5/7/13 2:19 AM
 */
public class BasicException extends RuntimeException {
    public BasicException() {
    }

    public BasicException(Throwable cause) {
        super(cause);
    }

    public BasicException(String message) {
        super(message);
    }

    public BasicException(String message, Throwable t) {
        super(message, t);
    }

    public BasicException(String message, Object arg1) {
        this(MessageFormatter.format(message, arg1));
    }

    public BasicException(String message, Object arg1, Object arg2) {
        this(MessageFormatter.format(message, arg1, arg2));
    }

    public BasicException(String message, Object... args) {
        this(MessageFormatter.arrayFormat(message, args));
    }

    public BasicException(MessageFormatter.FormattingTuple formattingTuple) {
        super(formattingTuple.getFormattedMessage());

        Throwable throwable = formattingTuple.getThrowable();
        if (throwable != null)
            initCause(throwable);
    }
}
