package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/students")
public class StudentListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String sql = "SELECT * FROM students ORDER BY id";

        try (PrintWriter out = response.getWriter();
             Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Student Records</title></head>");
            out.println("<body>");
            out.println("<h2>Student Records</h2>");
            out.println("<table border='1' cellpadding='10'>");
            out.println("<tr><th>ID</th><th>Roll No</th><th>Name</th>"
                    + "<th>Department</th><th>Age</th></tr>");

            while (resultSet.next()) {
                out.println("<tr>");
                out.println("<td>" + resultSet.getInt("id") + "</td>");
                out.println("<td>" + resultSet.getString("roll_no") + "</td>");
                out.println("<td>" + resultSet.getString("name") + "</td>");
                out.println("<td>" + resultSet.getString("department") + "</td>");
                out.println("<td>" + resultSet.getInt("age") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<br><a href='index.html'>Home</a>");
            out.println("<br><a href='add-student.html'>Add Student</a>");
            out.println("</body></html>");

        } catch (Exception e) {
            throw new ServletException("Unable to retrieve student records", e);
        }
    }
}