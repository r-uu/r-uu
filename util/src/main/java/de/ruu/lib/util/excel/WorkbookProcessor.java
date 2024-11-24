package de.ruu.lib.util.excel;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Processes an excel {@link Workbook}. Processing is configured by processors that can be provided by {@link
 * #sheetProcessor(SheetProcessor)} and {@link #rowProcessor(RowProcessor)}.
 */
public interface WorkbookProcessor
{
	void process(@NonNull Workbook workbook);

	WorkbookProcessor sheetProcessor(@NonNull SheetProcessor processor);
	WorkbookProcessor rowProcessor  (@NonNull RowProcessor   processor);

	@Getter @Setter @Accessors(fluent = true)
	class WorkbookProcessorSimple implements WorkbookProcessor
	{
		private @NonNull SheetProcessor sheetProcessor = SheetProcessor.create();

		@Override public void process(@NonNull Workbook workbook)
		{
			Iterator<Sheet> iterator = workbook.sheetIterator();

			while (iterator.hasNext())
			{
				sheetProcessor.process(iterator.next());
			}
		}

		@Override public WorkbookProcessor rowProcessor(@NonNull RowProcessor processor)
		{
			sheetProcessor.rowProcessor(processor);
			return this;
		}
	}
	
	static WorkbookProcessor create() { return new WorkbookProcessorSimple(); }
}