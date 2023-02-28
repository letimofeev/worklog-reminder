const ApiError = require('../errors/apiError')

module.exports = function (error, request, response, next) {
    let e = error instanceof ApiError ? error : ApiError.internalServerError('Internal server error');
    const status = e.status
    return response.status(status).json({
        message: e.message,
        httpStatus: status,
        errors: e.errors
    })
}
