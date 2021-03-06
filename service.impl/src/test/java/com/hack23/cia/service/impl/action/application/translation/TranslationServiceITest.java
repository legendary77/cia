/*
 * Copyright 2010 James Pether Sörling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *	$Id$
 *  $HeadURL$
*/
package com.hack23.cia.service.impl.action.application.translation;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hack23.cia.service.impl.AbstractServiceFunctionalIntegrationTest;

/**
 * The Class TranslationServiceITest.
 */
public final class TranslationServiceITest extends AbstractServiceFunctionalIntegrationTest {

	@Autowired
	private TranslationService translationService;

	/**
	 * Translate success test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	@Ignore
	public void translateSuccessTest() throws Exception {
		assertEquals("Expect correct translation", "Dags för frukost",
				translationService.translate("Time for breakfast", "SV"));
	}

}
