/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tbloders.tblOrdersDAO;
import tbloders.tblordersDTO;
import tblorderdetail.tblOrderDetailDAO;
import tblorderdetail.tblOrderDeteilDTO;
import tblproducts.tblProductsDAO;

/**
 *
 * @author IT
 */
@WebServlet(name = "GetHistoryServlet", urlPatterns = {"/GetHistoryServlet"})
public class GetHistoryServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final Logger LOGGER = Logger.getLogger(GetHistoryServlet.class);
    private final String VIEWHITORY = "history.jsp";
    private final String ERROR = "error.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("searchNameProduct");
        if (name == null) {
            name = "";
        }
        String date = request.getParameter("searchDate");
        if (date == null) {
            date = "";
        }

        String url = ERROR;

        try {
            if (!date.equals("")) {
                SimpleDateFormat pattern1 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateSearch = pattern1.parse(date);
                SimpleDateFormat pattern2 = new SimpleDateFormat("dd/MM/yyyy");
                date = pattern2.format(dateSearch);
            }
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession();
            String userid = (String) session.getAttribute("USERID");
            tblOrdersDAO daoorder = new tblOrdersDAO();
            List<tblordersDTO> listorder = daoorder.getListOrder(userid);
            tblOrderDetailDAO daoorderdetail = new tblOrderDetailDAO();
            Map<tblordersDTO, List<tblOrderDeteilDTO>> mapHistory = null;
            if (listorder.size() != 0) {
                mapHistory = new HashMap<>();
                for (tblordersDTO order : listorder) {
                    String orderid = order.getId();
                    List<tblOrderDeteilDTO> listorderdetail = daoorderdetail.getListOrderDetail(orderid);
                    for (tblOrderDeteilDTO orderDeteilDTO : listorderdetail) {
                        String idproduct = orderDeteilDTO.getProductid();
                        tblProductsDAO productDAO = new tblProductsDAO();
                        String nameproduct = productDAO.getName(idproduct);
                        orderDeteilDTO.setProductid(nameproduct);
                    }
                    mapHistory.put(order, listorderdetail);
                }
                if (name.equals("") & !date.equals("")) {
                    mapHistory = getMapHistoryBySearchDate(date, mapHistory);
                } else if (!name.equals("") & date.equals("")) {
                    mapHistory = getMapHistoryBySearchName(name, mapHistory);
                } else if (!name.equals("") & !date.equals("")) {
                    mapHistory = getMapHistoryBySearchDate(date, mapHistory);
                    mapHistory = getMapHistoryBySearchName(name, mapHistory);
                }
                request.setAttribute("MAPHISTORY", mapHistory);
            }
            url = VIEWHITORY;
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            url = ERROR;
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            url = ERROR;
        } catch (ParseException ex) {
            LOGGER.error(ex.getMessage());
            url = ERROR;
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    public Map getMapHistoryBySearchDate(String date, Map<tblordersDTO, List<tblOrderDeteilDTO>> map) {
        Map mapSearchedByDate = new HashMap();
        Set<tblordersDTO> keys = map.keySet();
        for (tblordersDTO key : keys) {
            if (key.getDate().contains(date)) {
                mapSearchedByDate.put(key, map.get(key));
            }
        }
        return mapSearchedByDate;
    }

    public Map getMapHistoryBySearchName(String name, Map<tblordersDTO, List<tblOrderDeteilDTO>> map) {
        Map mapSearchedByName = new HashMap();
        Set<tblordersDTO> keys = map.keySet();
        for (tblordersDTO key : keys) {
            List<tblOrderDeteilDTO> listProduct = map.get(key);
            for (tblOrderDeteilDTO orderDeteilDTO : listProduct) {
                if (orderDeteilDTO.getProductid().contains(name)) {
                    mapSearchedByName.put(key, listProduct);
                }
            }
        }
        return mapSearchedByName;
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
