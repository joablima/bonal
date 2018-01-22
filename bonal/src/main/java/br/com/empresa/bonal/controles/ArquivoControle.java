package br.com.empresa.bonal.controles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.empresa.bonal.util.Arquivo;
import br.com.empresa.bonal.util.logging.Logging;

@Named
@ViewScoped
public class ArquivoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	Logger logger;

	private StreamedContent streamedContent;

	@Logging
	public void upload(FileUploadEvent upload) {
		Arquivo arquivo = new Arquivo();
		try {
			arquivo.upload("C:/bonal", upload.getFile().getFileName(), upload.getFile().getInputstream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Logging
	public void upload2(FileUploadEvent upload) throws IOException {
		byte[] contents = upload.getFile().getContents();

		String fileName = upload.getFile().getFileName();
		Path diretorioServidor = Paths.get("C:/bonal/" + fileName);

		Files.write(diretorioServidor, contents);
	}

	public void download() throws IOException {
		// Abaixo temos um código estático, mas
		// obviamente você pode buscar o arquivo de onde quiser :)
		File file = new File("C:/log/img.png");
		String contentType = Files.probeContentType(file.toPath());
		String name = file.getName();

		logger.info("download do caminho: " + file.getAbsolutePath() + " - tipo: " + contentType);

		InputStream in = new FileInputStream(file);
		this.streamedContent = new DefaultStreamedContent(in, contentType, name);
	}

	public StreamedContent getStreamedContent() {
		return streamedContent;
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}

}
