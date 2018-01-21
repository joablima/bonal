package br.com.empresa.bonal.controles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@ViewScoped
public class PoiControle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	UnidadeDeMedidaControle controle;

	@Inject
	Logger logger;

	// Workbook workbook;
	//
	// public void workbook(FileUploadEvent upload) {
	// String excelFilePath = upload.getFile().getFileName();
	//
	// if (excelFilePath.endsWith("xlsx")) {
	// this.workbook = new XSSFWorkbook();
	// } else if (excelFilePath.endsWith("xls")) {
	// this.workbook = new HSSFWorkbook();
	// } else {
	// throw new IllegalArgumentException("The specified file is not Excel file");
	// }
	// this.workbook = null;
	// }

	public <T> StreamedContent downloadXlsx(List<T> list) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, IOException, ParseException {

		T objeto = null;

		Class<? extends Object> classe = list.get(0).getClass();
		String simpleName = classe.getSimpleName();
		Field[] fields = classe.getDeclaredFields();

		int rownum = list.size() + 1;
		int cellnum = fields.length;

		String address;

		SXSSFWorkbook wb = new SXSSFWorkbook(rownum);
		Sheet sh = wb.createSheet(simpleName);

		for (int i = 0; i < rownum; i++) {
			Row row = sh.createRow(i);
			if (i >= 1)
				objeto = list.get(i - 1);

			// primeiro for para fazer o cabeçalho
			for (int j = 0; j < cellnum; j++) {
				Cell cell = row.createCell(j);

				if (i == 0) {
					address = fields[j].getName();
				} else {
					// Escreve todos os valores do atributo da lista
					Field field = classe.getDeclaredField(fields[j].getName());
					field.setAccessible(true);
					Class<?> type = fields[j].getType();

					// tentativa de transformar as Strings que extendem de Number em numero no xls
					// if (type.getSuperclass().getSimpleName().equals("Number")) {
					// String string = field.get(objeto).toString();
					// }

					if (type.equals(Calendar.class)) {
						Calendar calendar = (Calendar) field.get(objeto);
						Date date = calendar.getTime();
						DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						address = dateFormat.format(date);
					} else {
						address = field.get(objeto).toString();
					}
				}
				cell.setCellValue(address);
			}
		}

		// gravando em disco
		File file = new File("C:/log/" + simpleName + "-" + Instant.now() + ".xlsx");
		FileOutputStream out = new FileOutputStream(file);
		wb.write(out);
		out.close();

		// dispose of temporary files backing this workbook on disk
		wb.dispose();
		wb.close();

		// parametros para o download
		String contentType = Files.probeContentType(file.toPath());
		String fileName = file.getName();
		InputStream in = new FileInputStream(file);
		return new DefaultStreamedContent(in, contentType, fileName);
	}

}
