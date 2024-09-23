/**
 * VERSION: 1.0.0
 * DATE: 2024-06-27
 */


// TODO: SET TO FALSE IN PROD ENVIRONMENT
let debugMode = true;


class Logger {
	traceEntry(functionName, args) {
		if (debugMode) {
			var message = "%c" + functionName + "%c(";
			for (var index in args) {
				if (index > 0) message += ", ";
				message += JSON.stringify(args[index]);
			}
			message += ")";
			console.log(message, "color:purple; font-weight:bold", "color:purple; font-weight:normal");
		}
	}
	
	traceExit(functionName, returnValue) {
		if (debugMode) {
			var message = "%c" + functionName + "%c() --> Return" + (returnValue != undefined ? " " + JSON.stringify(returnValue) : "");
			console.log(message, "color:purple; font-weight:bold", "color:purple; font-weight:normal");
		}
		return returnValue;
	}
	
	log(message) {
		if (debugMode) console.log(message);
	}
	
	info(message) {
		if (debugMode) console.info(message);
	}
	
	debug(message) {
		if (debugMode) console.debug(message);
	}
	
	warn(message) {
		if (debugMode) console.warn(message);
	}

	success(message) {
		if (debugMode) console.log("%c" + message, "color:green; font-weight:bold");
	}
	
	error(message, data) {
		if (debugMode) {
			console.error(message);
			if (data) console.error(data);
		}
	}
}


// Global Logger instance
const log = new Logger();

// Hack to use "caller" property (does not work inside a class)
log.entry = function() {
	log.traceEntry(arguments.callee.caller.name, arguments);
}

// Hack to use "caller" property (does not work inside a class)
log.exit = function(returnValue) {
	return log.traceExit(arguments.callee.caller.name, returnValue);
}
