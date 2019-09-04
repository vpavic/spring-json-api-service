/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.vpavic.demo.config;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class JsonApiDocumentHttpMessageConverter extends AbstractGenericHttpMessageConverter<JSONAPIDocument<?>> {

    private final ResourceConverter resourceConverter;

    JsonApiDocumentHttpMessageConverter(ResourceConverter resourceConverter) {
        super(MediaType.valueOf("application/vnd.api+json"));
        this.resourceConverter = resourceConverter;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return JSONAPIDocument.class.isAssignableFrom(clazz);
    }

    @Override
    public JSONAPIDocument read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException {
        return readResolved(GenericTypeResolver.resolveType(type, contextClass), inputMessage);
    }

    @Override
    protected JSONAPIDocument readInternal(Class<? extends JSONAPIDocument<?>> clazz, HttpInputMessage inputMessage)
            throws IOException {
        return readResolved(clazz, inputMessage);
    }

    @SuppressWarnings("unchecked")
    private JSONAPIDocument readResolved(Type resolvedType, HttpInputMessage inputMessage) throws IOException {
        return this.resourceConverter.readDocument(inputMessage.getBody().readAllBytes(),
                (Class) ((ParameterizedType) resolvedType).getActualTypeArguments()[0]);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void writeInternal(JSONAPIDocument jsonApiDocument, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        byte[] bytes;
        try {
            if (jsonApiDocument.get() instanceof Iterable<?>) {
                bytes = this.resourceConverter.writeDocumentCollection(jsonApiDocument);
            }
            else {
                bytes = this.resourceConverter.writeDocument(jsonApiDocument);
            }
        }
        catch (DocumentSerializationException e) {
            throw new HttpMessageNotWritableException(e.getMessage(), e);
        }
        outputMessage.getBody().write(bytes);
    }

}
