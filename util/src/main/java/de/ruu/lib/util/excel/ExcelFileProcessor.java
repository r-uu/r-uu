package de.ruu.lib.util.excel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Processes an excel file. Processing is configured by processors that can be provided by {@link
 * #workbookProcessor(WorkbookProcessor)}, {@link #sheetProcessor(SheetProcessor)} and {@link #rowProcessor(RowProcessor)}.
 */
public interface ExcelFileProcessor
{
	void process(@NonNull Path excelFile);

	ExcelFileProcessor workbookProcessor(@NonNull WorkbookProcessor processor);
	ExcelFileProcessor sheetProcessor   (@NonNull SheetProcessor    processor);
	ExcelFileProcessor rowProcessor     (@NonNull RowProcessor      processor);

	@Getter @Setter @Accessors(fluent = true)
	class ExcelFileProcessorSimple implements ExcelFileProcessor
	{
		private @NonNull WorkbookProcessor workbookProcessor = WorkbookProcessor.create();

		@Override public void process(@NonNull Path excelFile)
		{
			try (Workbook workbook = WorkbookFactory.create(new File(excelFile.toString())))
			{
				workbookProcessor.process(workbook);
			}
			catch (EncryptedDocumentException | IOException e)
			{
				throw new RuntimeException("failure creating workbook for " + excelFile, e);
			}
		}

		/** delegate sheet processor to workbook processor */
		@Override public ExcelFileProcessor sheetProcessor(@NonNull SheetProcessor processor)
		{
			workbookProcessor.sheetProcessor(processor);
			return this;
		}

		/** delegate row processor to sheet processor */
		@Override public ExcelFileProcessor rowProcessor(@NonNull RowProcessor processor)
		{
			workbookProcessor.rowProcessor(processor);
			return this;
		}
	}
	
	static ExcelFileProcessor create() { return new ExcelFileProcessorSimple(); }
}