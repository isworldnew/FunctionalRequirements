package ru.transneft.practice.FunctionalRequirements.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data @AllArgsConstructor @NoArgsConstructor
@Document(collection = "Requirements")
public class Requirement {

    @Id
    private String id;

    //private Integer frNumber; //номер ФТ

    private LocalDate creationDate;

    private String ops; //Oil Pumping Station - НПС (нефтеперекачивающая станция)

    private String rnu; //РНУ

    private String ca; //?

    private String reason;

    private LocalDate realizationDate;

    private String author;

}
