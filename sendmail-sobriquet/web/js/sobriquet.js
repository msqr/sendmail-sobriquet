/*jslint browser: true */
/**
 * @require jQuery 2.1
 */

(function(window, console) {
'use strict';

var aliasUrlTemplate = '';
var importCarousel;

function renderAliasResults(results, resultsContainer) {
	var templateRow = resultsContainer.find('.template'),
		rowContainer = templateRow.parent(),
		haveResults = (results && Array.isArray(results) && results.length > 0);
	
	if ( haveResults ) {
		rowContainer.children(':not(.template)').remove();
		results.forEach(function(result, idx) {
			var html = templateRow.clone(true),
				actuals = (Array.isArray(result.actuals) ? result.actuals.join(', ') : '');
			html.removeClass('template');
			html.find('.index').text(idx);
			html.find('.alias').text(result.alias);
			html.find('.actuals').text(actuals);
			html.data('alias', result);
			rowContainer.append(html);
		});
	}
	
	resultsContainer.find('.empty-results').toggleClass('hidden', haveResults);
	resultsContainer.find('.nonempty-results').toggleClass('hidden', !haveResults);
	
	return haveResults;
}

function renderSearchResults(results) {
	var resultsContainer = $('#search-results');
	renderAliasResults(results ? results.results : undefined, resultsContainer);
	$('.welcome').addClass('hidden');
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
	$.getJSON(form.action, { alias : aliasQuery, actual : aliasQuery }, function(data) {
		if ( data && data.success ) {
			renderSearchResults(data.data);
		}
	});
}

function aliasUrl(key) {
	return aliasUrlTemplate.replace(/\w+$/, encodeURIComponent(key));
}

function submitAddAlias(form) {
	var alias = form.elements['alias'].value,
		actual = form.elements['actual'].value,
		url = aliasUrl(alias);
	if ( isEmptyText(alias) || isEmptyText(actual) ) {
		return;
	}
	$.ajax({
		type : 'PUT',
		url : url,
		data : actual,
		contentType : 'text/plain',
		dataType : 'json'
	}).done(function() {
		$(form).modal('hide');
		// TODO
	}).fail(function(xhr, status, error) {
		// TODO
	});
}

function findData(root, key) {
	var r = $(root),
		d = r.data(key);
	if ( d ) {
		return d;
	}
	r.parents().each(function(idx, el) {
		d = $(el).data(key);
		return (d === undefined);
	});
	return d;
}

function deleteAlias(button) {
	var btn = $(button),
		alias = findData(btn, 'alias');
	if ( !alias ) {
		return;
	}
	if ( btn.hasClass('btn-danger') ) {
		$.ajax({
			type : 'DELETE',
			url : aliasUrl(alias.alias),
			dataType : 'json'
		}).done(function() {
			// remove the row from the table
			btn.parents('.alias').remove();
			// TODO: renumber?
		}).fail(function(xhr, status, error) {
			// TODO
		});
	} else {
		// confirm delete
		btn.addClass('btn-danger');
	}
}

function showImportForm(form) {
	form = $(form);
	form.modal('show');
}

function renderImportResults(results) {
	renderAliasResults(results, $('#import-results'));
}

function submitImportForm(form) {
	var formData = new FormData(form),
		to = form.elements['_to'].value;
	$.ajax({
		type : 'POST',
		url : form.action,
		dataType : 'json',
		data : formData,
        processData: false,
        contentType: false
	}).done(function(data) {
		if ( to === 'verify' && data && data.success ) {
			renderImportResults(data.data);
			form.elements['_to'].value = 'save';
			importCarousel.carousel('next');
			$(form).find('button.back').show();
		} else if ( to === 'save' ) {
			$(form).modal('hide');
		}
	}).fail(function(xhr, status, error) {
		// TODO
	});
}

function importGotoFirstStep(button) {
	importCarousel.carousel(0);
	button.form.elements['_to'].value = 'verify';
	$(button).hide();
}

function init() {
	$('#search-form').submit(function(event) {
		event.preventDefault();
		submitSearch(this);
		return false;
	});
	$('#add-alias-form').each(function() {
		// stash a ref to the alias base URL for add/delete ops
		aliasUrlTemplate = this.action;
	}).submit(function(event) {
		event.preventDefault();
		submitAddAlias(this);
		return false;
	}).on('shown.bs.modal', function(event) {
		$('#add-alias-alias').focus();
	}).on('hidden.bs.modal', function(event) {
		this.reset();
		this.elements['_to'].value = 'verify'; // hidden form elements don't reset via reset()
	});
	$('#search-results').on('click', 'button.delete-alias', function(event) {
		deleteAlias(this);
	});
	$('#import-aliases-form').submit(function(event) {
		event.preventDefault();
		submitImportForm(this);
		return false;
	}).on('hidden.bs.modal', function(event) {
		importCarousel.carousel(0);
		this.reset();
	}).find('button.back').hide().on('click', function() {
		importGotoFirstStep(this);
	});
	$('a.action-import').on('click', function(event) {
		event.preventDefault();
		showImportForm($('#import-aliases-form'));
	});
	$('#import-carousel').each(function() {
		// stash a ref to the alias base URL for add/delete ops
		importCarousel = $(this);
	}).carousel({interval : false, keyboard : false});
}

$(init);
}(window, console || {
	log : function() {}
}));
