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

package io.github.vpavic.demo.intefaces.article;

import java.net.URI;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.models.errors.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.vpavic.demo.intefaces.people.People;

@RestController
@RequestMapping(path = "/articles", produces = "application/vnd.api+json")
public class ArticleEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(ArticleEndpoint.class);

    private static final People samplePeople = new People("42");

    private static final Article sampleArticle = new Article("1", "JSON:API paints my bikeshed!",
            "The shortest article. Ever.", Instant.EPOCH, Instant.EPOCH, samplePeople);

    @GetMapping
    public ResponseEntity<?> getArticles() {
        List<Article> articles = Collections.singletonList(sampleArticle);
        return ResponseEntity.ok(new JSONAPIDocument<>(articles));
    }

    @PostMapping
    public ResponseEntity<?> postArticle(@RequestBody JSONAPIDocument<Article> article) {
        logger.info("Posted article: {}", article.get());
        return ResponseEntity.created(URI.create("https://example.com")).body(article);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getArticle(@PathVariable String id) {
        if (!"1".equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JSONAPIDocument<>(notFound(id)));
        }
        return ResponseEntity.ok(new JSONAPIDocument<>(sampleArticle));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<?> patchArticle(@RequestBody JSONAPIDocument<Article> article) {
        logger.info("Patched article: {}", article.get());
        return ResponseEntity.noContent().build();
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
