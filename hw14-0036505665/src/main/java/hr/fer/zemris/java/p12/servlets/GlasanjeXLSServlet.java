package hr.fer.zemris.java.p12.servlets;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;
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
 * A servlet that generates an XLS document containing the poll voting results.
 *
 * @author Bruna Dujmović
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/vnd.ms-excel");
        resp.setHeader("Content-Disposition", "attachment; filename=\"glasanje.xls\"");

        long pollID = Long.parseLong(req.getParameter("pollID"));

        List<PollOption> options = DAOProvider.getDao().getPollOptionsFor(pollID);
        HSSFWorkbook workbook = getGlasanjeXLS(options);

        workbook.write(resp.getOutputStream());
        workbook.close();
    }

    /**
     * Returns an {@link HSSFWorkbook} containing the poll voting results.
     *
     * @param options the list of voting results to be shown in the workbook
     */
    private HSSFWorkbook getGlasanjeXLS(List<PollOption> options) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Voting results");

        HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Title");
        headerRow.createCell(1).setCellValue("Score");

        for (int i = 0, rows = options.size(); i < rows; i++) {
            HSSFRow row = sheet.createRow(i + 1);
            PollOption option = options.get(i);

            row.createCell(0).setCellValue(option.getOptionTitle());
            row.createCell(1).setCellValue(option.getVotesCount());
        }

        return workbook;
    }
}