/*
 * Portions copyright (c) 2012-2014 Microsoft Mobile.  All Rights Reserved.
 *
 * This file is based on or incorporates material from the Scribe Java OAuth
 * library https://github.com/fernandezpablo85/scribe-java (collectively, “Third
 * Party Code”). Microsoft Mobile is not the original author of the Third Party
 * Code.
 *
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

package jsimple.oauth.extractors;

import jsimple.oauth.exceptions.OAuthParametersMissingException;
import jsimple.oauth.model.OAuthRequest;
import jsimple.oauth.model.ParameterList;
import jsimple.oauth.utils.OAuthEncoder;

/**
 * Default implementation of {@link BaseStringExtractor}. Conforms to OAuth 1.0a
 *
 * @author Pablo Fernandez
 */
public class BaseStringExtractorImpl implements BaseStringExtractor {
    /**
     * {@inheritDoc}
     */
    public String extract(OAuthRequest request) {
        checkPreconditions(request);
        String verb = OAuthEncoder.encode(request.getVerb());
        String url = OAuthEncoder.encode(request.getSanitizedUrl());
        String params = getSortedAndEncodedParams(request);
        return verb + "&" + url + "&" + params;
    }

    private String getSortedAndEncodedParams(OAuthRequest request) {
        ParameterList params = new ParameterList();
        params.addAll(request.getQueryStringParams());
        params.addAll(request.getBodyParams());
        params.addAll(new ParameterList(request.getOauthParameters()));
        return params.sort().asOauthBaseString();
    }

    private void checkPreconditions(OAuthRequest request) {
        if (request.getOauthParameters() == null || request.getOauthParameters().size() <= 0)
            throw new OAuthParametersMissingException(request);
    }
}
