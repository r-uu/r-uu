package de.ruu.lib.util.excel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * Processes all excel files in a directory. Processing is configured by processors that can be provided by {@link
 * #excelFileProcessor(ExcelFileProcessor)}, {@link #workbookProcessor(WorkbookProcessor)}, {@link
 * #sheetProcessor(SheetProcessor)} and {@link #rowProcessor(RowProcessor)}.
 */
public interface ExcelDirectoryProcessor
{
	void process(@NonNull Path excelDirectory);

	ExcelDirectoryProcessor excelFileProcessor(@NonNull ExcelFileProcessor processor);
	ExcelDirectoryProcessor workbookProcessor (@NonNull WorkbookProcessor  processor);
	ExcelDirectoryProcessor sheetProcessor    (@NonNull SheetProcessor     processor);
	ExcelDirectoryProcessor rowProcessor      (@NonNull RowProcessor       processor);

	@Getter @Setter @Accessors(fluent = true)
	@Slf4j
	class ExcelDirectoryProcessorSimple implements ExcelDirectoryProcessor
	{
		private @NonNull ExcelFileProcessor excelFileProcessor = ExcelFileProcessor.create();

		@Override public void process(@NonNull Path excelDirectory)
		{
			try (Stream<Path> paths = Files.list(excelDirectory))
			{
				log.debug("processing directory {}", excelDirectory);
				paths.forEach(path -> excelFileProcessor.process(path));
				log.debug("processed  directory {}", excelDirectory);
			}
			catch (IOException e)
			{
				throw new RuntimeException("failure listing files in " + excelDirectory, e);
			}
		}

		/** delegate workbook processor to excel file processor */
		@Override public ExcelDirectoryProcessor workbookProcessor(@NonNull WorkbookProcessor processor)
		{
			excelFileProcessor.workbookProcessor(processor);
			return this;
		}

		/** delegate sheet processor to workbook processor */
		@Override public ExcelDirectoryProcessor sheetProcessor(@NonNull SheetProcessor processor)
		{
			excelFileProcessor.sheetProcessor(processor);
			return this;
		}

		/** delegate row processor to sheet processor */
		@Override public ExcelDirectoryProcessor rowProcessor(@NonNull RowProcessor processor)
		{
			excelFileProcessor.rowProcessor(processor);
			return this;
		}
	}
	
	static ExcelDirectoryProcessor create() { return new ExcelDirectoryProcessorSimple(); }
}