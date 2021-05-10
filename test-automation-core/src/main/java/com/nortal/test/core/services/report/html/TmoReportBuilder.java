package com.nortal.test.core.services.report.html;

import java.util.List;

import com.nortal.test.core.services.report.cucumber.SkippedScenarios;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.Trends;

/**
 * This is customized report builder.
 */
public class TmoReportBuilder extends ReportBuilder {

	private final String reportBaseUrl;

	private final List<String> capabilities;
	private final SkippedScenarios skippedScenarios;
	private final ChangedFilesReport gdChanges;
	private final PostmanDataReport postmanData;
	private final List<ReportErrorMessage> errors;

	public TmoReportBuilder(final List<String> jsonFiles, final Configuration configuration,
	                        final String reportBaseUrl, final List<String> capabilities,
	                        final List<ReportErrorMessage> errors, final SkippedScenarios skippedScenarios,
	                        final ChangedFilesReport gdChanges, final PostmanDataReport postmanData) {
		super(jsonFiles, configuration);
		this.reportBaseUrl = reportBaseUrl;
		this.capabilities = capabilities;
		this.errors = errors;
		this.skippedScenarios = skippedScenarios;
		this.gdChanges = gdChanges;
		this.postmanData = postmanData;
	}

	@Override
	protected void copyStaticResources() {
		super.copyStaticResources();

		//compiled json react app that contains https://www.npmjs.com/package/react-json-view component for json viewing
		//component can be accessed by embedding a div with "<div class="json-view" data-input-json="BASE64_JSON"/>" into the report html
		//see main.c5f307ba.chunk.js if you want to make changes
		//while small changes can be made directly in js if you need to rebuild the whole app
		//it is attached in ReactJsonProjectForRebuildingBundledJs.zip
		copyResources("js", "2.c99a6472.chunk.js");
		copyResources("js", "main.c5f307ba.chunk.js");
		copyResources("js", "runtime-main.994def0d.js");
	}

	@Override
	protected void generatePages(final Trends trends) {
		new TmoFeaturesOverviewPage(reportResult, configuration, errors, skippedScenarios, gdChanges, postmanData).generatePage();

		super.generatePages(trends);

		new MailSummaryPage(reportResult, configuration, reportBaseUrl, capabilities, errors, skippedScenarios, gdChanges).generatePage();
	}
}
