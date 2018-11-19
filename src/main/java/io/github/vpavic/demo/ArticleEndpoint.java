package io.github.vpavic.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collections;

@RestController
@RequestMapping(path = "/articles", produces = "application/vnd.api+json")
public class ArticleEndpoint {

    private final ResourceConverter resourceConverter;

    public ArticleEndpoint(ObjectMapper objectMapper) {
        this.resourceConverter = new ResourceConverter(objectMapper, Article.class, People.class);
    }

    @GetMapping
    public String getArticles() throws Exception {
        Instant now = Instant.now();
        People author = new People("42");
        Article article = new Article("1", "JSON:API paints my bikeshed!", "The shortest article. Ever.", now, now,
                author);
        return new String(this.resourceConverter.writeObjectCollection(Collections.singletonList(article)));
    }

    @Type("article")
    static class Article {

        @Id
        private String id;

        private String title;

        private String body;

        private Instant created;

        private Instant updated;

        @Relationship("author")
        private People author;

        Article(String id, String title, String body, Instant created, Instant updated, People author) {
            this.id = id;
            this.title = title;
            this.body = body;
            this.created = created;
            this.updated = updated;
            this.author = author;
        }

        public String getId() {
            return this.id;
        }

        public String getTitle() {
            return this.title;
        }

        public String getBody() {
            return this.body;
        }

        public Instant getCreated() {
            return this.created;
        }

        public Instant getUpdated() {
            return this.updated;
        }

        public People getAuthor() {
            return this.author;
        }

    }

    @Type("people")
    static class People {

        @Id
        private String id;

        People(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

    }

}
