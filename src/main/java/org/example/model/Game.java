package org.example.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class Game {
    private int id;
    private String name;
    private Date releaseDate;
    private int rating;
    private int cost;
    private String description;

    @Override
    public String toString() {
        return "№ =" + id +
                ", name = " + name +
                ", releaseDate = " + releaseDate +
                ", rating = " + rating +
                ", cost = " + cost +
                ", description = " + description;
    }
}
