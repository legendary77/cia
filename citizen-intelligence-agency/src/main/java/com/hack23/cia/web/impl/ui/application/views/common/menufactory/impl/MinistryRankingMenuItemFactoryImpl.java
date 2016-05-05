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
package com.hack23.cia.web.impl.ui.application.views.common.menufactory.impl;

import org.springframework.stereotype.Service;

import com.hack23.cia.web.impl.ui.application.views.common.menufactory.api.MinistryRankingMenuItemFactory;
import com.hack23.cia.web.impl.ui.application.views.common.pagelinks.PageModeMenuCommand;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.PageMode;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.UserViews;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * The Class MinistryRankingMenuItemFactoryImpl.
 */
@Service
public final class MinistryRankingMenuItemFactoryImpl extends AbstractMenuItemFactoryImpl implements MinistryRankingMenuItemFactory {

	/** The Constant COMMAND21. */
	private static final PageModeMenuCommand COMMAND21 = new PageModeMenuCommand(UserViews.MINISTRY_RANKING_VIEW_NAME, PageMode.PAGEVISITHISTORY);

	/** The Constant COMMAND20. */
	private static final PageModeMenuCommand COMMAND20 = new PageModeMenuCommand(UserViews.MINISTRY_RANKING_VIEW_NAME, PageMode.CHARTS);

	/** The Constant COMMAND19. */
	private static final PageModeMenuCommand COMMAND19 = new PageModeMenuCommand(UserViews.MINISTRY_RANKING_VIEW_NAME, PageMode.DATAGRID);

	/** The Constant COMMAND18. */
	private static final PageModeMenuCommand COMMAND18 = new PageModeMenuCommand(UserViews.MINISTRY_RANKING_VIEW_NAME, PageMode.OVERVIEW);

	/** The Constant MINISTRY_RANKING. */
	private static final String MINISTRY_RANKING = "Ministry Ranking";

	/** The Constant ALL_MINISTRIES_TOTAL_MEMBERS_TEXT. */
	private static final String ALL_MINISTRIES_TOTAL_MEMBERS_TEXT = "All ministries, total members";

	/** The Constant ALL_PARTIES_TOTAL_DAYS_SERVED_IN_MINISTRIES_TEXT. */
	private static final String ALL_PARTIES_TOTAL_DAYS_SERVED_IN_MINISTRIES_TEXT = "All parties, total days served in ministries";

	/** The Constant CURRENT_PARTIES_ACTIVE_IN_MINISTRIES_HEAD_COUNT_TEXT. */
	private static final String CURRENT_PARTIES_ACTIVE_IN_MINISTRIES_HEAD_COUNT_TEXT = "Current parties active in ministries, head count";

	/** The Constant CURRENT_MINISTRIES_CURRENT_MEMBERS_TEXT. */
	private static final String CURRENT_MINISTRIES_CURRENT_MEMBERS_TEXT = "Current ministries, current members";

	/**
	 * The Constant
	 * CURRENT_AND_PAST_MEMBER_AND_SUMMARY_OF_TOTAL_POLTICIAL_DAYS_MEMBERSHIP_DESCRIPTION.
	 */
	private static final String CURRENT_AND_PAST_MEMBER_AND_SUMMARY_OF_TOTAL_POLTICIAL_DAYS_MEMBERSHIP_DESCRIPTION = "Current and past member and summary of total polticial days membership";

	/** The Constant POLITICAL_WORK_SUMMARY_TEXT. */
	private static final String POLITICAL_WORK_SUMMARY_TEXT = "Political Work Summary";

	/** The Constant RANKING_LIST_BY_TOPIC_TEXT. */
	private static final String RANKING_LIST_BY_TOPIC_TEXT = "Ranking list by topic";

	/** The Constant CHART_BY_TOPIC_TEXT. */
	private static final String CHART_BY_TOPIC_TEXT = "Chart by topic";

	/** The Constant OVERVIEW_TEXT. */
	private static final String OVERVIEW_TEXT = "Overview";

	/** The Constant PAGE_VISIT_HISTORY_TEXT. */
	private static final String PAGE_VISIT_HISTORY_TEXT = "Page Visit History";


	/**
	 * Instantiates a new ministry ranking menu item factory impl.
	 */
	public MinistryRankingMenuItemFactoryImpl() {
		super();
	}

	@Override
	public void createMinistryRankingMenuBar(final MenuBar menuBar) {
		initApplicationMenuBar(menuBar);

		createMinistryRankingTopics(menuBar.addItem(MINISTRY_RANKING, null,null));


	}

	@Override
	public void createMinistryRankingTopics(final MenuItem ministryMenuItem) {

		ministryMenuItem.addItem(OVERVIEW_TEXT, null,
				COMMAND18);

		final MenuItem listByTopic = ministryMenuItem.addItem(RANKING_LIST_BY_TOPIC_TEXT, null, null);

		final MenuItem listItem = listByTopic.addItem(POLITICAL_WORK_SUMMARY_TEXT,
				COMMAND19);
		listItem.setDescription(CURRENT_AND_PAST_MEMBER_AND_SUMMARY_OF_TOTAL_POLTICIAL_DAYS_MEMBERSHIP_DESCRIPTION);

		final MenuItem chartByTopic = ministryMenuItem.addItem(CHART_BY_TOPIC_TEXT, null, null);

		chartByTopic.addItem(CURRENT_MINISTRIES_CURRENT_MEMBERS_TEXT,
				COMMAND20);
		chartByTopic.addItem(CURRENT_PARTIES_ACTIVE_IN_MINISTRIES_HEAD_COUNT_TEXT,
				COMMAND20);
		chartByTopic.addItem(ALL_PARTIES_TOTAL_DAYS_SERVED_IN_MINISTRIES_TEXT,
				COMMAND20);
		chartByTopic.addItem(ALL_MINISTRIES_TOTAL_MEMBERS_TEXT,
				COMMAND20);

		ministryMenuItem.addItem(PAGE_VISIT_HISTORY_TEXT, null,
				COMMAND21);

	}

}