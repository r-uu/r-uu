module mapstruct.explore
{
	requires static lombok;

	requires org.mapstruct;
	requires org.slf4j;
	requires de.ruu.lib.mapstruct;
	requires de.ruu.lib.util;

//	opens de.ruu.lib.mapstruct.explore.objectfactory to org.junit.platform.commons
//			                                              , org.mapstruct
	opens de.ruu.lib.mapstruct.explore.objectfactory to org.mapstruct;
}