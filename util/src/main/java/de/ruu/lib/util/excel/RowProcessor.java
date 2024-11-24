package de.ruu.lib.util.excel;

import org.apache.poi.ss.usermodel.Row;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

public interface RowProcessor
{
	void process(@NonNull Row row);
	
	@Slf4j
	class RowProcessorSimple implements RowProcessor
	{
		@Override public void process(@NonNull Row row)
		{
			log.debug("processing row {}", row.getRowNum());
		}
	}
	
	static RowProcessor create() { return new RowProcessorSimple(); }
}