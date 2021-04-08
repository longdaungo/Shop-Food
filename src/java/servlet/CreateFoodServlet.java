/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import tblproducts.tblProductsDAO;

/**
 *
 * @author IT
 */
@WebServlet(name = "CreateFoodServlet", urlPatterns = {"/CreateFoodServlet"})
public class CreateFoodServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CreateFoodServlet.class);
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String DISPATCH = "DispatchServlet";
    private final String VIEWCREATEFOOD = "createfood.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("Name");
        String stringprice = request.getParameter("Price");
        int price = Integer.parseInt(stringprice);
        String stringquantity = request.getParameter("Quantity");
        int quantity = Integer.parseInt(stringquantity);
        String image = request.getParameter("image");
        String Category = request.getParameter("category");
        String url = DISPATCH+"?btnAction=searchByAdmin";
        try {
            /* TODO output your page here. You may use following sample code. */
           tblProductsDAO dao = new tblProductsDAO();
           int count = dao.countAllProduct();
           String id = String.valueOf(++count);
           Date date = new Date();
           SimpleDateFormat pattern= new SimpleDateFormat("dd/MM/yyyy");
           String dateCurrent = pattern.format(date);
           dao.insertFood(id, name, price, quantity, true, image, dateCurrent, Category,0);
           String notification = "insert successfully";
           request.setAttribute("NOTIFICATION", notification);
        }
        catch(NamingException ex){
            LOGGER.error(ex.getMessage());
            url = VIEWCREATEFOOD;
        }
        catch(SQLException ex){
            LOGGER.error(ex.getMessage());
            url = VIEWCREATEFOOD;
        }
        finally{
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
