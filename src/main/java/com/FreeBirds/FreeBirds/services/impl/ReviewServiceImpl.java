package com.FreeBirds.FreeBirds.services.impl;

import com.FreeBirds.FreeBirds.dtos.ReviewDTO;
import com.FreeBirds.FreeBirds.dtos.Response;
import com.FreeBirds.FreeBirds.entities.Review;

import com.FreeBirds.FreeBirds.repository.ReviewRepository;
import com.FreeBirds.FreeBirds.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper mapper;

    @Override
    public Response createReview(ReviewDTO reviewDTO) {
        Review review = mapper.map(reviewDTO, Review.class);
        Review saved = reviewRepository.save(review);

        return Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Review created successfully")
                .review(mapper.map(saved, ReviewDTO.class))
                .build();
    }

    @Override
    public Response getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Review retrieved successfully")
                .review(mapper.map(review, ReviewDTO.class))
                .build();
    }

    @Override
    public Response getAllReviews() {
        List<ReviewDTO> reviews = reviewRepository.findAll()
                .stream()
                .map(r -> mapper.map(r, ReviewDTO.class))
                .collect(Collectors.toList());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Reviews retrieved successfully")
                .reviews(reviews)
                .build();
    }

    @Override
    public Response updateReview(Long id, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        mapper.map(reviewDTO, review);
        Review updated = reviewRepository.save(review);

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Review updated successfully")
                .review(mapper.map(updated, ReviewDTO.class))
                .build();
    }

    @Override
    public Response deleteReview(Long id) {
        reviewRepository.deleteById(id);

        return Response.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Review deleted successfully")
                .build();
    }
}
