package com.inetum.appliSpringWeb.converter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

/*
 * Version plus sophistiquee de GenericMapper
 * deleguant des conversions spécifique à DtoConverter
 */

public class GenericConverter extends GenericMapper {

	public static GenericConverter CONVERTER = new GenericConverter();

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
			Method convertMethod = DtoConverter.INSTANCE.getClass().getDeclaredMethod(convertMethodName, source.getClass());
			if (convertMethod != null) {
				destination = (D) convertMethod.invoke(DtoConverter.INSTANCE, source);
			} else {
				// second try without DtoConverter (as fault back)
				destination = super.map(source, destinationClass);
				/*
				destination = destinationClass.getDeclaredConstructor().newInstance();
				BeanUtils.copyProperties(source, destination);
				*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destination;
	}

	/*
	//code hérité de la super-classe GenericMapper:
	public <S, D> List<D> map(List<S> sourceList, Class<D> destinationClass) {
		return sourceList.stream().map((source) -> map(source, destinationClass)).collect(Collectors.toList());
	}
	*/
}
