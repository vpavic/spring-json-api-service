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

import java.time.Instant;

import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;

import io.github.vpavic.demo.intefaces.people.People;

@Type("article")
public class Article {

    @Id
    private String id;

    private String title;

    private String body;

    private Instant created;

    private Instant updated;

    @Relationship("author")
    private People author;

    public Article(String id, String title, String body, Instant created, Instant updated, People author) {
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

    @Override
    public String toString() {
        return "Article{" + "id='" + this.id + '\'' + ", title='" + this.title + '\'' + ", body='" + this.body + '\''
                + ", created=" + this.created + ", updated=" + this.updated + ", author=" + this.author + '}';
    }
}
