/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.AdminDashboardDAO;
import model.AdminDashboardModel;

public class AdminDashboardController {

    private AdminDashboardDAO dao;

    public AdminDashboardController() {
        this.dao = new AdminDashboardDAO();
    }

    public AdminDashboardModel getDashboardData() {
        return dao.getDashboardData();
    }
}