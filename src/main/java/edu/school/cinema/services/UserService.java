package edu.school.cinema.services;

import edu.school.cinema.models.Image;
import edu.school.cinema.models.User;
import edu.school.cinema.models.Visit;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public interface UserService {
    String findPasswordByEmail(String email);
    User saveUser(User user);
    User findByEmail(String email);
    User findByEmailWithContent(String email);

    Image saveImage(Image image);
    List<Image> findImageByUserId(Long id);
    List<Visit> findVisitsById(Long id);
    Set<ConstraintViolation<User>> isValid(User user);
}
