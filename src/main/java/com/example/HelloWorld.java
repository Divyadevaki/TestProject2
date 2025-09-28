package com.example;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Scanner;

@WebServlet("/")

public class HelloWorld extends HttpServlet {
    public static void main(String[] args) {
        // hardcoded credentials for demo
        String correctUsername = "admin";
        String correctPassword = "password123";

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (username.equals(correctUsername) && password.equals(correctPassword)) {
            System.out.println("✅ Login successful! Welcome " + username);
        } else {
            System.out.println("❌ Invalid username or password!");
        }

        scanner.close();
    }
}
