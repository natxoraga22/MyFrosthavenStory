/**
 * VERSION: 1.0.0
 * DATE: 2024-06-27
 */


// ResponseType "enum"
const ResponseType = {
	JSON: "JSON",
	TEXT: "TEXT"
}

// HandlerType "enum"
const HandlerType = {
	SUCCESS: "SUCCESS",
	ERROR: "ERROR",
	ALWAYS: "ALWAYS"
}


function ajax(method, url, data = null, responseType = ResponseType.JSON) {
	//const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
	//const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
	const handlers = [];

	// Function to run all handlers of type in the order they were added
	const processHandlers = (type, result) => {
		handlers.forEach(handler => {
			if (handler.type == HandlerType.ALWAYS || handler.type == type) handler.callback(result);
		});
	}

	// Fetch options
	const fetchOptions = { method: method };

	// Headers
	//const headers = new Headers({ [csrfHeader]: csrfToken });
	const headers = new Headers();
	if (data != null && !(data instanceof FormData)) headers.set("Content-Type", "application/json");
	fetchOptions.headers = headers;

	// Body
	if (data != null) fetchOptions.body = data instanceof FormData ? data : JSON.stringify(data)

	// Fetch operation
	fetch(url, fetchOptions)
		.then(response => {
			if (!response.ok) throw new Error(`HTTP error ${response.status}: ${response.statusText}`);
			if (response.status != "204") {
				if (responseType == ResponseType.JSON) return response.json();
				if (responseType == ResponseType.TEXT) return response.text();
			}
		})
		.then(responseData => processHandlers(HandlerType.SUCCESS, responseData))
		.catch(errorData => processHandlers(HandlerType.ERROR, errorData));

	// Return object with methods to add handlers
	return {
		success: function(callback) { handlers.push({ type: HandlerType.SUCCESS, callback: callback }); return this; },
		error: function(callback) { handlers.push({ type: HandlerType.ERROR, callback: callback }); return this; },
		always: function(callback) { handlers.push({ type: HandlerType.ALWAYS, callback: callback }); return this; }
	}
}


function ajaxGet(url, responseType = ResponseType.JSON) {
	return ajax("GET", url, null, responseType);
}

function ajaxPost(url, data, responseType = ResponseType.JSON) {
	return ajax("POST", url, data, responseType);
}

function ajaxDelete(url, data, responseType = ResponseType.JSON) {
	return ajax("DELETE", url, data, responseType);
}
