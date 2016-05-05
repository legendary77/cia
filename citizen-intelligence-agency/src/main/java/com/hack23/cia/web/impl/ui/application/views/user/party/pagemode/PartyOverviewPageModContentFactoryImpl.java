/*
 * Copyright 2014 James Pether Sörling
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
package com.hack23.cia.web.impl.ui.application.views.user.party.pagemode;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.hack23.cia.model.internal.application.data.party.impl.ViewRiksdagenParty;
import com.hack23.cia.model.internal.application.data.party.impl.ViewRiksdagenPartySummary;
import com.hack23.cia.service.api.DataContainer;
import com.hack23.cia.web.impl.ui.application.views.common.labelfactory.LabelFactory;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.PageMode;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class OverviewPageModContentFactoryImpl.
 */
@Component
public final class PartyOverviewPageModContentFactoryImpl extends AbstractPartyPageModContentFactoryImpl {

	/** The Constant OVERVIEW. */
	private static final String OVERVIEW = "overview";

	/**
	 * Instantiates a new party overview page mod content factory impl.
	 */
	public PartyOverviewPageModContentFactoryImpl() {
		super();
	}

	@Override
	public boolean matches(final String page, final String parameters) {
		final String pageId = getPageId(parameters);
		return NAME.equals(page) && (StringUtils.isEmpty(parameters) || parameters.equals(pageId)
				|| parameters.contains(PageMode.OVERVIEW.toString()));
	}

	@Secured({ "ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN" })
	@Override
	public Layout createContent(final String parameters, final MenuBar menuBar, final Panel panel) {
		final VerticalLayout panelContent = createPanelContent();

		final String pageId = getPageId(parameters);


		final DataContainer<ViewRiksdagenParty, String> dataContainer = getApplicationManager()
				.getDataContainer(ViewRiksdagenParty.class);

		final DataContainer<ViewRiksdagenPartySummary, String> partySummarydataContainer = getApplicationManager()
				.getDataContainer(ViewRiksdagenPartySummary.class);

		final ViewRiksdagenParty viewRiksdagenParty = dataContainer
				.load(pageId);

		if (viewRiksdagenParty != null) {

			getPartyMenuItemFactory().createPartyMenuBar(menuBar, pageId);


			panelContent.addComponent(LabelFactory.createHeader2Label(OVERVIEW));
			panelContent.addComponent(getPageLinkFactory().addPartyPageLink(viewRiksdagenParty));

			getFormFactory().addTextFields(
					panelContent,
					new BeanItem<>(viewRiksdagenParty),
					ViewRiksdagenParty.class,
					Arrays.asList(new String[] { "partyName", "partyId",
							"headCount", "partyNumber", "registeredDate",
					"website" }));

			final ViewRiksdagenPartySummary viewRiksdagenPartySummary = partySummarydataContainer
					.load(pageId);

			if (viewRiksdagenPartySummary != null) {

				getFormFactory().addTextFields(panelContent,
						new BeanItem<>(
								viewRiksdagenPartySummary),
								ViewRiksdagenPartySummary.class,
								Arrays.asList(new String[] { "active",
										"firstAssignmentDate", "lastAssignmentDate",
										"currentAssignments", "totalAssignments",
										"totalDaysServed", "activeEu", "totalActiveEu",
										"totalDaysServedEu", "activeGovernment",
										"totalActiveGovernment",
										"totalDaysServedGovernment", "activeCommittee",
										"totalActiveCommittee",
										"totalDaysServedCommittee", "activeParliament",
										"totalActiveParliament",
								"totalDaysServedParliament" }));

			}


			pageCompleted(parameters, panel, pageId, viewRiksdagenParty);
		}
		return panelContent;

	}


}