package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/add-student")
public class AddStudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String rollNo = request.getParameter("roll_no");
        String name = request.getParameter("name");
        String department = request.getParameter("department");
        String ageValue = request.getParameter("age");

        try {
            int age = Integer.parseInt(ageValue);

            String sql = "INSERT INTO students "
                    + "(roll_no, name, department, age) "
                    + "VALUES (?, ?, ?, ?)";

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(1, rollNo);
                statement.setString(2, name);
                statement.setString(3, department);
                statement.setInt(4, age);

                statement.executeUpdate();
            }

            response.sendRedirect(
                    request.getContextPath() + "/students");

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Age must be a valid number");
        } catch (java.sql.SQLException e) {
            throw new ServletException("Unable to insert student", e);
        }
    }
}