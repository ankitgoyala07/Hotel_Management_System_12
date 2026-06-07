/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.admindashboarddao;
import model.admindashboardmodel;

public class admindashboardcontroller {

    private admindashboarddao dao;

    public admindashboardcontroller() {
        this.dao = new admindashboarddao();
    }

    public admindashboardmodel getDashboardData() {
        return dao.getDashboardData();
    }
}