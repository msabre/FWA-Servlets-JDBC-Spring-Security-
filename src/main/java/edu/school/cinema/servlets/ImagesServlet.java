package edu.school.cinema.servlets;

import edu.school.cinema.models.Image;
import edu.school.cinema.models.User;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@WebServlet("/main/images")
@MultipartConfig(fileSizeThreshold = 1024 * 5,
        maxFileSize = 1024 * 1024 * 2,
        maxRequestSize = 1024 * 1024 * 2)
public class ImagesServlet extends BaseServlet {

    private String imageDirectory;

    @Override
    public void init(ServletConfig servletConfig) {
        super.init(servletConfig);
        imageDirectory = applicationContext.getEnvironment().getProperty("images.path");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("image/jpeg");
        String name = req.getParameter("name");
        String typePhoto = req.getParameter("type");

        String path = imageDirectory + "\\" + name;
        if ("empty".equals(name)) {
            try {
                path = Paths.get(ImagesServlet.class.getResource("/avatar-male.jpg").toURI()).toFile().getPath();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        File image = new File(path);
        if (!image.exists()) {
            resp.getOutputStream().write(new byte[32]);
            return;
        }

        byte[] bytes;
        BufferedImage img = ImageIO.read(new FileImageInputStream(image));
        if ("avatar".equals(typePhoto))
            img = cutPhoto(img);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            String format = path.substring(path.lastIndexOf(".") + 1);
            ImageIO.write(img, format, baos);
            bytes = baos.toByteArray();
        }
        resp.getOutputStream().write(bytes);
    }

    private BufferedImage cutPhoto(BufferedImage img) {
        int onePart = getOnePart(img.getHeight(), img.getWidth());
        double width = onePart * 2;
        double height = onePart * 3;
        int x = (int) ((img.getWidth() - width) / 2);
        int y = (int) ((img.getHeight() - height) / 2);
        return img.getSubimage(x, y, (int) width, (int) height);
    }

    private int getOnePart(int height, int width) {
        if (height < 3)
            return -1;
        if (width < 2)
            return -1;

        int onePart;
        double x = ((double) 2 / 3) * height;

        if (x * 2 <= width)
            onePart = (int) x / 2;
        else
            onePart = width / 2;

        while (onePart * 3 > height)
            onePart--;
        while (onePart * 2 > width)
            onePart--;

        return onePart;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");

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
        resp.sendRedirect("/main/profile");
    }
}