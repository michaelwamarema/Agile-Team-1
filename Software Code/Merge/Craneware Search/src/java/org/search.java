/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tony
 */
@WebServlet(name = "search", urlPatterns = {"/search"})
public class search extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
                out.println("<html lang=\"en\">");
                out.println("<head>");
                  out.println("<meta charset=\"utf-8\">");
                  out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
                  out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\">");
                  out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js\"></script>");
                  out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js\"></script>");
                  out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js\"></script>");
                  out.println("<title>Craneware</title>");

                out.println("</head>");

                out.println("<body>");

                out.println("<!-- search options left-->");
                  out.println("<div class=\"container-fluid\">");
                    out.println("<h1 style=\"text-align:center\">Craneware</h1>");

                    out.println("<hr>");

                    out.println("<form action=\"search\" method=\"post\">");


                            out.println("<div class=\"row\">");

                              out.println("<!-- Search location-->");
                              out.println("<div class = \"col-sm-5\">");
                                out.println("<div class=\"md-form mt-0\">");
                                  out.println("<!-- Search bar -->");
                                  out.println("<p style =\"height:15px;\"> </p>");
                                  out.println("<div class=\"input-group mt-3 mb-3\">");
                                    out.println("<div class=\"input-group-prepend\">");

                                      out.println("<!-- Script to change the location type -->");
                                      out.println("<script>");
                                      out.println("function switchLocationSearchType(selection) {");
                                        out.println("var selectedType = selection;");
                                        out.println("document.getElementById(\"selectedType\").innerHTML = selectedType;");


                                      out.println("}");
                                      out.println("</script>");


                                      out.println("<button type=\"button\" class=\"btn btn-outline-secondary dropdown-toggle\" data-toggle=\"dropdown\">");
                                        out.println("<a id=\"selectedType\"> </a>");
                                      out.println("</button>");
                                      out.println("<div class=\"dropdown-menu\">");
                                        out.println("<a class=\"dropdown-item\" onclick=\"switchLocationSearchType('Zip')\">Zip Code</a>");
                                        out.println("<a class=\"dropdown-item\" onclick=\"switchLocationSearchType('Address')\">Address</a>");
                                        out.println("<a class=\"dropdown-item\" onclick=\"switchLocationSearchType('State')\">State</a>");

                                      out.println("</div>");
                                    out.println("</div>");
                                    out.println("<input type=\"text\" class=\"form-control\" placeholder=\"Search by location...\">");
                                  out.println("</div>");
                                  out.println("<p style =\"height:15px;\"> </p>");

                                  out.println("<!-- Search procedure -->");
                                  out.println("<div class=\"md-form mt-0\">");
                                    out.println("<input class=\"form-control\" type=\"text\" placeholder=\"Search by procedure...\" aria-label=\"Search\" id=\"procedureInput\">");
                                  out.println("</div>");
                                  out.println("<p style =\"height:15px;\"> </p>");

                                  out.println("<!-- Search code -->");
                                  out.println("<div class=\"md-form mt-0\">");
                                    out.println("<input class=\"form-control\" type=\"text\" placeholder=\"Search by procedure code...\" aria-label=\"Search\" id=\"codeInput\">");
                                  out.println("</div>");
                                out.println("</div>");
                                out.println("<p style =\"height:15px;\"> </p>");

                                out.println("<p style = \"text-align:center;\"><button type=\"submit\" class=\"btn btn-outline-primary\" style=\"width:220px\">Search</button></p>");
                                out.println("<p></p>");
                              out.println("</div>");



                              out.println("<!-- search options right -->");
                              out.println("<div class=\"col-sm-7\">");
                                out.println("<h4>Filters:</h4>");

                                out.println("<!-- dropdown select price range -->");

                                  out.println("<div class=\"input-group mb-3\">");
                                    out.println("<div class=\"input-group-prepend\">");
                                      out.println("<span class=\"input-group-text\">£</span>");
                                    out.println("</div>");
                                    out.println("<input type=\"text\" class=\"form-control\" placeholder=\"Maximum Price...\" aria-label=\"Amount (to the nearest pound)\">");
                                    out.println("<div class=\"input-group-append\">");
                                      out.println("<span class=\"input-group-text\">.00</span>");
                                    out.println("</div>");
                                  out.println("</div>");

                                  out.println("<p></p>");


                                  out.println("<!-- dropdown select distance range -->");
                                out.println("<div class=\"input-group mb-3\">");
                                  out.println("<input type=\"text\" class=\"form-control\" placeholder=\"Maximum Distance...\" aria-label=\"Maximum Distance\" aria-describedby=\"basic-addon2\">");
                                  out.println("<div class=\"input-group-append\">");
                                    out.println("<span class=\"input-group-text\" id=\"basic-addon2\">Km</span>");
                                  out.println("</div>");
                                out.println("</div>");

                                  out.println("<p></p>");

                                  out.println("<h4>Sort by:</h4>");

                                  out.println("<!-- dropdown select highest to lowest price -->");
                                  out.println("div class=\"input-group mb-3\">");
                                    out.println("<div class=\"input-group-prepend\">");
                                      out.println("<label class=\"input-group-text\" for=\"inputGroupSelect01\">Price</label>");
                                    out.println("</div>");
                                    out.println("<select class=\"custom-select\" id=\"inputGroupSelect01\">");
                                      out.println("<option selected>Choose...</option>");
                                      out.println("<option value=\"1\">None</option>");
                                      out.println("<option value=\"2\">Low - High</option>");
                                      out.println("<option value=\"3\">High - Low</option>");
                                    out.println("</select>");
                                  out.println("</div>");

                                  out.println("p></p>");
                                  out.println("<!-- dropdown select closest to furthest distance -->");
                                  out.println("<div class=\"input-group mb-3\">");
                                    out.println("<div class=\"input-group-prepend\">");
                                      out.println("<label class=\"input-group-text\" for=\"inputGroupSelect01\">Distance</label>");
                                    out.println("</div>");
                                    out.println("<select class=\"custom-select\" id=\"inputGroupSelect01\">");
                                      out.println("<option selected>Choose...</option>");
                                      out.println("<option value=\"1\">None</option>");
                                      out.println("<option value=\"2\">Low - High</option>");
                                      out.println("<option value=\"3\">High - Low</option>");
                                    out.println("</select>");
                                  out.println("</div>");
                                out.println("</div>");
                              out.println("</div>");

                    out.println("</form>");

                      out.println("<hr>");

                      out.println("<div class=\"container-fluid\">");
                        out.println("<div class=\"row\">");
                          out.println("<div class = \"col-sm-6\">");
                            out.println("<!-- search results -->");
                            out.println("<h1 id=\"searchResults\">Search results</h1>");


                            out.println("<script>");


                                out.println("function displaySearchResults() {");
                                    out.println("var searchResultNameArray = [\"ResultName1\", \"ResultName2\", \"ResultName3\"];");

                                    out.println("var searchResultAddressArray = [\"ResultAddress1\", \"ResultAddress2\", \"ResultAddress3\"];");

                                    out.println("var searchResultProcedureArray = [\"ResultProcedure1\", \"ResultProcedure2\", \"ResultProcedure3\"];");

                                    out.println("var searchResultPaymentsArray = [\"ResultPayments1\", \"ResultsPayments2\", \"ResultPayments3\"];");


                                    out.println("for (var i in searchResultNameArray) {");


                                        out.println("var newDiv = document.createElement(\"div\")");

                                        out.println("newDiv.className = \"container p-3 my-3 border\";");

                                        out.println("var name = document.createElement(\"h2\");");
                                        out.println("var newName = document.createTextNode(searchResultNameArray[i]);");
                                        out.println("name.appendChild(newName);");

                                        out.println("newDiv.appendChild(name);");

                                        out.println("var address = document.createElement(\"p\");");
                                        out.println("var newAddress = document.createTextNode(searchResultAddressArray[i]);");
                                        out.println("address.appendChild(newAddress);");

                                        out.println("newDiv.appendChild(address);");

                                        out.println("var procedure = document.createElement(\"p\");");
                                        out.println("var newProcedure = document.createTextNode(searchResultProcedureArray[i]);");
                                        out.println("procedure.appendChild(newProcedure);");

                                        out.println("newDiv.appendChild(procedure);");

                                        out.println("var name = document.createElement(\"p\");");
                                        out.println("var newName = document.createTextNode(searchResultNameArray[i]);");
                                        out.println("name.appendChild(newName);");

                                        out.println("newDiv.appendChild(name);");

                                        out.println("var currentDiv = document.getElementById(\"searchResults\");");

                                        out.println("$(\"#display\").append(newDiv);");

                                        /**document.body.insertBefore(newDiv, currentDiv);*/

                                       /**
                                       var name = document.createElement("h2");
                                       name.innerHTML = searchResultNameArray[i];
                                       $("#display").append(name);
                                       */

                                    out.println("}");
                                out.println("};");

                            out.println("</script>");

                            out.println("<a id=\"display\"></a>");

                          out.println("</div>");

                          out.println("<div class = \"col-sm-6\">");

                            out.println("div class=\"card sticky-top\">");
                              out.println("<!-- map -->");
                              out.println("<h1>Map</h1>");


                            out.println("</div>");


                          out.println("</div>");

                        out.println("</div>");


                      out.println("</div>");


                    out.println("</body>");

                out.println("</html>");
            
            
            Database database = new Database();
            
            ResultSet result = database.runSearchConditionP("codeInput");
            
                    
            try {
            //get size of result set
            int size =0;
                if (result != null) 
                {
                  result.last();    // moves cursor to the last row
                  size = result.getRow(); // get row id 
                }
                result.first();
            
            int[] selectIds = new int[size];
            
            while(result.next()){
                selectIds[size] = result.getInt("providerID");
                size++;
            }
            System.out.println(selectIds[0]);
            
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
            
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
