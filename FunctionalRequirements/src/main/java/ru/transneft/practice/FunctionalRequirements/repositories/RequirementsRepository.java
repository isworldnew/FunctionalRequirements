package ru.transneft.practice.FunctionalRequirements.repositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.transneft.practice.FunctionalRequirements.dto.ReasonStatisticsDto;
import ru.transneft.practice.FunctionalRequirements.entities.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public interface RequirementsRepository extends MongoRepository<Requirement, String> {

    default List<ReasonStatisticsDto> getReasonStatistics() {
        String connectionString = "mongodb://worker:password@localhost:27017/FunctionalRequirementsStorage?authSource=FunctionalRequirementsStorage";
        ConnectionString connString = new ConnectionString(connectionString);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "FunctionalRequirementsStorage");

        MongoDatabase database = mongoTemplate.getDb();
        MongoCollection<Document> collection = database.getCollection("Requirements");

        List<ReasonStatisticsDto> aggregationResults = new ArrayList<>();

        collection.aggregate(Arrays.asList(
                new Document("$group", new Document("_id", "$reason")
                        .append("amount", new Document("$sum", 1))),
                new Document("$project", new Document("reason", "$_id")
                        .append("amount", 1)
                        .append("_id", 0))
        )).forEach(doc -> {
            ReasonStatisticsDto res = new ReasonStatisticsDto();
            res.setReason(doc.getString("reason"));
            res.setAmount(doc.getInteger("amount"));
            aggregationResults.add(res);
        });

        mongoClient.close();

        return aggregationResults;
    }
}
