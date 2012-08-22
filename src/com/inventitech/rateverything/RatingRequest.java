package com.inventitech.rateverything;


public class RatingRequest {
	public enum RATING {
		RATING_10, RATING_9, RATING_8, RATING_7, RATING_6, RATING_5, RATING_XD;
	}

	public RATING rating = RATING.RATING_XD;

	public RatingRequest(RATING rating) {
		this.rating = rating;
	}
}
