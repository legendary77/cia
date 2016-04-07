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
package com.hack23.cia.service.data.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hack23.cia.model.internal.application.data.party.impl.ViewRiksdagenPartySummary;
import com.hack23.cia.service.data.api.DataViewer;
import com.hack23.cia.service.data.api.ViewDataManager;

/**
 * The Class ViewDataManagerITest.
 */
public final class ViewDataManagerITest extends AbstractServiceDataFunctionalIntegrationTest {

	/** The view data manager. */
	@Autowired
	private ViewDataManager viewDataManager;

	@Autowired
	private DataViewer dataViewer;


	/**
	 * Refresh views test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void refreshViewsTest() {
		viewDataManager.refreshViews();
		assertNotNull("Expect some value in database",dataViewer.getAll(ViewRiksdagenPartySummary.class));

	}

}
