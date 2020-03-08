package com.jj.vreden.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn
    private BoardCategory boardCategory;

    @OneToMany(mappedBy = "board",  cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BoardList> boardLists;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Label> labels;

}
