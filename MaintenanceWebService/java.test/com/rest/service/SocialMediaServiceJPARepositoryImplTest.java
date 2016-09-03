
package com.rest.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rest.dto.SocialMediaDTO;
import com.rest.entity.SocialMediaImpl;
import com.rest.repository.SocialMediaRepository;
import com.rest.request.BaseResponse;

@RunWith(MockitoJUnitRunner.class)
public class SocialMediaServiceJPARepositoryImplTest {
	@Mock
	private transient SocialMediaRepository repository;
	@InjectMocks
	private transient SocialMediaServiceRepositoryImpl target;
	private static SocialMediaImpl data;
	private static List<SocialMediaImpl> dataList = new ArrayList<SocialMediaImpl>();
	private static SocialMediaDTO dto;
	private static List<SocialMediaDTO> list;
	private static final String SUCCESS="Success";

	@BeforeClass
	public static void init() {
		data = new SocialMediaImpl("test", "Test.com", "desc");
		data.setIdentifier(1);
		dto = new SocialMediaDTO(data.getIdentifier(), data.getName(), data.getUrl(), data.getDescription());
		dataList.add(data);

	}

	@Test
	public void getSocialMeadiaDataTest1() {
		when(repository.findOne(1)).thenReturn(data);
		list = target.getSocialMeadiaData(1);
		assertEquals(SUCCESS,data.getName(), list.get(0).getName());
	}

	@Test(expected = RuntimeException.class)
	public void getSocialMeadiaDataTest2() {
		when(repository.findOne(10)).thenReturn(null);
		target.getSocialMeadiaData(10);
	}

	@Test
	public void getSocialMeadiaDataTest3() {
		when(repository.findAll()).thenReturn(dataList);
		list = target.getSocialMeadiaData(null);
		assertEquals(SUCCESS,dataList.get(0).getIdentifier(), list.get(0).getIdentifier());
	}

	@Test
	public void createTest() {
		when(repository.save(data)).thenReturn(data);
		final BaseResponse response = target.create(dto);
		assertEquals(SUCCESS,"Entity is created successfully", response.getMsg());
	}

	@Test
	public void updateTest1() {
		when(repository.findOne(1)).thenReturn(null);
		final BaseResponse response = target.update(dto);
		assertEquals(SUCCESS,"Social Meadia details does not exist for Id :" + 1, response.getMsg());
	}

	@Test
	public void updateTest2() {
		when(repository.findOne(1)).thenReturn(data);
		final BaseResponse response = target.update(dto);
		assertEquals(SUCCESS,"Entity is Updated successfully", response.getMsg());
	}

	@Test
	public void deleteTest1() {
		when(repository.findOne(1)).thenReturn(null);
		final BaseResponse response = target.delete(1);
		assertEquals(SUCCESS,"Social Meadia details does not exist for Id :" + 1, response.getMsg());
	}

	@Test
	public void deleteTest2() {
		when(repository.findOne(1)).thenReturn(data);
		final BaseResponse response = target.delete(1);
		assertEquals(SUCCESS,"Social Meadia details for Id :" + 1 + " removed successfully", response.getMsg());
	}
}
