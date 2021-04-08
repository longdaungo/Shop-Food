/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author IT
 */
public class DispatchServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String LOGIN = "LoginServlet";
    private final String LOGOUT = "LogoutServlet";
    private final String ERROR = "error.html";
    private final String SEARCH = "SearchServlet";
    private final String SEARCHBYADMIN ="SearchByAdminServlet";
    private final String VIEWCREATEFOOD = "createfood.jsp";
    private final String CREATENEWFOOD = "CreateFoodServlet";
    private final String DELETE = "DeleteServlet";
    private final String VIEWUPDATE = "update.jsp";
    private final String UPDATE = "UpdateServlet";
    private final String VIEWCART = "cart.jsp";
    private final String ADDYOURCART = "AddCartServlet";
    private final String REMOVECART = "RemoveCartServlet";
    private final String STOREDATEBASE= "StoreDateServlet";
    private final String VIEWUPDATEAMOUNT = "updateamount.jsp";
    private final String UPDATEDAMOUNT = "UpdateAmountServlet";
    private final String HISTORY = "GetHistoryServlet";
    private final String VIEWLOGIN = "login.jsp";
    private final String SEARCHBEFORELOGIN = "SearchBeforeLoginServlet";
    private final String AUTHORIZEPAYMENT = "authorize_payment";
    private final String ADDADDRESS = "AddAdressServlet";
    private final String INSERTSERVLET= "InsertServlet";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String btnAction = request.getParameter("btnAction");
        String url = null;
        try {
            /* TODO output your page here. You may use following sample code. */
            if("viewlogin".equals(btnAction)){
                url = VIEWLOGIN;
            }
            else if("searchbeforelogin".equals(btnAction)){
                url = SEARCHBEFORELOGIN;
            }
            else if("Login".equals(btnAction)){
                url = LOGIN;
            }
            else if("logout".equals(btnAction)){
                url = LOGOUT;
            }
            else if("search".equals(btnAction)){
                url = SEARCH;
            }
            else if("searchByAdmin".equals(btnAction)){
                url = SEARCHBYADMIN;
            }
            else if("viewcreatefood".equals(btnAction)){
                url = VIEWCREATEFOOD;
            }
            else if("create new food".equals(btnAction)){
                url = CREATENEWFOOD;
            }
            else if("delete".equals(btnAction)){
                url = DELETE;
            }
            else if("Update".equals(btnAction)){
                url = VIEWUPDATE;
            }
            else if("Updated".equals(btnAction)){
                url = UPDATE;
            }
            
            else if("buy".equals(btnAction)){
                url = ADDYOURCART;
            }
            
            else if("viewCart".equals(btnAction)){
                url = VIEWCART;
            }
            
            else if("removeCart".equals(btnAction)){
                url = REMOVECART;
            }
            
            else if("traditional payment".equals(btnAction)){
                url = STOREDATEBASE;
            }
            
            else if("updateamount".equals(btnAction)){
                url = VIEWUPDATEAMOUNT;
            }
            
            else if("updatedamount".equals(btnAction)){
                url = UPDATEDAMOUNT;
            }
            
            else if("getHistory".equals(btnAction)){
                url = HISTORY;
            }
            
            else if("paypal".equals(btnAction)){
                url = AUTHORIZEPAYMENT;
            }
            
            else if("AddAdress".equals(btnAction)){
                url = ADDADDRESS;
            }
            else if("insertSerlvet".equals(btnAction)){
                url = INSERTSERVLET;
            }
            else if("Reset".equals(btnAction)){
                url = HISTORY;
            }
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
