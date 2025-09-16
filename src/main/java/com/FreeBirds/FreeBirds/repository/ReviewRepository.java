package com.FreeBirds.FreeBirds.repository;

import com.FreeBirds.FreeBirds.entities.Contract;
import com.FreeBirds.FreeBirds.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewee(User reviewee);
    List<Review> findByReviewer(User reviewer);
    List<Review> findByContract(Contract contract);
}

