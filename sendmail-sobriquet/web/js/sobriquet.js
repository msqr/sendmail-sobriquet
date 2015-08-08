/*jslint browser: true */
/**
 * @require jQuery 2.1
 */

(function(window, console) {
'use strict';

function renderSearchResults(results) {
	var resultsContainer = $('#search-results'),
		templateRow = resultsContainer.find('.template'),
		rowContainer = templateRow.parent(),
		haveResults = (results && Array.isArray(results.results) && results.results.length > 0);
	
	if ( haveResults ) {
		rowContainer.children(':not(.template)').remove();
		results.results.forEach(function(result, idx) {
			var html = templateRow.clone(true),
				actuals = (Array.isArray(result.actuals) ? result.actuals.join(', ') : '');
			html.removeClass('template');
			html.find('.index').text(idx);
			html.find('.alias').text(result.alias);
			html.find('.actuals').text(actuals);
			rowContainer.append(html);
		});
	}
	
	$('.welcome').addClass('hidden');
	resultsContainer.find('.empty-results').toggleClass('hidden', haveResults);
	resultsContainer.find('.nonempty-results').toggleClass('hidden', !haveResults);
	resultsContainer.removeClass('hidden');
}

function isEmptyText(value) {
	return (!value || value.search(/\S/) < 0);
}

function submitSearch(form) {
	var aliasQuery = form.elements['alias'].value;
	if ( isEmptyText(aliasQuery) ) {
		// no query
		return;
	}
	$.getJSON(form.action, { alias : aliasQuery }, function(data) {
		if ( data && data.success ) {
			renderSearchResults(data.data);
		}
	});
}

function submitAddAlias(form) {
	var alias = form.elements['alias'].value,
		actual = form.elements['actual'].value,
		url = form.action.replace(/\w+$/, encodeURIComponent(alias));
	if ( isEmptyText(alias) || isEmptyText(actual) ) {
		return;
	}
	$.ajax({
		type: 'PUT',
		url: url,
		data: actual,
		contentType: 'text/plain',
		dataType : 'json'
	}).done(function() {
		$(form).modal('hide');
		// TODO
	}).fail(function(xhr, status, error) {
		// TODO
	});
}

function init() {
	$('#search-form').submit(function(event) {
		event.preventDefault();
		submitSearch(this);
		return false;
	});
	$('#add-alias-form').submit(function(event) {
		event.preventDefault();
		submitAddAlias(this);
		return false;
	}).on('shown.bs.modal', function(event) {
		$('#add-alias-alias').focus();
	}).on('hidden.bs.modal', function(event) {
		this.reset();
	});
}

$(init);
}(window, console || {
	log : function() {}
}));
