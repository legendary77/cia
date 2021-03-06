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
package com.hack23.cia.web.impl.ui.application.views.admin.system.pagemode;

import java.util.Arrays;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.hack23.cia.model.internal.application.system.impl.ApplicationEventGroup;
import com.hack23.cia.model.internal.application.user.impl.UserAccount;
import com.hack23.cia.model.internal.application.user.impl.UserAccount_;
import com.hack23.cia.service.api.DataContainer;
import com.hack23.cia.web.impl.ui.application.action.ViewAction;
import com.hack23.cia.web.impl.ui.application.views.common.labelfactory.LabelFactory;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.AdminViews;
import com.hack23.cia.web.impl.ui.application.views.pageclicklistener.PageItemPropertyClickListener;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.v7.data.util.BeanItem;
import com.vaadin.v7.data.util.BeanItemContainer;
import com.vaadin.v7.ui.VerticalLayout;

/**
 * The Class AdminUserAccountPageModContentFactoryImpl.
 */
@Component
public final class AdminUserAccountPageModContentFactoryImpl extends AbstractAdminSystemPageModContentFactoryImpl {

	/** The Constant ADMIN_USERACCOUNT. */
	private static final String ADMIN_USERACCOUNT = "Admin Useraccount";

	/** The Constant NAME. */
	public static final String NAME = AdminViews.ADMIN_USERACCOUNT_VIEW_NAME;

	/**
	 * Instantiates a new admin user account page mod content factory impl.
	 */
	public AdminUserAccountPageModContentFactoryImpl() {
		super();
	}

	@Override
	public boolean matches(final String page, final String parameters) {
		return NAME.equals(page);
	}

	@Secured({ "ROLE_ADMIN" })
	@Override
	public Layout createContent(final String parameters, final MenuBar menuBar, final Panel panel) {
		final VerticalLayout content = createPanelContent();

		final String pageId = getPageId(parameters);
		final int pageNr= getPageNr(parameters);

		getMenuItemFactory().createMainPageMenuBar(menuBar);

		LabelFactory.createHeader2Label(content,ADMIN_USERACCOUNT);

		final DataContainer<UserAccount, Long> dataContainer = getApplicationManager()
				.getDataContainer(UserAccount.class);

		final BeanItemContainer<UserAccount> politicianDocumentDataSource = new BeanItemContainer<>(UserAccount.class,
				dataContainer.getPageOrderBy(pageNr,DEFAULT_RESULTS_PER_PAGE,UserAccount_.createdDate));

		createPagingControls(content,NAME,pageId, dataContainer.getSize(), pageNr, DEFAULT_RESULTS_PER_PAGE);

		getGridFactory().createBasicBeanItemGrid(content,
				politicianDocumentDataSource,
				"UserAccount",
				new String[] { "hjid", "modelObjectId", "modelObjectVersion", "createdDate", "userId", "username",
						"userType", "userRole", "userpassword", "email", "country", "numberOfVisits" }, new String[] { "hjid", "modelObjectId", "modelObjectVersion","userId","userpassword", "address","googleAuthKey",
							    "googleAuthVerificationCode",
							    "googleAuthScratchCodes" },
				new PageItemPropertyClickListener(AdminViews.ADMIN_USERACCOUNT_VIEW_NAME, "hjid"), null, null);


		if (pageId != null && !pageId.isEmpty()) {

			final UserAccount userAccount = dataContainer.load(Long.valueOf(pageId));

			if (userAccount != null) {

				getFormFactory()
						.addFormPanelTextFields(content, new BeanItem<>(userAccount), UserAccount.class,
								Arrays.asList(new String[] {"username", "createdDate", "email", "country",
										"numberOfVisits" }));
			}
		}

		getPageActionEventHelper().createPageEvent(ViewAction.VISIT_ADMIN_USERACCOUNT_VIEW, ApplicationEventGroup.ADMIN,
				NAME, null, pageId);

		return content;

	}

}
