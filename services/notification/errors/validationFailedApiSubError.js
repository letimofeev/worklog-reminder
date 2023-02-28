const ApiSubError = require("./apiSubError");

class ValidationFailedApiSubError extends ApiSubError {
    constructor(error) {
        super(error.msg);
        this.rejectedValue = error.value
    }
}

module.exports = ValidationFailedApiSubError
