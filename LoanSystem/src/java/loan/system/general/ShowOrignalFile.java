/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loan.system.general;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author me
 */
@WebServlet("/showOrginalFile")
public class ShowOrignalFile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            BufferedInputStream input = null;
            BufferedOutputStream output = null;
            HttpSession session = request.getSession(false);
            byte[] file = (byte[]) session.getAttribute("doc");
            if (file == null){
                PrintWriter out = response.getWriter();
                out.print("الرجاء التأكد من أن الملف موجود");
            } else {
                response.reset();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"downlaodedFile.pdf\"");
                try {
                    InputStream content = new ByteArrayInputStream(file);
                    input = new BufferedInputStream(content);
                    output = new BufferedOutputStream(response.getOutputStream());

                    byte[] buffer = new byte[10240];
                    for (int length; (length = input.read(buffer)) > 0;) {
                        output.write(buffer, 0, length);
                    }

                } finally {
                    output.close();
                    input.close();
                    session.removeAttribute("doc");
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
