package edu.school.cinema.services;

import edu.school.cinema.models.Image;
import edu.school.cinema.models.User;
import edu.school.cinema.models.Visit;
import edu.school.cinema.repositories.ImageRepository;
import edu.school.cinema.repositories.UserRepository;
import edu.school.cinema.repositories.VisitRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service("userService")
public class UserServiceImpl implements UserService, ApplicationContextAware {

    private UserRepository userRepository;
    private ImageRepository imageRepository;
    private VisitRepository visitRepository;
    private Validator validator;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.validator = applicationContext.getBean(Validator.class);
        this.userRepository = applicationContext.getBean("userRepository", UserRepository.class);
        this.imageRepository = applicationContext.getBean("imageRepository", ImageRepository.class);
        this.visitRepository = applicationContext.getBean("visitRepository", VisitRepository.class);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public String findPasswordByEmail(String email) {
        return userRepository.findPasswordByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByEmailWithContent(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<Image> findImageByUserId(Long id) {
        return imageRepository.findByUserId(id);
    }

    @Override
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public List<Visit> findVisitsById(Long id) {
        return visitRepository.findByUserId(id);
    }

    @Override
    public Visit addVisit(Visit visit) {
        return visitRepository.save(visit);
    }

    public Set<ConstraintViolation<User>> isValid(User user) {
        return validator.validate(user);
    }
}
