package com.multiplex.dto;

import java.util.List;

public class MoviesDto {
	private int movieId;
	private String movieName;
	private List<UserShowsDto> shows;

	public MoviesDto() {
		super();
	}

	public MoviesDto(int movieId, String movieName, List<UserShowsDto> shows) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.shows = shows;
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

	public List<UserShowsDto> getShows() {
		return shows;
	}

	public void setShows(List<UserShowsDto> shows) {
		this.shows = shows;
	}

	@Override
	public String toString() {
		return "MoviesDto [movieId=" + movieId + ", movieName=" + movieName + ", shows=" + shows + "]";
	}

}