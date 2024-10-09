package br.com.cineroom.api.controllers;

import br.com.cineroom.api.dtos.review.ReviewDTO;
import br.com.cineroom.api.dtos.review.ReviewReturnDTO;
import br.com.cineroom.api.entities.Review;
import br.com.cineroom.api.entities.Session;
import br.com.cineroom.api.entities.User;
import br.com.cineroom.api.repositories.ReviewRepository;
import br.com.cineroom.api.repositories.SessionRepository;
import br.com.cineroom.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    // create new review
    @PostMapping
    @Transactional
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO, UriComponentsBuilder uriBuilder) {
        User user = userRepository.getReferenceById(reviewDTO.userId());
        Session session = sessionRepository.getReferenceById(reviewDTO.sessionId());
        Review review = new Review(reviewDTO, user, session);
        reviewRepository.save(review);

        var uri = uriBuilder.path("/reviews/{id}").buildAndExpand(review.getId()).toUri();

        return ResponseEntity.created(uri).body(new ReviewReturnDTO(review));
    }

    // get a review by its ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long id) {
        var review = reviewRepository.getReferenceById(id);

        return ResponseEntity.ok(new ReviewReturnDTO(review));
    }

    // get all reviews by its user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getReviewsByUserId(@PathVariable Long userId) {
        var reviews = reviewRepository.findAllByUserId(userId).stream()
                .map(ReviewReturnDTO::new)
                .toList();
        return ResponseEntity.ok(reviews);
    }

    // update a review by its ID
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        var review = reviewRepository.getReferenceById(id);
        review.updateReview(reviewDTO);
        reviewRepository.save(review);

        return ResponseEntity.ok(new ReviewReturnDTO(review));
    }

    // delete a review by its ID
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        var review = reviewRepository.getReferenceById(id);
        reviewRepository.delete(review);

        return ResponseEntity.noContent().build();
    }

}
