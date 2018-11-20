/*
 * Copyright 2018 the original author or authors.
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

package io.github.vpavic.demo.intefaces.article;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.models.errors.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/articles", produces = "application/vnd.api+json")
public class ArticleEndpoint {

    private static final Article demoArticle;

    static {
        Instant now = Instant.now();
        People author = new People("42");
        demoArticle = new Article("1", "JSON:API paints my bikeshed!", "The shortest article. Ever.", now, now, author);
    }

    @GetMapping
    public ResponseEntity<?> getArticles() {
        List<Article> articles = Collections.singletonList(demoArticle);
        return ResponseEntity.ok(new JSONAPIDocument<>(articles));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getArticle(@PathVariable String id) {
        if (!"1".equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JSONAPIDocument<>(notFound(id)));
        }
        return ResponseEntity.ok(new JSONAPIDocument<>(demoArticle));
    }

    private static Error notFound(String id) {
        Error error = new Error();
        error.setId(id);
        error.setStatus("404");
        error.setCode("Not Found");
        error.setDetail("Article " + id + " not found");
        return error;
    }

}
