package com.jj.vreden.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class BoardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    private float orderInBoard;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Board board;

    @OneToMany(mappedBy = "boardList", cascade = CascadeType.ALL)
    @OrderBy("orderInBoardList")
    private List<Card> cards;

}
