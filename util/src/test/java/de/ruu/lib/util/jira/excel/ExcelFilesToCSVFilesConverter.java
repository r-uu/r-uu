package de.ruu.lib.util.jira.excel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import de.ruu.lib.util.excel.ExcelDirectoryProcessor;
import de.ruu.lib.util.excel.ExcelDirectoryProcessor.ExcelDirectoryProcessorSimple;
import de.ruu.lib.util.excel.ExcelFileProcessor;
import de.ruu.lib.util.excel.ExcelFileProcessor.ExcelFileProcessorSimple;
import de.ruu.lib.util.excel.RowProcessor.RowProcessorSimple;
import de.ruu.lib.util.excel.SheetProcessor.SheetProcessorSimple;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

public interface ExcelFilesToCSVFilesConverter
{
	/**
	 * @param sourceDirectory where the jira export files live
	 * @param targetDirectory where the transformed csv files live
	 * @throws IOException if transformation process fails due to io problems
	 */
	void transform(@NonNull Path sourceDirectory, @NonNull Path targetDirectory) throws IOException;

	class ExcelFilesToCSVFilesConverterSimple implements ExcelFilesToCSVFilesConverter
	{
		@RequiredArgsConstructor
		@Slf4j
		private static class RowProcessorLocal extends RowProcessorSimple
		{
			private final static Config CONFIG = ConfigProvider.getConfig();

			private final static String HEADER_DATA_EXCEL = CONFIG.getValue("header.data.excel", String.class);

			private final static String       EXCEL_EXPORT_BLACKLIST_CELLVALUES_STRING =
					CONFIG.getValue("excel.export.blacklist.cellvalues", String.class);
			private final static List<String> EXCEL_EXPORT_BLACKLIST_CELLVALUES        =
					Arrays.asList(EXCEL_EXPORT_BLACKLIST_CELLVALUES_STRING.split(";"));

			private final static DataFormatter FORMATTER = new DataFormatter();

			private final @NonNull Writer writer;

			@Override public void process(@NonNull Row row)
			{
				boolean        firstCell      = true;
				List<String>   cellsAsStrings = new ArrayList<>();
				Iterator<Cell> cells          = row.cellIterator();
				
				while (cells.hasNext())
				{
					Cell   cell      = cells.next();
					String cellValue = FORMATTER.formatCellValue(cell);
					
					if (firstCell == true)
					{
						if (cellValue.equals(HEADER_DATA_EXCEL.substring(0, HEADER_DATA_EXCEL.indexOf(';'))))
						{
							log.debug("processing header");
							cellsAsStrings.add(HEADER_DATA_EXCEL); // create entire new csv row for header
							break; // stop processing of cell values for current header row
						}
					}

					for (String blackListItem : EXCEL_EXPORT_BLACKLIST_CELLVALUES)
					{
						if (cellValue.contains(blackListItem))
						{
							log.debug("skipping row with blacklist cell value {}", blackListItem);
							return;
						}
					}

					cellsAsStrings.add
					(
						cellValue.replaceAll(";", ":") // replace ";" separator inside cell value
					);
					firstCell = false;
				}

				String string = String.join(";", cellsAsStrings) + "\n";

				try
				{
					writer.write(string);
				}
				catch (IOException e)
				{
					throw new RuntimeException("failure writing", e);
				}
			}
		}

		@RequiredArgsConstructor
		@Slf4j
		private static class SheetProcessorLocal extends SheetProcessorSimple
		{
			private final @NonNull Path targetFile;

			@Override public void process(@NonNull Sheet sheet)
			{
				int sheetIndex = sheet.getWorkbook().getSheetIndex(sheet.getSheetName());

				if (sheetIndex == 1)
				{
					log.debug("processing sheet {}", sheetIndex);

					String absolutePathAsString = targetFile.toAbsolutePath().normalize().toString();

					try (Writer writer = new BufferedWriter(new FileWriter(absolutePathAsString, StandardCharsets.UTF_8, false)))
					{
						log.debug("writing sheet to csv file {}", absolutePathAsString);
						super.rowProcessor(new RowProcessorLocal(writer)); // configure row processor
						super.process(sheet);                              // process sheet
						log.debug("wrote   sheet to csv file {}", absolutePathAsString);
					}
					catch (IOException e)
					{
						log.error("io error for " + absolutePathAsString, e);
					}

					log.debug("processed  sheet {}", sheetIndex);
				}
				else
				{
//					log.debug("skipping sheet {}", sheetIndex);
				}
			}
			
		}

		@RequiredArgsConstructor
		private static class ExcelFileProcessorLocal extends ExcelFileProcessorSimple
		{
			private final @NonNull Path targetDirectory;

			@Override public void process(@NonNull Path excelFile)
			{
				String absoluteTargetPathAsString =
						  targetDirectory.toAbsolutePath().normalize().toString()
						+ "/"
						+ excelFile.getFileName()
						+ ".csv";
				Path targetFile = Paths.get(absoluteTargetPathAsString);
				super.sheetProcessor(new SheetProcessorLocal(targetFile));
				super.process(excelFile);
			}
			
		}
		
		@RequiredArgsConstructor
		@Slf4j
		private static class ExcelDirectoryProcessorLocal extends ExcelDirectoryProcessorSimple
		{
			private final @NonNull Path targetDirectory;

			@Override public void process(@NonNull Path excelDirectory)
			{
				ExcelFileProcessor processor = new ExcelFileProcessorLocal(targetDirectory);
				try (Stream<Path> paths = Files.list(excelDirectory))
				{
					log.debug("processing directory {}", excelDirectory);
					paths.forEach(path -> processor.process(path));
					log.debug("processed  directory {}", excelDirectory);
				}
				catch (IOException e)
				{
					throw new RuntimeException("failure listing files in " + excelDirectory, e);
				}
			}
			
			private static ExcelDirectoryProcessor create(@NonNull Path targetDirectory)
			{
				return new ExcelDirectoryProcessorLocal(targetDirectory);
			}
		}

		@Override public void transform(@NonNull Path sourceDirectory, @NonNull Path targetDirectory) throws IOException
		{
			ExcelDirectoryProcessor processor = ExcelDirectoryProcessorLocal.create(targetDirectory);
			processor.process(sourceDirectory);
		}
	}
	
	static ExcelFilesToCSVFilesConverter create() { return new ExcelFilesToCSVFilesConverterSimple(); }
	
	public static void main(String[] args) throws IOException
	{
		Config CONFIG = ConfigProvider.getConfig();

		Path sourceDirectory = Paths.get(CONFIG.getValue("path.directory.data.jira.export.excel.work", String.class));
		Path targetDirectory = Paths.get(CONFIG.getValue("path.directory.data.jira.transform.csv"    , String.class));
		
		create().transform(sourceDirectory, targetDirectory);
	}
}