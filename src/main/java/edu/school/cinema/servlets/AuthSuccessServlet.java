package edu.school.cinema.servlets;

import edu.school.cinema.models.User;
import edu.school.cinema.models.Visit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

@WebServlet("/authSuccess")
public class AuthSuccessServlet extends BaseServlet {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            String email = (String) req.getSession().getAttribute("username");
            user = userService.findByEmail(email);
            req.getSession().setAttribute("user", user);
        }

        Date now = new Date();
        String date = dateFormat.format(now);
        String time = timeFormat.format(now);
        String ip = getClientIpAddress(req);

        Visit visit = new Visit();
        visit.setUserId(user.getId());
        visit.setDate(date);
        visit.setTime(time);
        visit.setIp(ip);

        userService.addVisit(visit);
        req.getRequestDispatcher("/main/profile").forward(req, resp);
    }

    public static String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            String ip = request.getRemoteAddr();

            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                String curr = getCurrentIP();

                if (curr == null) {
                    InetAddress inetAddress;
                    try {
                        inetAddress = InetAddress.getLocalHost();
                        ip = inetAddress.getHostAddress();

                    } catch (UnknownHostException ignore) { }
                }
            }
            return ip;

        } else {
            return new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
        }
    }

    private static String getCurrentIP() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL("http://checkip.amazonaws.com").openStream()))) {
            return in.readLine();
        } catch (Exception e) {
            return null;
        }
    }
}
