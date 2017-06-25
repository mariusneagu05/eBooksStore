/*
 * Servlet eBooksStoreManagerServlet.java serves eBooksStoreManagerServlet.JSP.
 * Servlet manages CRUD operations on ISBN.
 */
package servlets;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet is serving eBooksStoreManagerServlet.jsp
 *
 * @author MariusNeagu
 */
public class eBooksStoreManagerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // declare specific DB info
        String user = "test";
        String password = "test";
        String url = "jdbc:derby://localhost:1527/eBooksDatabase;create=true;";
        String driver = "org.apache.derby.jdbc.ClientDriver";
        response.setContentType("text/html;charset=UTF-8");
        // check push on Insert button
        if (request.getParameter("admin_isbn_values_insert") != null) { // insert values from fields
            // set connection paramters to the DB
            // read values from page fields
            String ID = request.getParameter("admin_id_values");
            String ID_AUTHORS = request.getParameter("admin_authorid_values");
            String NAME = request.getParameter("admin_name_values");
            String ISBN = request.getParameter("admin_isbn_values");
            String TITLE = request.getParameter("admin_title_values");
            String PRICE = request.getParameter("admin_price_values");
            // declare specific variables
            ResultSet resultSet = null;
            Statement statement = null;
            Connection connection = null;
            PreparedStatement pstmnt = null;
            try {
                //check driver and create connection
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                // realize the insert into EBOOKS Table
                connection.setAutoCommit(false);
                String DML = "INSERT INTO EBOOKS VALUES (?, ?, ?)";
                pstmnt = connection.prepareStatement(DML);
                pstmnt.setString(1, ISBN);
                pstmnt.setString(2, TITLE);
                pstmnt.setString(3, PRICE);
                pstmnt.execute();
                // realize the insert into AUTHORS Table
                String DML1 = "INSERT INTO AUTHORS VALUES (?, ?)";
                pstmnt = connection.prepareStatement(DML1);
                pstmnt.setString(1, ID_AUTHORS);
                pstmnt.setString(2, NAME);
                pstmnt.execute();
                // realize the insert into EBOOKS_AUTHORS Table
                String DML2 = "INSERT INTO EBOOKS_AUTHORS VALUES (?, ?, ?)";
                pstmnt = connection.prepareStatement(DML2);
                pstmnt.setInt(1, parseInt(ID));
                pstmnt.setString(2, ISBN);
                pstmnt.setInt(3, parseInt(ID_AUTHORS));
                pstmnt.execute();
                connection.commit();
                connection.setAutoCommit(true);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(eBooksStoreAdminUserRolesServlet.class.getName()).log(Level.SEVERE, null, ex);
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(eBooksStoreAdminUserRolesServlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // redirect page to its JSP as view
                request.getRequestDispatcher("./eBooksStoreManager.jsp").forward(request, response);
            }
        } // check push on Update button
        else if (request.getParameter("admin_isbn_values_update") != null){ // update
                // declare specific variables
                ResultSet resultSet = null;
                PreparedStatement pstmnt = null;
                Connection connection = null;
                try
                { 
                    //check driver and create connection
                    Class driverClass = Class.forName(driver);
                    connection = DriverManager.getConnection(url, user, password);
                    // identify selected checkbox and for each execute the update operation
                    String[] selectedCheckboxes = request.getParameterValues("admin_isbn_values_checkbox");
                    String newIsbn = request.getParameter("admin_isbn_values");
                    // if both username and password are "" do nothing
                    if(!("".equals(newIsbn))){
                        for(String s : selectedCheckboxes){
                            // realize update of all selected rows
                            String DML = "UPDATE EBOOKS SET ISBN=? WHERE ISBN=?";
                            pstmnt = connection.prepareStatement(DML);
                            pstmnt.setString(1, newIsbn);
                            pstmnt.setString(2, s);
                            boolean execute = pstmnt.execute();
                            break;// because isbn value is a key in ebooks table it cannot be more selections considered
                        }
                    }    
            } catch (ClassNotFoundException | SQLException ex) {
                // display a message for NOT OK
                Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // redirect page to its JSP as view
                request.getRequestDispatcher("./eBooksStoreManager.jsp").forward(request, response);
            }
        } // check push on Delete button
        else if (request.getParameter("admin_isbn_values_delete") != null) { // delete 
            // declare specific variables
            ResultSet resultSet = null;
            PreparedStatement pstmnt = null;
            Connection connection = null;
            try {
                //check driver and create connection
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                // identify selected checkbox and for each execute the delete operation
                String[] selectedCheckboxes = request.getParameterValues("admin_isbn_values_checkbox");
                // multiple delete is allowed so a transaction should be created
                connection.setAutoCommit(false);
                for (String s : selectedCheckboxes) {
                    // realize delete of all selected rows
                    String DML = "DELETE FROM EBOOKS WHERE ISBN=?";
                    pstmnt = connection.prepareStatement(DML);
                    pstmnt.setString(1, s);
                    pstmnt.execute();
                }
                connection.commit();
                connection.setAutoCommit(true);
            } catch (ClassNotFoundException | SQLException ex) {
                // display a message for NOT OK
                Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception ex) {
                        Logger.getLogger(eBooksStoreManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // redirect page to its JSP as view
                request.getRequestDispatcher("./eBooksStoreManager.jsp").forward(request, response);
            }
        } // check push on Cancel button
        else if (request.getParameter("admin_isbn_values_cancel") != null) { // cancel
            request.getRequestDispatcher("./eBooksStoreMainPage.jsp").forward(request, response);
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
        return "Servlet is serving eBooksStoreManager.jsp";
    }// </editor-fold>
}
