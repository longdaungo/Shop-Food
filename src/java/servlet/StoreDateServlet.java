/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import cart.CartObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tbloders.tblOrdersDAO;
import tblorderdetail.tblOrderDetailDAO;
import tblproducts.ProductObject;
import tbluser.tblUsersDAO;

/**
 *
 * @author IT
 */
@WebServlet(name = "StoreDateServlet", urlPatterns = {"/StoreDateServlet"})
public class StoreDateServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(StoreDateServlet.class);
    private final String DISPATCH = "DispatchServlet";
    private final String ERROR = "error.html";
    private final String ADDRESS = "address.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String stringtotal = request.getParameter("total");
        String stringpaypal = request.getParameter("paypal");
        boolean paypal = false;
        if(stringpaypal != null){
            paypal =true;
        }
        float floattotal = Float.parseFloat(stringtotal);
        int total = (int) floattotal;
        String url = ERROR;
        try {
            /* TODO output your page here. You may use following sample code. */
            //get quantity cua product ma may muon kiem tra
            HttpSession session = request.getSession();
            String userid = (String) session.getAttribute("USERID");
            tblUsersDAO userdao = new tblUsersDAO();
            String address = userdao.getAddress(userid);
            if (address == null) {
                url = ADDRESS;
            } else {
                CartObject cart = (CartObject) session.getAttribute("CART");
                ProductObject productmap = new ProductObject();
                productmap.getQuantityByids(cart);
                productmap.compareQuantity(cart);
                productmap.updateQuantity(cart);
                productmap.updateQuantityPurchased(cart);

                tblOrdersDAO dao = new tblOrdersDAO();
                int count = dao.coutRowOders();
                String idOrder = String.valueOf(++count);

                Date date = new Date();
                SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String dateBuy = pattern.format(date);
                dao.insertOrder(idOrder, userid, total, dateBuy, paypal);
                tblOrderDetailDAO detailDAO = new tblOrderDetailDAO();
                count = detailDAO.countOrderDetail();
                //getiddetail
                String iddetail = String.valueOf(count);
                //get product id
                cart.insertProduct(iddetail, idOrder);
                
                
                String NOTIFICATION = "Your food is order successfully";
                request.setAttribute("NOTIFICATION", NOTIFICATION);
                session.removeAttribute("CART");
                url = DISPATCH + "?btnAction=search";
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            url = ERROR;
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            url = ERROR;
        } catch (Exception ex) {
            url = DISPATCH + "?btnAction=viewCart";
            request.setAttribute("Exception", ex.getMessage());
            log(ex.getMessage());
        } finally {
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(StoreDateServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(StoreDateServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
