package edu.school.cinema.servlets;

import edu.school.cinema.models.Image;
import edu.school.cinema.models.User;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@WebServlet("/images")
@MultipartConfig(fileSizeThreshold = 1024 * 5,
        maxFileSize = 1024 * 1024 * 2,
        maxRequestSize = 1024 * 1024 * 2)
public class UploadImageServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        String imageDirectory = applicationContext.getEnvironment().getProperty("images.path");

        Image image = null;
        for (Part part : req.getParts()) {
            if (part.getName().equals("image")) {
                image = new Image();
                image.setFileName(part.getSubmittedFileName());
                image.setSize(part.getSize());
                image.setUserId(user.getId());
                image.setMime(part.getContentType());
                image.setPath(imageDirectory + "\\" + part.getSubmittedFileName());

                BufferedImage img = ImageIO.read(part.getInputStream());
                ImageIO.write(img, "jpeg", new File(image.getPath()));
                break;
            }
        }

        if (image == null) {
            putBody("NOT FOUND TAG 'image'", resp.getOutputStream());
            return;
        }

        userService.saveImage(image);
        req.getRequestDispatcher("/main/profile").forward(req, resp);
    }
}