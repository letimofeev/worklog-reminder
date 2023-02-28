const ApiError = require("../errors/apiError");
const ApiSubError = require("../errors/apiSubError");

const uniqueConstraintErrorHandler = function (error, request, response, next) {
    if (error.name === 'SequelizeUniqueConstraintError') {
        console.log(`Caught SequelizeUniqueConstraintError: ${error.original.detail}`)
        const apiError = ApiError.conflict('Uniqueness error', [
            new ApiSubError(error.original.detail)
        ])
        return response.status(apiError.status).json(apiError)
    }
    next(error)
}

module.exports = {
    uniqueConstraintErrorHandler
}