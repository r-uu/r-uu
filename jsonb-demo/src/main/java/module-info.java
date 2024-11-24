module de.ruu.lib.jsonb.demo
{
	opens de.ruu.lib.jsonb.demo.creator;

	requires static lombok;
	requires org.slf4j;

	requires jakarta.json.bind;
	requires de.ruu.lib.jsonb;
	requires de.ruu.lib.util;
	requires jakarta.json;
}