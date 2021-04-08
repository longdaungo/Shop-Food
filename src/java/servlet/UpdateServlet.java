/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tblproducts.tblProductsDAO;
import tblupdateproduct.tblUpdateProductDAO;

/**
 *
 * @author IT
 */
@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
public class UpdateServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String ERROR = "error.html";
    private final String DISPATCHER = "DispatchServlet";
    private static final Logger LOGGER = Logger.getLogger(UpdateServlet.class);
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String name = request.getParameter("Name");
        String stringprice = request.getParameter("Price");
        int price = Integer.parseInt(stringprice);
        String stringquantity = request.getParameter("Quantity");
        int quantity = Integer.parseInt(stringquantity);
        String image = request.getParameter("image");
        String category = request.getParameter("category");
        String url = ERROR;
        try{
            Date date = new Date();
            SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
            String stringdate = pattern.format(date);
            HttpSession session = request.getSession();
            String userid = (String) session.getAttribute("USERID");
            tblProductsDAO dao = new tblProductsDAO();
            dao.updateProduct(id, name, price, quantity, image, category);
            tblUpdateProductDAO daoupdateproduct = new tblUpdateProductDAO();
            int rowsupdateproduct = daoupdateproduct.countRows();
            String idupdateproduct = String.valueOf(++rowsupdateproduct);
            daoupdateproduct.insertUpdatedProduct(idupdateproduct, id, userid, stringdate, "Update");
            url = DISPATCHER+"?btnAction=searchByAdmin";
        }catch(SQLException ex){
            LOGGER.error(ex.getMessage());
            url = ERROR;
        }catch(NamingException ex){
            LOGGER.error(ex.getMessage());
            url = ERROR;
        }
        finally{
            response.sendRedirect(url);
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
