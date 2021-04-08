/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
@WebServlet(name = "SearchBeforeLoginServlet", urlPatterns = {"/SearchBeforeLoginServlet"})
public class SearchBeforeLoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final Logger LOGGER = Logger.getLogger(SearchBeforeLoginServlet.class);
    private final String SEARCH = "searchbeforelogin.jsp";
    private final String ERROR = "error.html";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String searchValue = request.getParameter("searchValue");
        String stringpageIndex = request.getParameter("pageIndex");
        String stringmoneyFrom = request.getParameter("moneyFrom");
        String stringmoneyTo = request.getParameter("moneyTo");
        String category = request.getParameter("category");
        int moneyFrom = 0;
        int moneyTo = 0;
        if(stringmoneyFrom != null & stringmoneyTo != null){
            if(!stringmoneyFrom.equals("") & !stringmoneyTo.equals("")) {
                moneyFrom = Integer.parseInt(stringmoneyFrom);
                moneyTo = Integer.parseInt(stringmoneyTo);
        }
        }
        int pageIndex = 0;
        if (stringpageIndex == null) {
            stringpageIndex = "1";
            pageIndex = Integer.parseInt(stringpageIndex);
        } else {
            pageIndex = Integer.parseInt(stringpageIndex);
        }
        if(searchValue == null){
            searchValue = "";
        }
        if(category == null){
            category = "";
        }
        String url = ERROR;
        try {
            List listAllProducts = null;
            int count = 0;
            tblProductsDAO dao = new tblProductsDAO();
            if(stringmoneyFrom == null & stringmoneyTo == null){
                listAllProducts = dao.getProducts(pageIndex,searchValue,category);
                count = dao.countProducts(searchValue,category);
            }
            else if(stringmoneyFrom.equals("") & stringmoneyTo.equals("")){
                listAllProducts = dao.getProducts(pageIndex,searchValue,category);
                count = dao.countProducts(searchValue,category);
            }
            else{
                listAllProducts = dao.getProductsByPriceAndName(pageIndex, searchValue, moneyFrom, moneyTo,category);
//                request.setAttribute("moneyFrom", moneyFrom);
//                request.setAttribute("moneyTo",moneyTo);
                count = dao.countProductsByPrice(searchValue, category, moneyFrom, moneyTo);
            }
            int numberPage = countPage(count);
            url = SEARCH;
            List<String> listCategory = dao.getAllCategory();
            ServletContext context= request.getServletContext();
            request.setAttribute("LISTPRODUCTS", listAllProducts);
            request.setAttribute("NUMBERPAGE", numberPage);
            request.setAttribute("PAGEINDEX", pageIndex);
            request.setAttribute("LISTCATEGORY", listCategory);
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            url = ERROR;
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            url = ERROR;
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    public int countPage(int numberProducts) {
        int numberPages = 0;
        int lastPage = 0;
        int numberPagesNearLastPage = 0;
        if (numberProducts % 3 != 0) {
            lastPage = 1;
        }
        numberPagesNearLastPage = numberProducts / 3;
        numberPages = numberPagesNearLastPage + lastPage;
        return numberPages;
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
