package com.bertachiniprojetos.mappers;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class DozerMapper {

	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	//private static ModelMapper mapper = new ModelMapper();
	
	public static <O,D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}
	
	public static <O,D> List<D> parseListObject(List<O> origin, Class<D> destination) {
		List<D> destinationList = new ArrayList<>();
		origin.forEach(objtOrigin -> //
		destinationList.add(mapper.map(objtOrigin, destination)));
		return destinationList;
	}
}
