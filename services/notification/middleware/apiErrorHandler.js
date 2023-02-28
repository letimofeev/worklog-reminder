const ApiError = require("../error/apiError");

const apiErrorHandler = function (error, request, response, next) {
    if (error instanceof ApiError) {
        console.log('api error')
        return response.status(error.status).json({
            message: error.message,
            httpStatus: error.status,
            errors: error.errors
        })
    }
    next(error)

}

module.exports = apiErrorHandler
