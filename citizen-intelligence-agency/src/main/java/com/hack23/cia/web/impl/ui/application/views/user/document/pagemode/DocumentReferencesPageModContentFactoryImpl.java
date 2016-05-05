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
package com.hack23.cia.web.impl.ui.application.views.user.document.pagemode;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.hack23.cia.model.external.riksdagen.dokumentlista.impl.DocumentElement;
import com.hack23.cia.model.external.riksdagen.dokumentstatus.impl.DocumentData;
import com.hack23.cia.model.external.riksdagen.dokumentstatus.impl.DocumentData_;
import com.hack23.cia.model.external.riksdagen.dokumentstatus.impl.DocumentReferenceData;
import com.hack23.cia.model.external.riksdagen.dokumentstatus.impl.DocumentStatusContainer;
import com.hack23.cia.model.external.riksdagen.dokumentstatus.impl.DocumentStatusContainer_;
import com.hack23.cia.model.external.riksdagen.utskottsforslag.impl.CommitteeProposalComponentData;
import com.hack23.cia.model.internal.application.system.impl.ApplicationEventGroup;
import com.hack23.cia.service.api.DataContainer;
import com.hack23.cia.web.impl.ui.application.action.ViewAction;
import com.hack23.cia.web.impl.ui.application.views.common.labelfactory.LabelFactory;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.DocumentPageMode;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class DocumentReferencesPageModContentFactoryImpl.
 */
@Component
public final class DocumentReferencesPageModContentFactoryImpl extends AbstractDocumentPageModContentFactoryImpl {

	/** The Constant DOCUMENT_REFERENCES. */
	private static final String DOCUMENT_REFERENCES = "Document References";



	/**
	 * Instantiates a new document references page mod content factory impl.
	 */
	public DocumentReferencesPageModContentFactoryImpl() {
		super();
	}

	@Override
	public boolean matches(final String page, final String parameters) {
		return NAME.equals(page) && (!StringUtils.isEmpty(parameters) && parameters.contains(DocumentPageMode.DOCUMENTREFERENCES.toString()));
	}

	@Secured({ "ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN" })
	@Override
	public Layout createContent(final String parameters, final MenuBar menuBar, final Panel panel) {
		final VerticalLayout panelContent = createPanelContent();

		final String pageId = getPageId(parameters);

		final DataContainer<DocumentElement, String> documentElementDataContainer = getApplicationManager()
				.getDataContainer(DocumentElement.class);

		final DataContainer<DocumentStatusContainer, String> documentStatusContainerDataContainer = getApplicationManager()
				.getDataContainer(DocumentStatusContainer.class);

		getApplicationManager()
				.getDataContainer(CommitteeProposalComponentData.class);

		final DocumentElement documentElement = documentElementDataContainer.load(pageId);

		if (documentElement != null) {

			getDocumentMenuItemFactory().createDocumentMenuBar(menuBar, pageId);

			final DocumentStatusContainer documentStatusContainer = documentStatusContainerDataContainer
					.findByQueryProperty(DocumentStatusContainer.class, DocumentStatusContainer_.document,
							DocumentData.class, DocumentData_.id, pageId);




			panelContent.addComponent(LabelFactory.createHeader2Label(DOCUMENT_REFERENCES));

			if (documentStatusContainer != null
					&& documentStatusContainer.getDocumentReferenceContainer() != null
					&& documentStatusContainer.getDocumentReferenceContainer()
							.getDocumentReferenceList() != null) {
				final BeanItemContainer<DocumentReferenceData> documentReferenceDataDataSource = new BeanItemContainer<>(
						DocumentReferenceData.class,
						documentStatusContainer.getDocumentReferenceContainer().getDocumentReferenceList());

				final Grid documentReferenceDataItemGrid = getGridFactory().createBasicBeanItemGrid(
						documentReferenceDataDataSource, "Document references",
						new String[] { "referenceType", "referenceDocumentId", "detail" },
						new String[] { "hjid" }, null, null, null);
				panelContent.addComponent(documentReferenceDataItemGrid);

			}


			panel.setContent(panelContent);
			getPageActionEventHelper().createPageEvent(ViewAction.VISIT_DOCUMENT_VIEW, ApplicationEventGroup.USER, NAME, parameters, pageId);
		}

		return panelContent;

	}


}