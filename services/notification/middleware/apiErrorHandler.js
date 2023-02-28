const ApiError = require("../errors/apiError");

const apiErrorHandler = function (error, request, response, next) {
    if (error instanceof ApiError) {
        console.log(`Caught api error: ${error}`)
        return response.status(error.status).json({
            message: error.message,
            httpStatus: error.status,
            errors: error.errors
        })
    }
    next(error)
}

module.exports = apiErrorHandler
