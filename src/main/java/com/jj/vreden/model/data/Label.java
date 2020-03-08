package com.jj.vreden.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    private String hexColor;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Board board;

    @ManyToMany(mappedBy = "labels")
    @JsonIgnore
    private List<Card> cards;

}
