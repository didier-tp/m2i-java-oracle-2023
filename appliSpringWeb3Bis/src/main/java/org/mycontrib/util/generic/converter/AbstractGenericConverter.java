package org.mycontrib.util.generic.converter;

import java.lang.reflect.Method;

/*
 * Version plus sophistiquee de GenericMapper
 * deleguant des conversions spécifique à un DtoConverter
 */

public abstract class AbstractGenericConverter extends GenericMapper {

	
	public abstract Object getDtoConverter();

	static String withFirstLowerCase(String s) {
		return Character.toLowerCase(s.charAt(0)) + s.substring(1);
	}

	@SuppressWarnings("unchecked")
	public <S, D> D map(S source, Class<D> destinationClass) {
		D destination = null;
		try {
			// first try With DtoConverter
			String convertMethodName = withFirstLowerCase(
					source.getClass().getSimpleName() + "To" + destinationClass.getSimpleName());
			// System.out.println("convertMethodName="+convertMethodName);
			Method convertMethod = getDtoConverter().getClass().getDeclaredMethod(convertMethodName, source.getClass());
			destination = (D) convertMethod.invoke(getDtoConverter(), source);
		}
		catch (NoSuchMethodException e) {
			// second try without DtoConverter (as fault back)
			destination = super.map(source, destinationClass);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return destination;
	}

}
