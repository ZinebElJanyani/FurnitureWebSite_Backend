package ma.org.comfybackend.security.Mappers;

import ma.org.comfybackend.security.DTO.ItemDTO;
import ma.org.comfybackend.security.DTO.ReviewDTO;
import ma.org.comfybackend.security.Entities.Item;
import ma.org.comfybackend.security.Entities.Review;
import org.springframework.beans.BeanUtils;

public class ReviewMapper {
    public Review fromReviewDTO(ReviewDTO reviewDTO){
       Review review = new Review();

        BeanUtils.copyProperties(reviewDTO,review);
        return review;
    }

    public ReviewDTO fromReview(Review review){
        ReviewDTO reviewDTO = new ReviewDTO();

        BeanUtils.copyProperties(review,reviewDTO);
        return reviewDTO;
    }
}
