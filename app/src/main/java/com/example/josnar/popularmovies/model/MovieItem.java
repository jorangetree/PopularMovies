package com.example.josnar.popularmovies.model;


import java.io.Serializable;
import java.util.List;

public class MovieItem implements Serializable {
    public int id;
    public String posterPath;
    public String originalTitle;
    public String releaseDate;
    public Double voteAverage;
    public String plotSynopsis;
}
