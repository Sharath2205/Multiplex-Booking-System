package com.multiplex.dto;

public class PublishMovieDto {
	private String movieName;
	private String genre;

	public PublishMovieDto() {
		super();
	}

	public PublishMovieDto(String movieName, String genre) {
		super();
		this.movieName = movieName;
		this.genre = genre;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

}
