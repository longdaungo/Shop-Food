/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import cart.CartObject;
import cart.ItemOrder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author IT
 */
@WebServlet(name = "AddCartServlet", urlPatterns = {"/AddCartServlet"})
public class AddCartServlet extends HttpServlet {
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String stringquantity = request.getParameter("quantity");
        String pageIndex = request.getParameter("pageIndex");
        String searchValue = request.getParameter("searchValue");
        String moneyFrom = request.getParameter("moneyFrom");
        String moneyTo = request.getParameter("moneyTo");
        String category = request.getParameter("category");
        String pageIndexBestSeller = request.getParameter("pageIndexBestSeller");
        String pageIndexRecomendation = request.getParameter("pageIndexRecomendation");
        
        int quantity = Integer.parseInt(stringquantity);
        String stringprice = request.getParameter("price");
        int price = Integer.parseInt(stringprice);
        int total = price*quantity;
        String url = null;
        if(pageIndexBestSeller == null & pageIndexRecomendation == null){
            url = DISPATCH+"?btnAction=search&pageIndex="+pageIndex+"&searchValue="+searchValue+"&moneyFrom="+moneyFrom+"&moneyTo="+moneyTo+"&category="+category;
        }
        else if(pageIndexBestSeller != null & pageIndexRecomendation == null){
            url = DISPATCH+"?btnAction=search&pageIndexBestSeller="+pageIndexBestSeller;
        }
        else{
            url = DISPATCH+"?btnAction=search&pageIndexRecomendation="+pageIndexRecomendation;
        }
        try{
            ItemOrder newitem = new ItemOrder(id, name, quantity, price, total);
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession();
            CartObject cart= (CartObject)session.getAttribute("CART");
            if(cart == null){
                cart = new CartObject();
            }
            cart.addYourCart(newitem);
            session.setAttribute("CART", cart);
        }finally{
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
