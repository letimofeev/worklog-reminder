const ApiError = require('../error/apiError')

const uncaughtErrorHandler = function (error, request, response, next) {
    console.error(`Uncaught error: ${error}\n${error.stack}`)
    const apiError = ApiError.internalServerError('Internal server error')
    return response.status(apiError.status).json({
        message: apiError.message,
        httpStatus: apiError.status,
        errors: []
    })
}

module.exports = uncaughtErrorHandler
