//package com.gym.servlets;
//
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * Created by Julia on 13.10.2016.
// */
//@WebServlet("/s")
//public class MyServlet extends HttpServlet {
//    private static final Logger logger = LogManager.getLogger(MyServlet.class);
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        super.doPost(request, response);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        logger.info("do get");
//        logger.error("test error message");
//        response.setContentType("text/html");
//
//        String varTextA = "Hello World!";
//        request.setAttribute("textA", varTextA);
//        String varTextB = "It JSP.";
//        request.setAttribute("textB", varTextB);
//
////        MyDataSourceFactory dataSourceFactory = new MyDataSourceFactory();
////        dataSourceFactory.getMySQLDataSource();
//
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
//        dispatcher.forward(request, response);
//
//    }
//}
