package com.FreeBirds.FreeBirds.services;

import com.FreeBirds.FreeBirds.dtos.ReviewDTO;
import com.FreeBirds.FreeBirds.dtos.Response;

public interface ReviewService {
    Response createReview(ReviewDTO reviewDTO);
    Response getReviewById(Long id);
    Response getAllReviews();
    Response updateReview(Long id, ReviewDTO reviewDTO);
    Response deleteReview(Long id);
}
