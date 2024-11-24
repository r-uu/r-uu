package de.ruu.lib.util.excel;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Processes an excel {@link Sheet}. Processing is configured by processors that can be provided by {@link
 * #rowProcessor(RowProcessor)}.
 */
public interface SheetProcessor
{
	void process(@NonNull Sheet sheet);

	SheetProcessor rowProcessor(@NonNull RowProcessor processor);
	
	@Getter @Setter @Accessors(fluent = true)
	class SheetProcessorSimple implements SheetProcessor
	{
		private @NonNull RowProcessor rowProcessor = RowProcessor.create();

		@Override public void process(@NonNull Sheet sheet)
		{
			Iterator<Row> iterator = sheet.rowIterator();

			while (iterator.hasNext())
			{
				Row row = iterator.next();
				if (row == null) continue;
				rowProcessor().process(row);
			}
		}
	}
	
	static SheetProcessor create() { return new SheetProcessorSimple(); }
}