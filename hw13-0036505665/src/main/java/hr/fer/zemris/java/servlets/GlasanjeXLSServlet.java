package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.util.BandData;
import hr.fer.zemris.java.util.GlasanjeUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * A servlet that generates an XLS document containing favorite band voting results.
 *
 * @author Bruna Dujmović
 *
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/vnd.ms-excel");
        resp.setHeader("Content-Disposition", "attachment; filename=\"glasanje.xls\"");

        List<BandData> results = GlasanjeUtil.getVotingResults(req);
        HSSFWorkbook workbook = getGlasanjeXLS(results);

        workbook.write(resp.getOutputStream());
    }

    /**
     * Returns an {@link HSSFWorkbook} containing favorite band voting results.
     *
     * @param results the list of voting results to be shown in the workbook
     */
    private HSSFWorkbook getGlasanjeXLS(List<BandData> results) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Voting results");

        HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Band");
        headerRow.createCell(1).setCellValue("Score");

        for (int i = 0, rows = results.size(); i < rows; i++) {
            HSSFRow row = sheet.createRow(i + 1);
            BandData band = results.get(i);

            row.createCell(0).setCellValue(band.getName());
            row.createCell(1).setCellValue(band.getScore());
        }

        return workbook;
    }
}