package br.com.magalu.sbootapiwishlist.adapter.out.persistence;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;

import static org.bson.UuidRepresentation.STANDARD;

@Configuration
@EnableMongoRepositories(basePackages = "br.com.magalu.sbootapiwishlist.adapter.out.persistence.repository")
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    public MongoClient mongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoUri))
                .uuidRepresentation(STANDARD)
                .build();
        return MongoClients.create(settings);
    }

    @Bean
    public SimpleMongoClientDatabaseFactory mongoDbFactory(MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, new ConnectionString(mongoUri).getDatabase());
    }

    @Bean
    public MongoTemplate mongoTemplate(SimpleMongoClientDatabaseFactory mongoDbFactory,
                                       MongoCustomConversions conversions,
                                       MongoMappingContext context) {
        return new MongoTemplate(mongoDbFactory);
    }

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Collections.emptyList());
    }
}

