/**
 * VERSION: 1.0.0
 * DATE: 2024-06-27
 */


/* ------------ */
/* STRING UTILS */
/* ------------ */

String.prototype.format = function() {
	let string = this;
	for (const index in arguments) {
		string = string.replace(new RegExp("\\{" + index + "\\}", "g"), arguments[index]);
	}
	return string;
}

String.prototype.uncapitalize = function() {
	return this.charAt(0).toLowerCase() + this.slice(1);
}
