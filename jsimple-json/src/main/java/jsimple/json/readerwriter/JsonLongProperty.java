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

package jsimple.json.readerwriter;

/**
 * @author Bret Johnson
 * @since 5/22/13 9:08 PM
 */
public class JsonLongProperty extends JsonProperty {
    public JsonLongProperty(String name, int id) {
        super(name, id);
    }

    // TODO: Automatically convert int to long
    public long readValue(JsonObjectReader objectReader) {
        Object value = objectReader.readPropertyValue();
        if (value instanceof Integer)
            return (long) (int) (Integer) value;
        else return (long) (Long) value;
    }

    @Override public Object readValueUntyped(JsonObjectReader objectReader) {
        return objectReader.readPropertyValue();
    }

    public void write(JsonObjectWriter objectWriter, long value) {
        objectWriter.writeProperty(this, value);
    }
}
