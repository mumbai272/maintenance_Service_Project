package com.rest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.rest.dto.SocialMediaDTO;

@RunWith(MockitoJUnitRunner.class)
public class SocialMediaServiceInMemoryImplTest {
	private static Map<Integer, SocialMediaDTO> socialMediasMap = new HashMap<Integer, SocialMediaDTO>();
	private static SocialMediaServiceInMemoryImpl target = new SocialMediaServiceInMemoryImpl();
	private static SocialMediaDTO dto = new SocialMediaDTO(1, "Test", "test.com", "test desc");
	private static final String  SUCCESS="Success";

	@BeforeClass
	public static void setUp() {
		target.setSocialMediasMap(socialMediasMap);
	}

	@Before
	public void init() {
		socialMediasMap.clear();
	}

	@Test
	public void getAllTest() {
		socialMediasMap.put(1, dto);
		assertTrue(SUCCESS,target.getSocialMeadiaData(1).containsAll(socialMediasMap.values()));
	}

	@Test
	public void createTest1() {
		target.create(dto);
		assertEquals(SUCCESS,dto, target.getSocialMeadiaData(dto.getIdentifier()).get(0));
	}

	@Test
	public void updateTest1() {
		socialMediasMap.put(1, dto);
		dto.setName("Updated Test");
		target.update(dto);
		assertEquals(SUCCESS,dto, target.getSocialMeadiaData(dto.getIdentifier()).get(0));

	}


	@Test
	public void deleteTest1() {
		socialMediasMap.put(1, dto);
		target.delete(1);
		assertEquals(SUCCESS,0, target.getSocialMeadiaData(null).size());

	}


}
