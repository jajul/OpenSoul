package com.gym.servlets;


import com.gym.db.MyDataSourceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Julia on 13.10.2016.
 */
@WebServlet("/s")
public class StartServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(StartServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        Properties props = new Properties();
        try (InputStream fis = (InputStream) StartServlet.class.getClassLoader().getResourceAsStream("app.properties")) {
            props.load(fis);
            request.setAttribute("application_name", props.getProperty("application_name"));
            request.setAttribute("account_name", props.getProperty("account_name"));
            request.setAttribute("password", props.getProperty("password"));
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);

    }
}
