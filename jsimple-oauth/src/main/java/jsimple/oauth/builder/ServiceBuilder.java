/*
 * Copyright (c) 2012-2014, Microsoft Mobile
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
 *
 *
 * This code was adapted from the Scribe Java OAuth library,
 * https://github.com/fernandezpablo85/scribe-java, with modest changes made
 * for JUniversal.  Scribe itself is distributed under the MIT license.
 */

package jsimple.oauth.builder;

import jsimple.oauth.builder.api.OAuthApi;
import jsimple.oauth.model.OAuthConfig;
import jsimple.oauth.model.OAuthConstants;
import jsimple.oauth.model.OAuthLogger;
import jsimple.oauth.model.SignatureType;
import jsimple.oauth.oauth.OAuthService;
import org.jetbrains.annotations.Nullable;


/**
 * Implementation of the Builder pattern, with a fluent interface that creates a {@link OAuthService}
 *
 * @author Pablo Fernandez
 */
public class ServiceBuilder {
    private @Nullable String apiKey;
    private @Nullable String apiSecret;
    private String callback;
    private @Nullable OAuthApi api;
    private @Nullable String scope;
    private SignatureType signatureType;
    private @Nullable OAuthLogger debugLogger;

    /**
     * Default constructor
     */
    public ServiceBuilder() {
        this.callback = OAuthConstants.OUT_OF_BAND;
        this.signatureType = SignatureType.Header;
        this.debugLogger = null;
    }

    /**
     * Configures the {@link jsimple.oauth.builder.api.OAuthApi}
     * <p/>
     * Overloaded version. Let's you use an instance instead of a class.
     *
     * @param api instance of {@link jsimple.oauth.builder.api.OAuthApi}s
     * @return the {@link ServiceBuilder} instance for method chaining
     */
    public ServiceBuilder provider(OAuthApi api) {
        assert api != null : "Api cannot be null";
        this.api = api;
        return this;
    }

    /**
     * Adds an OAuth callback url
     *
     * @param callback callback url. Must be a valid url or 'oob' for out of band OAuth
     * @return the {@link ServiceBuilder} instance for method chaining
     */
    public ServiceBuilder callback(String callback) {
        assert callback != null : "Callback can't be null";
        this.callback = callback;
        return this;
    }

    /**
     * Configures the api key
     *
     * @param apiKey The api key for your application
     * @return the {@link ServiceBuilder} instance for method chaining
     */
    public ServiceBuilder apiKey(String apiKey) {
        assert !apiKey.isEmpty() : "Invalid Api key";
        this.apiKey = apiKey;
        return this;
    }

    /**
     * Configures the api secret
     *
     * @param apiSecret The api secret for your application
     * @return the {@link ServiceBuilder} instance for method chaining
     */
    public ServiceBuilder apiSecret(String apiSecret) {
        assert !apiSecret.isEmpty() : "Invalid Api secret";
        this.apiSecret = apiSecret;
        return this;
    }

    /**
     * Configures the OAuth scope. This is only necessary in some APIs (like Google's).
     *
     * @param scope The OAuth scope
     * @return the {@link ServiceBuilder} instance for method chaining
     */
    public ServiceBuilder scope(String scope) {
        assert !scope.isEmpty() : "Invalid OAuth scope";
        this.scope = scope;
        return this;
    }

    /**
     * Configures the signature type, choose between header, querystring, etc. Defaults to Header
     *
     * @param type signature type
     * @return the {@link ServiceBuilder} instance for method chaining
     */
    public ServiceBuilder signatureType(SignatureType type) {
        this.signatureType = type;
        return this;
    }

    public ServiceBuilder debugLogger(OAuthLogger logger) {
        assert logger != null : "debug stream can't be null";
        this.debugLogger = logger;
        return this;
    }

    /*
    public ServiceBuilder debug() {
        this.debugLogger(System.out);
        return this;
    }
    */

    /**
     * Returns the fully configured {@link OAuthService}
     *
     * @return fully configured {@link OAuthService}
     */
    public OAuthService build() {
        assert api != null : "nullness";          // You must specify a valid api through the provider() method"
        assert apiKey != null : "nullness";       // You must provide an api key
        assert apiSecret != null : "nullness";    // You must provide an api secret
        return api.createService(new OAuthConfig(apiKey, apiSecret, callback, signatureType, scope, debugLogger));
    }
}
