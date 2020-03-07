package com.jj.vreden.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jj.vreden.model.data.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class BoardCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "boardCategory", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Board> boards;

}
