package com.multiplex.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "movieId")
public class Movies {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movie_id")
	private int movieId;

	@Column(name = "movie_name", length = 255)
	private String movieName;

	@Column(name = "genre", length = 30)
	private String genre;
	
	@OneToMany(mappedBy = "movie", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Show> shows;

	public Movies() {
		super();
	}

	public Movies(int movieId, String movieName, String genre) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.genre = genre;
	}

	public Movies(int movieId, String movieName, String genre, List<Show> shows) {
		super();
		this.movieId = movieId;
		this.genre = genre;
		this.movieName = movieName;
		this.shows = shows;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public List<Show> getShows() {
		return shows;
	}

	public void setShows(List<Show> shows) {
		this.shows = shows;
	}

	@Override
	public String toString() {
		return "Movies [movieId=" + movieId + ", movieName=" + movieName + ", shows=" + shows + "]";
	}
}
