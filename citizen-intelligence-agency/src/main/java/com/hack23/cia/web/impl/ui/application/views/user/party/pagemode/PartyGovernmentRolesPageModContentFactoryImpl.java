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

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.hack23.cia.model.internal.application.data.ministry.impl.ViewRiksdagenGovermentRoleMember;
import com.hack23.cia.model.internal.application.data.ministry.impl.ViewRiksdagenGovermentRoleMember_;
import com.hack23.cia.model.internal.application.data.party.impl.ViewRiksdagenParty;
import com.hack23.cia.service.api.DataContainer;
import com.hack23.cia.web.impl.ui.application.views.common.labelfactory.LabelFactory;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.PartyPageMode;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.UserViews;
import com.hack23.cia.web.impl.ui.application.views.pageclicklistener.PageItemPropertyClickListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class GovernmentRolesPageModContentFactoryImpl.
 */
@Component
public final class PartyGovernmentRolesPageModContentFactoryImpl extends AbstractPartyPageModContentFactoryImpl {

	/** The Constant GOVERNMENT_ROLES. */
	private static final String GOVERNMENT_ROLES = "Government Roles";

	/**
	 * Instantiates a new party government roles page mod content factory impl.
	 */
	public PartyGovernmentRolesPageModContentFactoryImpl() {
		super();
	}

	@Override
	public boolean matches(final String page, final String parameters) {
		return NAME.equals(page) && parameters.contains(PartyPageMode.GOVERNMENTROLES.toString());
	}

	@Secured({ "ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN" })
	@Override
	public Layout createContent(final String parameters, final MenuBar menuBar, final Panel panel) {
		final VerticalLayout panelContent = createPanelContent();

		final String pageId = getPageId(parameters);

		final DataContainer<ViewRiksdagenParty, String> dataContainer = getApplicationManager()
				.getDataContainer(ViewRiksdagenParty.class);

		final ViewRiksdagenParty viewRiksdagenParty = dataContainer.load(pageId);

		if (viewRiksdagenParty != null) {

			getPartyMenuItemFactory().createPartyMenuBar(menuBar, pageId);

			LabelFactory.createHeader2Label(panelContent,GOVERNMENT_ROLES);


			final DataContainer<ViewRiksdagenGovermentRoleMember, String> govermentRoleMemberDataContainer = getApplicationManager()
					.getDataContainer(ViewRiksdagenGovermentRoleMember.class);

			final BeanItemContainer<ViewRiksdagenGovermentRoleMember> currentGovermentMemberDataSource = new BeanItemContainer<>(
					ViewRiksdagenGovermentRoleMember.class,
					govermentRoleMemberDataContainer.findListByProperty(
							new Object[] { viewRiksdagenParty.getPartyId(), Boolean.TRUE },
							ViewRiksdagenGovermentRoleMember_.party, ViewRiksdagenGovermentRoleMember_.active));

			getGridFactory().createBasicBeanItemGrid(
					panelContent, currentGovermentMemberDataSource,
					GOVERNMENT_ROLES,
					new String[] { "roleId", "personId", "firstName", "lastName", "active", "detail",
							"roleCode", "fromDate", "toDate", "totalDaysServed" }, new String[] { "roleId", "personId", "party" },
					new PageItemPropertyClickListener(UserViews.POLITICIAN_VIEW_NAME, "personId"), null, null);


			pageCompleted(parameters, panel, pageId, viewRiksdagenParty);
		}
		return panelContent;

	}

}
