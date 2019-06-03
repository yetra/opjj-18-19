package hr.fer.zemris.java.servlets;

import org.apache.poi.hssf.usermodel.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A servlet that generates an XLS document containing the i-th powers of all integers
 * in a specified range.
 *
 * @author Bruna Dujmović
 *
 */
@WebServlet("/powers")
public class PowerXLSServlet extends HttpServlet {

    /**
     * The minimum value of the a parameter.
     */
    private static final int MIN_A = -100;
    /**
     * The maximum value of the a parameter.
     */
    private static final int MAX_A = 100;

    /**
     * The minimum value of the b parameter.
     */
    private static final int MIN_B = -100;
    /**
     * The maximum value of the a parameter.
     */
    private static final int MAX_B = 100;

    /**
     * The minimum value of the n parameter.
     */
    private static final int MIN_N = 1;
    /**
     * The maximum value of the n parameter.
     */
    private static final int MAX_N = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int a = Integer.parseInt(req.getParameter("a"));
            int b = Integer.parseInt(req.getParameter("b"));
            int n = Integer.parseInt(req.getParameter("n"));

            if (a < MIN_A || a > MAX_A || b < MIN_B || b > MAX_B || n < MIN_N || n > MAX_N) {
                req.setAttribute("error", "The given parameters are not in range!");
                req.getRequestDispatcher("/powerError.jsp").forward(req, resp);
            }

            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment; filename=\"power.xls\"");

            HSSFWorkbook workbook = getPowerXLS(n, a, b);
            workbook.write(resp.getOutputStream());

        } catch (NumberFormatException | NullPointerException e) {
            req.setAttribute("error", "The given parameters are of invalid type!");
            req.getRequestDispatcher("/powerError.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            req.setAttribute("error", "Failed to create .xls file!");
            req.getRequestDispatcher("/powerError.jsp").forward(req, resp);
        }

    }

    /**
     * Returns an {@link HSSFWorkbook} of n sheets. The i-th sheet contains a table
     * with two columns. The first column displays all the integers from a to b. The
     * second column displays the i-th powers of those integers.
     *
     * @param n the number of workbook sheets
     * @param a the first number in the range
     * @param b the last number in the range
     * @return an {@link HSSFWorkbook} of n sheets containing the i-th powers of all
     *         integers in the given range
     */
    private HSSFWorkbook getPowerXLS(int n, int a, int b) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        for (int i = 1; i <= n; i++) {
            HSSFSheet sheet = workbook.createSheet("Sheet " + i);

            HSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Value");
            headerRow.createCell(1).setCellValue("Power of " + i);

            int maxRow = Math.abs(b - a) + 1;
            for (int j = 1; j <= maxRow; j++) {
                HSSFRow row = sheet.createRow(j);
                row.createCell(0).setCellValue(a + j - 1);
                row.createCell(1).setCellValue((int) Math.pow(a + j - 1, i));
            }
        }

        return workbook;
    }
}
